package com.codepath.apps.neattweet.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.neattweet.R;

/**
 * Created by vidhurvoora on 8/11/16.
 */
public class UserTimelineFragment extends TweetBaseFragment {

    public static UserTimelineFragment newUserInstance(String userId) {
        UserTimelineFragment userFragment =  new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("userId", userId);
        userFragment.setArguments(args);
        return userFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listMode = ListMode.ListModeUserTimeline;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        //inflate menu
        return inflater.inflate(R.layout.fragment_timeline,parent,false);
    }
}
