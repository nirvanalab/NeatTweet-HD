// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.neattweet.ViewHolder;

import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.codepath.apps.neattweet.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class TweetTextViewHolder_ViewBinding<T extends TweetTextViewHolder> implements Unbinder {
  protected T target;

  public TweetTextViewHolder_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.ivProfilePic = finder.findRequiredViewAsType(source, R.id.ivProfilePic, "field 'ivProfilePic'", ImageView.class);
    target.tvName = finder.findRequiredViewAsType(source, R.id.tvName, "field 'tvName'", TextView.class);
    target.tvUsername = finder.findRequiredViewAsType(source, R.id.tvUsername, "field 'tvUsername'", TextView.class);
    target.tvTweetContent = finder.findRequiredViewAsType(source, R.id.tvTweetContent, "field 'tvTweetContent'", TextView.class);
    target.tvTimeSince = finder.findRequiredViewAsType(source, R.id.tvTimeSince, "field 'tvTimeSince'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.ivProfilePic = null;
    target.tvName = null;
    target.tvUsername = null;
    target.tvTweetContent = null;
    target.tvTimeSince = null;

    this.target = null;
  }
}
