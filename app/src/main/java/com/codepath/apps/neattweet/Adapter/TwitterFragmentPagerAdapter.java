package com.codepath.apps.neattweet.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.codepath.apps.neattweet.Fragment.DirectMessagesFragment;
import com.codepath.apps.neattweet.Fragment.HomeTimelineFragment;
import com.codepath.apps.neattweet.Fragment.ListMode;
import com.codepath.apps.neattweet.Fragment.MentionsFragment;
import com.codepath.apps.neattweet.Fragment.TweetBaseFragment;
import com.codepath.apps.neattweet.Fragment.UserTimelineFragment;

/**
 * Created by vidhurvoora on 8/11/16.
 */
public class TwitterFragmentPagerAdapter extends SmartFragmentStatePagerAdapter {

    TweetBaseFragment.ComposeTweetActionListener actionListener;
    public TwitterFragmentPagerAdapter(FragmentManager fm, TweetBaseFragment.ComposeTweetActionListener listener) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if ( position == ListMode.ListModeHomeTimeline.ordinal()) {
            HomeTimelineFragment timelineFragment = new HomeTimelineFragment();
            timelineFragment.actionListener = actionListener;
            return timelineFragment;
        }
        else if ( position == ListMode.ListModeMentions.ordinal()){
            MentionsFragment mentionsFragment = new MentionsFragment();
            mentionsFragment.actionListener = actionListener;
            return mentionsFragment;
        }
        else if ( position == ListMode.ListModeDirectMessages.ordinal()){
            DirectMessagesFragment messagesFragment =  new DirectMessagesFragment();
            messagesFragment.actionListener = actionListener;
            return messagesFragment;
        }
        else if ( position == ListMode.ListModeUserTimeline.ordinal()){
            UserTimelineFragment userTimelineFragment =  new UserTimelineFragment();
            userTimelineFragment.actionListener = actionListener;
            return userTimelineFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        int count =  ListMode.ListModeCount.ordinal();
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if ( position == ListMode.ListModeHomeTimeline.ordinal()) {
            return "Home";
        }
        else if ( position == ListMode.ListModeMentions.ordinal()){
            return "Mentions";
        }
        else if ( position == ListMode.ListModeDirectMessages.ordinal()){
            return "Messages";
        }
        else if ( position == ListMode.ListModeUserTimeline.ordinal()){
            return "User";
        }
        return null;
    }
}
