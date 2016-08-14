// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.neattweet.Activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.neattweet.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class UserDetailActivity_ViewBinding<T extends UserDetailActivity> implements Unbinder {
  protected T target;

  public UserDetailActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.ivProfilePic = finder.findRequiredViewAsType(source, R.id.ivProfilePic, "field 'ivProfilePic'", ImageView.class);
    target.tvFullname = finder.findRequiredViewAsType(source, R.id.tvFullname, "field 'tvFullname'", TextView.class);
    target.tvUsername = finder.findRequiredViewAsType(source, R.id.tvUsername, "field 'tvUsername'", TextView.class);
    target.tvTagline = finder.findRequiredViewAsType(source, R.id.tvTagline, "field 'tvTagline'", TextView.class);
    target.tvLocation = finder.findRequiredViewAsType(source, R.id.tvLocation, "field 'tvLocation'", TextView.class);
    target.tvFollowers = finder.findRequiredViewAsType(source, R.id.tvFollowers, "field 'tvFollowers'", TextView.class);
    target.tvFollowing = finder.findRequiredViewAsType(source, R.id.tvFollowing, "field 'tvFollowing'", TextView.class);
    target.tabsDetail = finder.findRequiredViewAsType(source, R.id.tabsDetail, "field 'tabsDetail'", PagerSlidingTabStrip.class);
    target.viewPagerDetail = finder.findRequiredViewAsType(source, R.id.viewPagerDetail, "field 'viewPagerDetail'", ViewPager.class);
    target.ivBackdrop = finder.findRequiredViewAsType(source, R.id.ivBackdrop, "field 'ivBackdrop'", ImageView.class);
    target.rootLayout = finder.findRequiredViewAsType(source, R.id.main_content, "field 'rootLayout'", CoordinatorLayout.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.ivProfilePic = null;
    target.tvFullname = null;
    target.tvUsername = null;
    target.tvTagline = null;
    target.tvLocation = null;
    target.tvFollowers = null;
    target.tvFollowing = null;
    target.tabsDetail = null;
    target.viewPagerDetail = null;
    target.ivBackdrop = null;
    target.rootLayout = null;

    this.target = null;
  }
}
