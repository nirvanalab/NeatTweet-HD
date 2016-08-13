package com.codepath.apps.neattweet.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.neattweet.R;

/**
 * Created by vidhurvoora on 8/12/16.
 */
public class FavsFragment extends TweetBaseFragment {

    public static FavsFragment newUserInstance(String userId) {
        FavsFragment favsFragment =  new FavsFragment();
        Bundle args = new Bundle();
        args.putString("userId", userId);
        favsFragment.setArguments(args);
        return favsFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userListMode = UserListMode.UserListModeFavs;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        //inflate menu
        return inflater.inflate(R.layout.fragment_timeline,parent,false);
    }

}
