// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.neattweet.ViewHolder;

import butterknife.internal.Finder;
import com.codepath.apps.neattweet.R;
import com.yqritc.scalablevideoview.ScalableVideoView;
import java.lang.Object;
import java.lang.Override;

public class TweetVideoViewHolder_ViewBinding<T extends TweetVideoViewHolder> extends TweetTextViewHolder_ViewBinding<T> {
  public TweetVideoViewHolder_ViewBinding(T target, Finder finder, Object source) {
    super(target, finder, source);

    target.vvTweetVideo = finder.findRequiredViewAsType(source, R.id.vvTweetVideo, "field 'vvTweetVideo'", ScalableVideoView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    super.unbind();

    target.vvTweetVideo = null;
  }
}
