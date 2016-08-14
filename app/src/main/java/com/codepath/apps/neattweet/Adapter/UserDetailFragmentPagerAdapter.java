package com.codepath.apps.neattweet.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.codepath.apps.neattweet.Fragment.FavsFragment;
import com.codepath.apps.neattweet.Fragment.FollowersListFragment;
import com.codepath.apps.neattweet.Fragment.FriendsListFragment;
import com.codepath.apps.neattweet.Fragment.RetweetsFragment;
import com.codepath.apps.neattweet.Fragment.UserListMode;
import com.codepath.apps.neattweet.Fragment.UserTimelineFragment;

/**
 * Created by vidhurvoora on 8/12/16.
 */
public class UserDetailFragmentPagerAdapter extends SmartFragmentStatePagerAdapter {

    private String user_id;
    private boolean shouldIncludeRetweets;
    public UserDetailFragmentPagerAdapter(FragmentManager fm,String userId,boolean includeRetweets) {
        super(fm);
        this.user_id = userId;
        shouldIncludeRetweets = includeRetweets;
    }

    @Override
    public Fragment getItem(int position) {

        if ( position == UserListMode.UserListModeTimeline.ordinal()){
            return UserTimelineFragment.newUserInstance(user_id);
        }
        else if (position == UserListMode.UserListModeFavs.ordinal()) {
            return FavsFragment.newUserInstance(user_id);
        }
        else if (position == UserListMode.UserListModeRetweets.ordinal()) {
            return RetweetsFragment.newUserInstance(user_id);
        }
        else if (position == UserListMode.UserListModeFriends.ordinal()) {
            return FriendsListFragment.newUserInstance(user_id);
        }
        else if (position == UserListMode.UserListModeFollowers.ordinal()) {
            return FollowersListFragment.newUserInstance(user_id);
        }
        return null;
    }

    @Override
    public int getCount() {

        int count ;
        if ( shouldIncludeRetweets) {
            count = UserListMode.UserListModeCount.ordinal();
        }
        else {
            //don't include retweets for other users
            count = UserListMode.UserListModeCount.ordinal()-1;
        }
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if ( position == UserListMode.UserListModeTimeline.ordinal()){
            return "User";
        }
        else if (position ==  UserListMode.UserListModeFavs.ordinal()) {
            return "Favs";
        }
        else if (position == UserListMode.UserListModeRetweets.ordinal()){
            return "Retweets";
        }
        else if (position == UserListMode.UserListModeFriends.ordinal()){
            return "Friends";
        }
        else if (position == UserListMode.UserListModeFollowers.ordinal()){
            return "Followers";
        }
        return null;
    }
}
