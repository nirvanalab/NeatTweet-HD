package com.codepath.apps.neattweet.Activity;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.neattweet.Adapter.TwitterTimelineAdapter;
import com.codepath.apps.neattweet.Fragment.ComposeTweetFragment;
import com.codepath.apps.neattweet.Manager.TwitterManager;
import com.codepath.apps.neattweet.Manager.UserInfoResponseHandler;
import com.codepath.apps.neattweet.Models.Tweet;
import com.codepath.apps.neattweet.Models.TwitterTimelineResponseHandler;
import com.codepath.apps.neattweet.Models.User;
import com.codepath.apps.neattweet.R;
import com.codepath.apps.neattweet.ThirdPartyDecoration.DividerItemDecoration;
import com.codepath.apps.neattweet.ThirdPartyDecoration.InsetDecoration;
import com.codepath.apps.neattweet.Utility.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class TimelineActivity extends AppCompatActivity implements ComposeTweetFragment.TweetPostListener {

    ArrayList<Tweet> timeline;
    TwitterTimelineAdapter timelineAdapter;
    @BindView(R.id.rvTimeline) RecyclerView rvTimeline;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.fabAddTweet) FloatingActionButton fabAddTweet;
    @BindView(R.id.toolbar) Toolbar toolbar;
    LinearLayoutManager layoutManager;
    int fetchCount = 25;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //TODO using just butterknife is causing a crash..figure it out
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupToolbar();

        timeline = new ArrayList<Tweet>();
        currentUser = null;
        timelineAdapter = new TwitterTimelineAdapter(this,timeline);
        rvTimeline = (RecyclerView)findViewById(R.id.rvTimeline);
        swipeContainer = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        fabAddTweet = (FloatingActionButton)findViewById(R.id.fabAddTweet);

        rvTimeline.setAdapter(timelineAdapter);
        timelineAdapter.setOnTweetReplyClickListener(new TwitterTimelineAdapter.OnTweetReplyClickListener() {
            @Override
            public void onTweetReplyClicked(Tweet tweet) {
                onReplyTweet(tweet);
            }
        });

        setupTimelineView();
        setupSwipeRefresh();

        //first load
        fetchTweets(fetchCount);

    }

    // Menu icons are inflated just as they were with actionbar
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    private void setupTimelineView() {

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.scrollToPosition(0);
        rvTimeline.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvTimeline.addItemDecoration(itemDecoration);
        RecyclerView.ItemDecoration insetDecoration = new InsetDecoration(this);
        rvTimeline.addItemDecoration(insetDecoration);
        rvTimeline.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                fetchTweets(fetchCount);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    fabAddTweet.hide();
                }
                else {
                    fabAddTweet.show();
                }
            }
        });
    }

    private void setupSwipeRefresh() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearTweets();
                fetchTweets(fetchCount);
            }
        });
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
                }
            }
        });
    }

    private void fetchTweets(int limit) {

        int existingCount = timelineAdapter.getItemCount();
        String maxId = null;
        if ( existingCount > 0 ) {
            //fetch last item
            Tweet lastTweet = timeline.get(existingCount-1);
            maxId = lastTweet.getId();
        }
        TwitterManager.getSharedInstance().getTimelineTweets(limit,maxId,new TwitterTimelineResponseHandler() {
            @Override
            public void timelineResults(boolean isSuccess, ArrayList<Tweet> tweets) {
                if (isSuccess ) {
                    // record this value before making any changes to the existing list
                    int curSize = timelineAdapter.getItemCount();
                    timeline.addAll(tweets);
                    timelineAdapter.notifyItemRangeInserted(curSize,tweets.size());
                    swipeContainer.setRefreshing(false);
                }
            }
        });
    }

    private void clearTweets() {
        int existingCount = timelineAdapter.getItemCount();
        timeline.clear();
        timelineAdapter.notifyItemRangeRemoved(0,existingCount);
    }

    //click event handler for new tweet
    public void onAddNewTweet(View view) {
        FragmentManager fm = getSupportFragmentManager();
        ComposeTweetFragment composeTweetFragment = ComposeTweetFragment.newInstance(currentUser,false,null);
        composeTweetFragment.mListener = this;
        composeTweetFragment.show(fm,"compose fragment");

    }

    public void onReplyTweet(Tweet tweet){
        FragmentManager fm = getSupportFragmentManager();
        ComposeTweetFragment composeTweetFragment = ComposeTweetFragment.newInstance(currentUser,true,tweet.getUser());
        composeTweetFragment.mListener = this;
        composeTweetFragment.show(fm,"compose fragment");
    }

    @Override
    public void onTweetPosted(Tweet tweet) {
        //update the existing model
        timeline.add(0,tweet);
        timelineAdapter.notifyItemRangeInserted(0,1);
        layoutManager.scrollToPosition(0);
    }
}
