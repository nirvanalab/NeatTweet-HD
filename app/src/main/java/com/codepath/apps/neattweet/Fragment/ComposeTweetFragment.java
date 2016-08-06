package com.codepath.apps.neattweet.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.neattweet.Manager.TweetResponseHandler;
import com.codepath.apps.neattweet.Manager.TwitterManager;
import com.codepath.apps.neattweet.Models.Tweet;
import com.codepath.apps.neattweet.R;


/**
 *
 */
public class ComposeTweetFragment extends android.support.v4.app.DialogFragment {

    EditText etComposeTweet;
    Button btnPost;

    public ComposeTweetFragment() {
        // Required empty public constructor
    }

    public interface TweetPostListener {
        void onTweetPosted(Tweet tweet);
    }

    public TweetPostListener mListener;

    public static ComposeTweetFragment newInstance() {
        ComposeTweetFragment fragment = new ComposeTweetFragment();
        Bundle args = new Bundle();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose_tweet, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etComposeTweet = (EditText)view.findViewById(R.id.etComposeTweet);
        btnPost = (Button)view.findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postTweet(view);
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
