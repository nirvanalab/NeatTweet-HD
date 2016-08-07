package com.codepath.apps.neattweet.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.neattweet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vidhurvoora on 8/6/16.
 */
public class TweetTextViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ivProfilePic) ImageView ivProfilePic;
    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.tvUsername) TextView tvUsername;
    @BindView(R.id.tvTweetContent) TextView tvTweetContent;
    @BindView(R.id.tvTimeSince) TextView tvTimeSince;

    public TweetTextViewHolder(View view) {
        super(view);
        ButterKnife.bind(this,view);

    }

    public ImageView getIvProfilePic() {
        return ivProfilePic;
    }

    public TextView getTvName() {
        return tvName;
    }

    public TextView getTvUsername() {
        return tvUsername;
    }

    public TextView getTvTweetContent() {
        return tvTweetContent;
    }

    public TextView getTvTimeSince() {
        return tvTimeSince;
    }

    public void setIvProfilePic(ImageView ivProfilePic) {
        this.ivProfilePic = ivProfilePic;
    }

    public void setTvName(TextView tvName) {
        this.tvName = tvName;
    }

    public void setTvUsername(TextView tvUsername) {
        this.tvUsername = tvUsername;
    }

    public void setTvTweetContent(TextView tvTweetContent) {
        this.tvTweetContent = tvTweetContent;
    }

    public void setTvTimeSince(TextView tvTimeSince) {
        this.tvTimeSince = tvTimeSince;
    }
}
