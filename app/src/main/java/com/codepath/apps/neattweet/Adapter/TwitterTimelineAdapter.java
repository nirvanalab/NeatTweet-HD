package com.codepath.apps.neattweet.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codepath.apps.neattweet.Activity.UserDetailActivity;
import com.codepath.apps.neattweet.Manager.TwitterManager;
import com.codepath.apps.neattweet.Models.Media;
import com.codepath.apps.neattweet.Models.Tweet;
import com.codepath.apps.neattweet.Models.TweetType;
import com.codepath.apps.neattweet.Models.User;
import com.codepath.apps.neattweet.R;
import com.codepath.apps.neattweet.ViewHolder.TweetImageViewHolder;
import com.codepath.apps.neattweet.ViewHolder.TweetTextViewHolder;
import com.codepath.apps.neattweet.ViewHolder.TweetVideoViewHolder;
import com.codepath.apps.neattweet.ViewHolder.TweetWebviewViewHolder;
import com.yqritc.scalablevideoview.ScalableVideoView;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by vidhurvoora on 8/4/16.
 */
public class TwitterTimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String webCardsBaseUrl = "https://twitter.com/i/cards/tfw/v1/";

    private ArrayList<Tweet> mTweets;
    private Context mContext;

    // Define listener member variable
    private static OnTweetReplyClickListener tweetReplyClickListener;
    // Define the listener interface
    public interface OnTweetReplyClickListener {
        void onTweetReplyClicked(Tweet tweet);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnTweetReplyClickListener(OnTweetReplyClickListener listener) {
        this.tweetReplyClickListener = listener;
    }

    public TwitterTimelineAdapter(Context context, ArrayList<Tweet> tweets) {
        mTweets = tweets;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public int getItemViewType(int position) {
        //More to come
        Tweet tweet = mTweets.get(position);
        return tweet.getTweetType().ordinal();
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == TweetType.TextTweet.ordinal()) {
            //inflate the text tweet layout
            View textTweetView = inflater.inflate(R.layout.row_text_item_timeline, parent, false);
            viewHolder = new TweetTextViewHolder(textTweetView);
        } else if (viewType == TweetType.ImageTweet.ordinal()) {
            //inflate the text tweet layout
            View imageTextView = inflater.inflate(R.layout.row_image_item_timeline, parent, false);
            viewHolder = new TweetImageViewHolder(imageTextView);
        } else if (viewType == TweetType.VideoTweet.ordinal()) {
            //inflate the text tweet layout
            View textTweetView = inflater.inflate(R.layout.row_video_item_timeline, parent, false);
            viewHolder = new TweetVideoViewHolder(textTweetView);
        } else if (viewType == TweetType.WebviewTweet.ordinal()) {
            //inflate the text tweet layout
            View textTweetView = inflater.inflate(R.layout.row_webview_item_timeline, parent, false);
            viewHolder = new TweetWebviewViewHolder(textTweetView);
        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == TweetType.TextTweet.ordinal()) {
            TweetTextViewHolder textViewHolder = (TweetTextViewHolder) holder;
            configureTextTweet(textViewHolder, position);
        } else if (holder.getItemViewType() == TweetType.ImageTweet.ordinal()) {
            TweetImageViewHolder imageViewHolder = (TweetImageViewHolder) holder;
            configureImageTweet(imageViewHolder, position);
        } else if (holder.getItemViewType() == TweetType.VideoTweet.ordinal()) {
            TweetVideoViewHolder videoViewHolder = (TweetVideoViewHolder) holder;
            configureVideoTweet(videoViewHolder, position);
        } else if (holder.getItemViewType() == TweetType.WebviewTweet.ordinal()) {
            TweetWebviewViewHolder webViewHolder = (TweetWebviewViewHolder) holder;
            configureWebviewTweet(webViewHolder, position);
        }

    }

    public void setupFonts(TweetTextViewHolder viewHolder){
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto_bold.ttf");
        Typeface regularFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto_regular.ttf");
        viewHolder.getTvName().setTypeface(boldFont);
        viewHolder.getTvTimeSince().setTypeface(boldFont);
        viewHolder.getTvFavCount().setTypeface(boldFont);
        viewHolder.getTvRetweetCount().setTypeface(boldFont);
        viewHolder.getTvUsername().setTypeface(regularFont);
        viewHolder.getTvTweetContent().setTypeface(regularFont);
    }

    public void configureTextTweet(final TweetTextViewHolder viewHolder, final int position) {

        final Tweet tweet = mTweets.get(position);

        setupFonts(viewHolder);

        viewHolder.getTvName().setText(tweet.getUser().getName());
        viewHolder.getTvTweetContent().setText(tweet.getContent());

        viewHolder.getTvTimeSince().setText(tweet.getRelativeDate());
        String screenName = "@" + tweet.getUser().getScreenName();
        viewHolder.getTvUsername().setText(screenName);

        viewHolder.getIvRetweet().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( !tweet.isRetweeted() ) {
                    highlightRetweet(viewHolder);
                    //increment the count
                    viewHolder.getTvRetweetCount().setText(Integer.toString(tweet.getRetweetCount()+1));
                    TwitterManager.getSharedInstance().retweetATweet(tweet.getId(),true);
                }
                else {
                    regularRetweet(viewHolder);
                    //decrement
                    viewHolder.getTvRetweetCount().setText(Integer.toString(tweet.getRetweetCount()-1));
                    TwitterManager.getSharedInstance().retweetATweet(tweet.getId(),false);
                }
            }
        });
        viewHolder.getIvFav().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (! tweet.isFavorited() ) {
                    highlightFav(viewHolder);
                      //increment the count
                    viewHolder.getTvFavCount().setText(Integer.toString(tweet.getFavoriteCount()+1));
                    TwitterManager.getSharedInstance().markTweetAsFav(tweet.getId(),true);
                }
                else {
                    regularFav(viewHolder);
                    viewHolder.getTvFavCount().setText(Integer.toString(tweet.getFavoriteCount()-1));
                    TwitterManager.getSharedInstance().markTweetAsFav(tweet.getId(),false);
                }


            }
        });
        viewHolder.getIvReply().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( tweetReplyClickListener != null ) {
                    Tweet tweet = mTweets.get(position);
                    tweetReplyClickListener.onTweetReplyClicked(tweet);
                }
            }
        });

        viewHolder.getTvRetweetCount().setText(Integer.toString(tweet.getRetweetCount()));
        viewHolder.getTvFavCount().setText(Integer.toString(tweet.getFavoriteCount()));
        ImageView ivProfilePic = viewHolder.getIvProfilePic();
        Glide.with(getContext()).load(tweet.getUser().getProfileImageUrl())
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(ivProfilePic);

        //if retweeted set the highlight color
        if ( tweet.isRetweeted() ) {
            highlightRetweet(viewHolder);
        }else {
            regularRetweet(viewHolder);
        }

        if ( tweet.isFavorited() ) {
            highlightFav(viewHolder);
        }
        else {
            regularFav(viewHolder);
        }

        //setup on click listner for the individual pic
        ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UserDetailActivity.class);
                User user = tweet.getUser();
                intent.putExtra("user", Parcels.wrap(user));
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                getContext().startActivity(intent);

            }
        });
    }

    public void highlightFav(TweetTextViewHolder viewHolder){
        viewHolder.getIvFav().setImageDrawable(getContext().getResources().getDrawable(R.drawable.heart_icon_gold_dark));
        viewHolder.getTvFavCount().setTextColor(getContext().getResources().getColor(R.color.tweetActionHighlightColor));
    }

    public void regularFav(TweetTextViewHolder viewHolder) {
        viewHolder.getIvFav().setImageDrawable(getContext().getResources().getDrawable(R.drawable.heart_icon_grey));
        viewHolder.getTvFavCount().setTextColor(getContext().getResources().getColor(R.color.tweetActionsTextColor));
    }

    public void highlightRetweet(TweetTextViewHolder viewHolder) {
        viewHolder.getIvRetweet().setImageDrawable(getContext().getResources().getDrawable(R.drawable.retweet_icon_gold_dark));
        viewHolder.getTvRetweetCount().setTextColor(getContext().getResources().getColor(R.color.tweetActionHighlightColor));
    }

    public void regularRetweet(TweetTextViewHolder viewHolder) {
        viewHolder.getIvRetweet().setImageDrawable(getContext().getResources().getDrawable(R.drawable.retweet_icon_grey));
        viewHolder.getTvRetweetCount().setTextColor(getContext().getResources().getColor(R.color.tweetActionsTextColor));
    }

    public void configureImageTweet(TweetImageViewHolder viewHolder, int position) {
        configureTextTweet(viewHolder, position);
        final ImageView ivTweetImage = viewHolder.getIvTweetImage();
        Tweet tweet = mTweets.get(position);
        ArrayList<Media> medias = tweet.getMedias();
        if (!medias.isEmpty()) {
            //fetch first
            Media media = medias.get(0);
            String imagePath = media.getMediaUrl();
            if (imagePath != null) {
                Glide.with(getContext()).load(imagePath).into(ivTweetImage);
            }
        }
     /*   else {
          String webSnapshotUrl = tweet.getWebSnapshotUrl();
            if ( webSnapshotUrl != null ) {
                //load the image
                new WebSnapshotAsyncTask(new WebSnapshotAsyncTask.OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(Bitmap snapshot) {
                        if ( snapshot != null) {
                            ivTweetImage.setImageBitmap(snapshot);
                        }
                    }
                }).execute(webSnapshotUrl);

            }
        }*/
    }

    public void configureWebviewTweet(TweetWebviewViewHolder viewHolder, int position) {
        Tweet tweet = mTweets.get(position);
        configureTextTweet(viewHolder, position);
        WebView webView = viewHolder.getWvTweetWebCard();
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        String loadUrl = webCardsBaseUrl + tweet.getId();
        webView.loadUrl(loadUrl);
    }


    public void configureVideoTweet(TweetVideoViewHolder viewHolder, int position) {
        configureTextTweet(viewHolder, position);
        final ScalableVideoView vvTweetVideo = viewHolder.getVvTweetVideo();

        Tweet tweet = mTweets.get(position);
        ArrayList<Media> medias = tweet.getMedias();
        Media media = null;
        if (!medias.isEmpty()) {
            //find the video media
            for (Media mediaItem : medias) {
                if (mediaItem.getVideoUrl() != null) {
                    media = mediaItem;
                    break;
                }
            }
        }
        if (media != null) {
            try {
                vvTweetVideo.setDataSource(media.getVideoUrl());
                vvTweetVideo.setVolume(0, 0);
                vvTweetVideo.setLooping(true);
                //vvTweetVideo.start();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {

        if (holder.getItemViewType() == TweetType.VideoTweet.ordinal()) {

            TweetVideoViewHolder videoViewHolder = (TweetVideoViewHolder) holder;
            final ScalableVideoView vvTweetVideo = videoViewHolder.getVvTweetVideo();
            try {
                vvTweetVideo.prepare(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        vvTweetVideo.start();
                    }
                });
            } catch (IOException ioe) {
                //ignore
            }
        }
    }

    /*public void onBindViewHolder(TwitterTimelineAdapter.ViewHolder viewHolder, int position) {

        Tweet tweet = mTweets.get(position);

        // Set item views based on your views and data model

        TextView tvName = viewHolder.tvName;
        tvName.setText(tweet.getUser().getName());
        TextView tvContent = viewHolder.tvTweetContent;
        tvContent.setText(tweet.getContent());
        ImageView ivProfilePic = viewHolder.ivProfilePic;

        Glide.with(getContext()).load(tweet.getUser().getProfileImageUrl())
                                .bitmapTransform(new CropCircleTransformation(getContext()))
                                .into(ivProfilePic);
    }*/

    // Returns the total count of items in the list

}
