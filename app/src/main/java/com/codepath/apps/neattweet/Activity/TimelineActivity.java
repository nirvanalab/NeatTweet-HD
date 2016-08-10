package com.codepath.apps.neattweet.Activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.neattweet.Manager.TwitterManager;
import com.codepath.apps.neattweet.Manager.UserInfoResponseHandler;
import com.codepath.apps.neattweet.Models.User;
import com.codepath.apps.neattweet.R;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class TimelineActivity extends AppCompatActivity  {


    @BindView(R.id.toolbar) Toolbar toolbar;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //TODO using just butterknife is causing a crash..figure it out
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupToolbar();



    }

    // Menu icons are inflated just as they were with actionbar
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }


    private void setupToolbar() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getCurrentUser();
    }

    public void getCurrentUser() {
        TwitterManager.getSharedInstance().getCurrentUser(new UserInfoResponseHandler() {
            @Override
            public void onUserInfo(boolean isSuccess, User user) {
                if (isSuccess) {
                    currentUser = user;
                    ImageView profileView = (ImageView) toolbar.findViewById(R.id.tbUserPic);
                    Glide.with(getApplicationContext()).load(user.getProfileImageUrl())
                            .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                            .into(profileView);
                    TextView tbUsername = (TextView)toolbar.findViewById(R.id.tbUsername);
                    String screenName = "@"+user.getScreenName();
                    tbUsername.setText(screenName);
                }
            }
        });
    }


}
