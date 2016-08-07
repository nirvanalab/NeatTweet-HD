package com.codepath.apps.neattweet.ViewHolder;

import android.view.View;
import android.webkit.WebView;

import com.codepath.apps.neattweet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vidhurvoora on 8/6/16.
 */
public class TweetWebviewViewHolder extends TweetTextViewHolder {
    @BindView(R.id.wvTweetWebCard) WebView wvTweetWebCard;

    public TweetWebviewViewHolder(View view) {
        super(view);
        ButterKnife.bind(this,view);
    }

    public WebView getWvTweetWebCard() {
        return wvTweetWebCard;
    }
}
