package com.codepath.apps.neattweet.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.neattweet.Models.Tweet;
import com.codepath.apps.neattweet.R;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by vidhurvoora on 8/4/16.
 */
public class TwitterTimelineAdapter extends RecyclerView.Adapter<TwitterTimelineAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivProfilePic;
        public TextView tvName;
        public TextView tvTweetContent;

        public ViewHolder(View itemView) {

            super(itemView);
            ivProfilePic = (ImageView) itemView.findViewById(R.id.ivProfilePic);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvTweetContent = (TextView)itemView.findViewById(R.id.tvTweetContent);
        }
    }

    private ArrayList<Tweet> mTweets;
    private Context mContext;

    public TwitterTimelineAdapter(Context context, ArrayList<Tweet> tweets) {
        mTweets = tweets;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public TwitterTimelineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View tweetItemView = inflater.inflate(R.layout.row_item_timeline, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(tweetItemView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(TwitterTimelineAdapter.ViewHolder viewHolder, int position) {

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
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTweets.size();
    }
}
