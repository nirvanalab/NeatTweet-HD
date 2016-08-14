package com.codepath.apps.neattweet.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.neattweet.R;

/**
 * Created by vidhurvoora on 8/14/16.
 */
public class FollowersListFragment extends TweetBaseFragment {

    public static FollowersListFragment newUserInstance(String userId) {
        FollowersListFragment followersListFragment =  new FollowersListFragment();
        Bundle args = new Bundle();
        args.putString("userId", userId);
        followersListFragment.setArguments(args);
        return followersListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userListMode = UserListMode.UserListModeFollowers;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        //inflate menu
        return inflater.inflate(R.layout.fragment_timeline,parent,false);
    }

}
