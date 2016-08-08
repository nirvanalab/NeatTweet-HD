// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.neattweet.Activity;

import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.codepath.apps.neattweet.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class LoginActivity_ViewBinding<T extends LoginActivity> implements Unbinder {
  protected T target;

  public LoginActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.ivTweetBird = finder.findRequiredViewAsType(source, R.id.ivTweetBird, "field 'ivTweetBird'", ImageView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.ivTweetBird = null;

    this.target = null;
  }
}
