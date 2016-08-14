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
public class FriendsListFragment extends TweetBaseFragment {

    public static FriendsListFragment newUserInstance(String userId) {
        FriendsListFragment friendsListFragment =  new FriendsListFragment();
        Bundle args = new Bundle();
        args.putString("userId", userId);
        friendsListFragment.setArguments(args);
        return friendsListFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userListMode = UserListMode.UserListModeFriends;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        //inflate menu
        return inflater.inflate(R.layout.fragment_timeline,parent,false);
    }


}
