package com.codepath.apps.neattweet.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.neattweet.R;

public class HomeTimelineFragment extends TweetBaseFragment  {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listMode = ListMode.ListModeHomeTimeline;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        //inflate menu
        return inflater.inflate(R.layout.fragment_timeline,parent,false);
    }

}
