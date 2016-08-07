package com.codepath.apps.neattweet.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.neattweet.Manager.TweetResponseHandler;
import com.codepath.apps.neattweet.Manager.TwitterManager;
import com.codepath.apps.neattweet.Models.Tweet;
import com.codepath.apps.neattweet.Models.User;
import com.codepath.apps.neattweet.R;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 *
 */
public class ComposeTweetFragment extends android.support.v4.app.DialogFragment {

    EditText etComposeTweet;
    Button btnPost;
    private User currentUser;
    public ComposeTweetFragment() {
        // Required empty public constructor
    }

    public interface TweetPostListener {
        void onTweetPosted(Tweet tweet);
    }

    public TweetPostListener mListener;

    public static ComposeTweetFragment newInstance(User user,boolean isReply,String replyToHandle) {
        ComposeTweetFragment fragment = new ComposeTweetFragment();
        Bundle args = new Bundle();
        if ( user != null ) {
            args.putParcelable("user", Parcels.wrap(user));
        }
        if ( isReply && replyToHandle != null) {
            args.putString("replyTo",replyToHandle);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.fragment_compose_tweet, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentUser = (User) Parcels.unwrap(getArguments().getParcelable("user"));
        if (currentUser != null) {
            ImageView ivUserPic = (ImageView)view.findViewById(R.id.ivUserPic);
            Glide.with(getContext()).load(currentUser.getProfileImageUrl())
                    .bitmapTransform(new CropCircleTransformation(getContext()))
                    .into(ivUserPic);
        }
        etComposeTweet = (EditText)view.findViewById(R.id.etComposeTweet);
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.composeToolbar);
        final TextView tvCharCount = (TextView) toolbar.findViewById(R.id.tvCharCount);

        btnPost = (Button)view.findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postTweet(view);
            }
        });

        etComposeTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String tweetText = etComposeTweet.getText().toString();
                int count = tweetText.length();

                int remainingCount = 140 - count;
                tvCharCount.setText(Integer.toString(remainingCount));

                if ( remainingCount < 0 ) {
                    //change the color to red
                    tvCharCount.setTextColor(getResources().getColor(R.color.tweetOverCountColor));
                    btnPost.setEnabled(false);

                }
                else {
                    tvCharCount.setTextColor(getResources().getColor(R.color.tweetCharCountColor));
                    btnPost.setEnabled(true);
                }

            }
        });




    }

    public void postTweet(View view) {
        String content = etComposeTweet.getText().toString();
        TwitterManager.getSharedInstance().postTweet(getContext(), content, new TweetResponseHandler() {
            @Override
            public void onTweetPosted(boolean isSuccess, Tweet tweet) {
               if ( mListener != null ) {
                   mListener.onTweetPosted(tweet);
                   getDialog().dismiss();

               }
            }
        });
    }
}
