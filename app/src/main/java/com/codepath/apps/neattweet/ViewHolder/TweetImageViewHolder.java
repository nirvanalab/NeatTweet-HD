package com.codepath.apps.neattweet.ViewHolder;

import android.view.View;
import android.widget.ImageView;

import com.codepath.apps.neattweet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vidhurvoora on 8/6/16.
 */
public class TweetImageViewHolder extends TweetTextViewHolder {

    @BindView(R.id.ivTweetImage) ImageView ivTweetImage;

    public TweetImageViewHolder(View view) {
        super(view);
        ButterKnife.bind(this,view);
    }

    public ImageView getIvTweetImage() {
        return ivTweetImage;
    }
}
