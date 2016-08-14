package com.codepath.apps.neattweet.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.TabletTransformer;
import com.astuetz.PagerSlidingTabStrip;
import com.bumptech.glide.Glide;
import com.codepath.apps.neattweet.Adapter.TwitterFragmentPagerAdapter;
import com.codepath.apps.neattweet.Fragment.ComposeTweetFragment;
import com.codepath.apps.neattweet.Fragment.HomeTimelineFragment;
import com.codepath.apps.neattweet.Fragment.TweetBaseFragment;
import com.codepath.apps.neattweet.Manager.TwitterManager;
import com.codepath.apps.neattweet.Manager.UserInfoResponseHandler;
import com.codepath.apps.neattweet.Models.Banner;
import com.codepath.apps.neattweet.Models.BannerResponseHandler;
import com.codepath.apps.neattweet.Models.Tweet;
import com.codepath.apps.neattweet.Models.User;
import com.codepath.apps.neattweet.R;
import com.codepath.apps.neattweet.Utility.UtilityManager;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class TimelineActivity extends AppCompatActivity implements TweetBaseFragment.ComposeTweetActionListener {

    @BindView(R.id.drawerLayout)DrawerLayout drawerLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tabsTweet) PagerSlidingTabStrip tabsTweet;
    @BindView(R.id.viewPagerTweet) ViewPager viewPagerTweet ;
    @BindView(R.id.nvView) NavigationView nvView;
    ImageView profilePic;
    User currentUser;
    TwitterFragmentPagerAdapter pagerAdapter;
    ImageButton homeButton;

    private int tabIcons[] = {R.drawable.home_icon, R.drawable.notification_icon, R.drawable.person_icon,R.drawable.message_icon};
    private int tabIconsSelected[] = {R.drawable.home_icon_selected
                                        ,R.drawable.notification_icon_selected
                                        ,R.drawable.person_icon_selected
                                        ,R.drawable.message_icon_selected
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);

        pagerAdapter = new TwitterFragmentPagerAdapter(getSupportFragmentManager(),this);
        viewPagerTweet.setAdapter(pagerAdapter);
//        viewPagerTweet.setPageTransformer(true, new ScaleInOutTransformer());
//        viewPagerTweet.setPageTransformer(true, new StackTransformer());
        viewPagerTweet.setPageTransformer(true, new TabletTransformer());
