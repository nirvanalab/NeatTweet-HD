package com.codepath.apps.neattweet.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.astuetz.PagerSlidingTabStrip;
import com.bumptech.glide.Glide;
import com.codepath.apps.neattweet.Adapter.TwitterFragmentPagerAdapter;
import com.codepath.apps.neattweet.Manager.TwitterManager;
import com.codepath.apps.neattweet.Manager.UserInfoResponseHandler;
import com.codepath.apps.neattweet.Models.User;
import com.codepath.apps.neattweet.R;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class TimelineActivity extends AppCompatActivity  {

    @BindView(R.id.drawerLayout)DrawerLayout drawerLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tabsTweet) PagerSlidingTabStrip tabsTweet;
    @BindView(R.id.viewPagerTweet) ViewPager viewPagerTweet ;
    @BindView(R.id.nvView) NavigationView nvView;
    ImageView profilePic;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);

        viewPagerTweet.setAdapter(new TwitterFragmentPagerAdapter(getSupportFragmentManager()));
        viewPagerTweet.setPageTransformer(true, new RotateUpTransformer());
        tabsTweet.setViewPager(viewPagerTweet);

        setSupportActionBar(toolbar);
        setupToolbar();

        profilePic = (ImageView) toolbar.findViewById(R.id.tbUserPic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        nvView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if ( item.getItemId() == R.id.nav_profile) {
                    Intent intent = new Intent(getApplicationContext(),UserDetailActivity.class);
                    //include retweets of the current user
                    if ( currentUser != null ) {
                        intent.putExtra("user", Parcels.wrap(currentUser));
                        intent.putExtra("shouldIncludeRetweets",true);
                        startActivity(intent);
                        return  true;
                    }
                    return false;

                }
                return false;
            }
        });


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

                    //setup the nav view header
                    View headerLayout = nvView.getHeaderView(0);
                    if ( headerLayout != null ) {
                        ImageView navProfilePic = (ImageView) headerLayout.findViewById(R.id.navTvProfilePic);
                        Glide.with(getApplicationContext()).load(user.getProfileImageUrl())
                                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                                .into(navProfilePic);
                        TextView navTvFullname = (TextView)headerLayout.findViewById(R.id.navTvFullName);
                        navTvFullname.setText(user.getName());
                        TextView navTvUsername = (TextView)headerLayout.findViewById(R.id.navTvUsername);
                        navTvUsername.setText(screenName);
                    }
                }
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


}
