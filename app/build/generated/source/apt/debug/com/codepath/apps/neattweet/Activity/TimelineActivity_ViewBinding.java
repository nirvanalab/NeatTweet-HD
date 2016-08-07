// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.neattweet.Activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.codepath.apps.neattweet.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class TimelineActivity_ViewBinding<T extends TimelineActivity> implements Unbinder {
  protected T target;

  public TimelineActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.rvTimeline = finder.findRequiredViewAsType(source, R.id.rvTimeline, "field 'rvTimeline'", RecyclerView.class);
    target.swipeContainer = finder.findRequiredViewAsType(source, R.id.swipeContainer, "field 'swipeContainer'", SwipeRefreshLayout.class);
    target.fabAddTweet = finder.findRequiredViewAsType(source, R.id.fabAddTweet, "field 'fabAddTweet'", FloatingActionButton.class);
    target.toolbar = finder.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.rvTimeline = null;
    target.swipeContainer = null;
    target.fabAddTweet = null;
    target.toolbar = null;

    this.target = null;
  }
}
