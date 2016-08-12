package com.codepath.apps.neattweet.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.apps.neattweet.Fragment.DirectMessagesFragment;
import com.codepath.apps.neattweet.Fragment.HomeTimelineFragment;
import com.codepath.apps.neattweet.Fragment.ListMode;
import com.codepath.apps.neattweet.Fragment.MentionsFragment;
import com.codepath.apps.neattweet.Fragment.UserTimelineFragment;

/**
 * Created by vidhurvoora on 8/11/16.
 */
public class TwitterFragmentPagerAdapter extends FragmentPagerAdapter {

    public TwitterFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if ( position == ListMode.ListModeHomeTimeline.ordinal()) {
            return new HomeTimelineFragment();
        }
        else if ( position == ListMode.ListModeMentions.ordinal()){
            return new MentionsFragment();
        }
        else if ( position == ListMode.ListModeDirectMessages.ordinal()){
            return new DirectMessagesFragment();
        }
        else if ( position == ListMode.ListModeUserTimeline.ordinal()){
            return new UserTimelineFragment();
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
