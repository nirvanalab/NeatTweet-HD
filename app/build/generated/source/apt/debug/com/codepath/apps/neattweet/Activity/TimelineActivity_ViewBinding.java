// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.neattweet.Activity;

import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.neattweet.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class TimelineActivity_ViewBinding<T extends TimelineActivity> implements Unbinder {
  protected T target;

  public TimelineActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.drawerLayout = finder.findRequiredViewAsType(source, R.id.drawerLayout, "field 'drawerLayout'", DrawerLayout.class);
    target.toolbar = finder.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.tabsTweet = finder.findRequiredViewAsType(source, R.id.tabsTweet, "field 'tabsTweet'", PagerSlidingTabStrip.class);
    target.viewPagerTweet = finder.findRequiredViewAsType(source, R.id.viewPagerTweet, "field 'viewPagerTweet'", ViewPager.class);
    target.nvView = finder.findRequiredViewAsType(source, R.id.nvView, "field 'nvView'", NavigationView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.drawerLayout = null;
    target.toolbar = null;
    target.tabsTweet = null;
    target.viewPagerTweet = null;
    target.nvView = null;

    this.target = null;
  }
}
