// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.neattweet.ViewHolder;

import android.widget.ImageView;
import butterknife.internal.Finder;
import com.codepath.apps.neattweet.R;
import java.lang.Object;
import java.lang.Override;

public class TweetImageViewHolder_ViewBinding<T extends TweetImageViewHolder> extends TweetTextViewHolder_ViewBinding<T> {
  public TweetImageViewHolder_ViewBinding(T target, Finder finder, Object source) {
    super(target, finder, source);

    target.ivTweetImage = finder.findRequiredViewAsType(source, R.id.ivTweetImage, "field 'ivTweetImage'", ImageView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    super.unbind();

    target.ivTweetImage = null;
  }
}
