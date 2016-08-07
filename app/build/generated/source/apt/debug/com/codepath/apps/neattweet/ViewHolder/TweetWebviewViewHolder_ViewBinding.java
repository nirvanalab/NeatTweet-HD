// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.neattweet.ViewHolder;

import android.webkit.WebView;
import butterknife.internal.Finder;
import com.codepath.apps.neattweet.R;
import java.lang.Object;
import java.lang.Override;

public class TweetWebviewViewHolder_ViewBinding<T extends TweetWebviewViewHolder> extends TweetTextViewHolder_ViewBinding<T> {
  public TweetWebviewViewHolder_ViewBinding(T target, Finder finder, Object source) {
    super(target, finder, source);

    target.wvTweetWebCard = finder.findRequiredViewAsType(source, R.id.wvTweetWebCard, "field 'wvTweetWebCard'", WebView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    super.unbind();

    target.wvTweetWebCard = null;
  }
}
