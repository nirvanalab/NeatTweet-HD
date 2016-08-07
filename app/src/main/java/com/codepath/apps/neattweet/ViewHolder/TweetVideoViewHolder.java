package com.codepath.apps.neattweet.ViewHolder;

import android.view.View;

import com.codepath.apps.neattweet.R;
import com.yqritc.scalablevideoview.ScalableVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;




/**
 * Created by vidhurvoora on 8/6/16.
 */
public class TweetVideoViewHolder extends TweetTextViewHolder {

    @BindView(R.id.vvTweetVideo) ScalableVideoView vvTweetVideo;


    public TweetVideoViewHolder(View view) {
        super(view);
        ButterKnife.bind(this,view);
    }


    public ScalableVideoView getVvTweetVideo() {
        return vvTweetVideo;
    }
}