//        viewPagerTweet.setPageTransformer(true, new ForegroundToBackgroundTransformer());
//        viewPagerTweet.setPageTransformer(true, new DepthPageTransformer());
//        viewPagerTweet.setPageTransformer(true, new FlipHorizontalTransformer());
//        viewPagerTweet.setPageTransformer(true, new FlipVerticalTransformer());
//        viewPagerTweet.setPageTransformer(true, new ZoomOutSlideTransformer());
//        viewPagerTweet.setPageTransformer(true, new CubeOutTransformer());
//        viewPagerTweet.setPageTransformer(true, new CubeInTransformer());
//        viewPagerTweet.setPageTransformer(true,new AccordionTransformer());
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

        // Attach the page change listener inside the activity
        viewPagerTweet.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                LinearLayout view = (LinearLayout) tabsTweet.getChildAt(0);
                View itemView = (View) view.getChildAt(position);
                if ( itemView.getClass() == ImageButton.class) {
                    resetIcons();
                    ImageButton imageButton = (ImageButton)itemView;
                    imageButton.setImageDrawable(getResources().getDrawable(tabIconsSelected[position]));
                }
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
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

    private void resetIcons(){
        LinearLayout view = (LinearLayout) tabsTweet.getChildAt(0);
        for (int i=0; i<4;i++) {
            View itemView = (View) view.getChildAt(i);
            if ( itemView.getClass() == ImageButton.class) {
                ImageButton imageButton = (ImageButton)itemView;
                imageButton.setImageDrawable(getResources().getDrawable(tabIcons[i]));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //check if internet is there
        if (!UtilityManager.getSharedInstance().isNetworkAvailable(this)) {
            Snackbar.make(drawerLayout, R.string.no_internet_message, Snackbar.LENGTH_INDEFINITE).show();
        }

    }

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
                    final View headerLayout = nvView.getHeaderView(0);
                    if ( headerLayout != null ) {
                        ImageView navProfilePic = (ImageView) headerLayout.findViewById(R.id.navTvProfilePic);
                        Glide.with(getApplicationContext()).load(user.getProfileImageUrl())
                                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                                .into(navProfilePic);
                        TextView navTvFullname = (TextView)headerLayout.findViewById(R.id.navTvFullName);
                        navTvFullname.setText(user.getName());
                        TextView navTvUsername = (TextView)headerLayout.findViewById(R.id.navTvUsername);
                        navTvUsername.setText(screenName);

                        //get backdrop
                        TwitterManager.getSharedInstance().getBanner(user.getId(), new BannerResponseHandler() {
                            @Override
                            public void bannerResults(boolean isSuccess, Banner banner) {
                                if (isSuccess) {
                                    ImageView ivHeaderBackdrop = (ImageView)headerLayout.findViewById(R.id.ivHeaderBackdrop);
                                    Glide.with(getApplicationContext()).load(banner.getUrl())
                                            .into(ivHeaderBackdrop);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

//    public void onAddNewTweet(View view) {
//        FragmentManager fm = getSupportFragmentManager();
//        ComposeTweetFragment composeTweetFragment = ComposeTweetFragment.newInstance(currentUser,false,null);
//        composeTweetFragment.mListener = (HomeTimelineFragment) pagerAdapter.getItem(0);
//        composeTweetFragment.show(fm,"compose fragment");
//
//    }
//
//    public void onReplyTweet(Tweet tweet){
//        FragmentManager fm = getSupportFragmentManager();
//        ComposeTweetFragment composeTweetFragment = ComposeTweetFragment.newInstance(currentUser,true,tweet.getUser());
//        composeTweetFragment.mListener = (HomeTimelineFragment) pagerAdapter.getItem(0);;
//        composeTweetFragment.show(fm,"compose fragment");
//    }

    @Override
    public void onAddNewTweetInitiated() {
        FragmentManager fm = getSupportFragmentManager();
        ComposeTweetFragment composeTweetFragment = ComposeTweetFragment.newInstance(currentUser,false,null);
        composeTweetFragment.mListener = (HomeTimelineFragment) pagerAdapter.getRegisteredFragment(0);
        composeTweetFragment.show(fm,"compose fragment");
    }

    @Override
    public void onReplyTweetInitiated(Tweet tweet) {
        FragmentManager fm = getSupportFragmentManager();
        ComposeTweetFragment composeTweetFragment = ComposeTweetFragment.newInstance(currentUser,true,tweet.getUser());
        composeTweetFragment.mListener = (HomeTimelineFragment) pagerAdapter.getRegisteredFragment(0);
        composeTweetFragment.show(fm,"compose fragment");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tweet_search_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        setupSearch(searchItem);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setGravity(Gravity.CENTER);

        return true;
    }

    private void setupSearch(MenuItem searchItem){
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                LinearLayout view = (LinearLayout) tabsTweet.getChildAt(0);
                View itemView = view.getChildAt(0);
                if (itemView.getClass() == ImageButton.class) {
                    homeButton = (ImageButton)itemView;
                }
                view.removeView(itemView);
                TextView textView = new TextView(getApplicationContext());
                textView.setTextColor(getResources().getColor(R.color.tweetCharCountColor));
                textView.setTextSize(20);
                textView.setWidth(250);
                view.addView(textView,0);
                textView.setText("Search Results: "+query);

                HomeTimelineFragment homeTimelineFragment = (HomeTimelineFragment) pagerAdapter.getRegisteredFragment(0);
                homeTimelineFragment.performSearch(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if ( newText.isEmpty() ) {
                    //clear
                    refreshHomeTimeline();
                }
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                refreshHomeTimeline();
                return true;
            }
        });
    }

    void refreshHomeTimeline(){
        LinearLayout view = (LinearLayout) tabsTweet.getChildAt(0);
        View itemView = (View) view.getChildAt(0);
        if (itemView.getClass() == TextView.class) {
            view.removeView(itemView);

            if (homeButton != null) {
                view.addView(homeButton,0);
            }
            else {
                ImageButton imageButton = new ImageButton(getApplicationContext());
                imageButton.setImageDrawable(getResources().getDrawable(R.drawable.home_icon));
                imageButton.setScaleType(ImageView.ScaleType.CENTER);
                view.addView(imageButton,0);
            }


        }
        else if (itemView.getClass() == ImageButton.class) {
            homeButton = (ImageButton)homeButton;
        }
        HomeTimelineFragment homeTimelineFragment = (HomeTimelineFragment) pagerAdapter.getRegisteredFragment(0);
        homeTimelineFragment.clearSearch();


    }

}
