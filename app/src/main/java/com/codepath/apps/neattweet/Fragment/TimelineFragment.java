package com.codepath.apps.neattweet.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.neattweet.Adapter.TwitterTimelineAdapter;
import com.codepath.apps.neattweet.Manager.TwitterManager;
import com.codepath.apps.neattweet.Models.Tweet;
import com.codepath.apps.neattweet.Models.TwitterTimelineResponseHandler;
import com.codepath.apps.neattweet.Models.User;
import com.codepath.apps.neattweet.R;
import com.codepath.apps.neattweet.ThirdPartyDecoration.DividerItemDecoration;
import com.codepath.apps.neattweet.ThirdPartyDecoration.InsetDecoration;
import com.codepath.apps.neattweet.Utility.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;

import butterknife.BindView;


public class TimelineFragment extends Fragment implements ComposeTweetFragment.TweetPostListener {


    ArrayList<Tweet> timeline;
    TwitterTimelineAdapter timelineAdapter;
    @BindView(R.id.rvTimeline)
    RecyclerView rvTimeline;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.fabAddTweet)
    FloatingActionButton fabAddTweet;
    LinearLayoutManager layoutManager;
    int fetchCount = 25;
    User currentUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        //inflate menu
        return inflater.inflate(R.layout.fragment_timeline,parent,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        timeline = new ArrayList<Tweet>();
        currentUser = null;
        timelineAdapter = new TwitterTimelineAdapter(getContext(),timeline);
        rvTimeline = (RecyclerView)view.findViewById(R.id.rvTimeline);
        swipeContainer = (SwipeRefreshLayout)view.findViewById(R.id.swipeContainer);
        fabAddTweet = (FloatingActionButton)view.findViewById(R.id.fabAddTweet);

        rvTimeline.setAdapter(timelineAdapter);
        timelineAdapter.setOnTweetReplyClickListener(new TwitterTimelineAdapter.OnTweetReplyClickListener() {
            @Override
            public void onTweetReplyClicked(Tweet tweet) {
                //TODO onReplyTweet(tweet);
            }
        });

        setupTimelineView();
        setupSwipeRefresh();

        //first load
        fetchTweets(fetchCount);
    }

    private void setupTimelineView() {

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.scrollToPosition(0);
        rvTimeline.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        rvTimeline.addItemDecoration(itemDecoration);
        RecyclerView.ItemDecoration insetDecoration = new InsetDecoration(getContext());
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

    //TODO enable this
    //click event handler for new tweet
//    public void onAddNewTweet(View view) {
//        FragmentManager fm = getContext().getSupportFragmentManager();
//        ComposeTweetFragment composeTweetFragment = ComposeTweetFragment.newInstance(currentUser,false,null);
//        composeTweetFragment.mListener = this;
//        composeTweetFragment.show(fm,"compose fragment");
//
//    }
//
//    public void onReplyTweet(Tweet tweet){
//        FragmentManager fm = getSupportFragmentManager();
//        ComposeTweetFragment composeTweetFragment = ComposeTweetFragment.newInstance(currentUser,true,tweet.getUser());
//        composeTweetFragment.mListener = this;
//        composeTweetFragment.show(fm,"compose fragment");
//    }

    @Override
    public void onTweetPosted(Tweet tweet) {
        //update the existing model
        timeline.add(0,tweet);
        timelineAdapter.notifyItemRangeInserted(0,1);
        layoutManager.scrollToPosition(0);
    }


}
