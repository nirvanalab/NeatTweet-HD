package com.codepath.apps.neattweet.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.codepath.apps.neattweet.Activity.TimelineActivity;
import com.codepath.apps.neattweet.Adapter.TwitterTimelineAdapter;
import com.codepath.apps.neattweet.Manager.TwitterManager;
import com.codepath.apps.neattweet.Manager.UserFriendsFollowersResponseHandler;
import com.codepath.apps.neattweet.Models.Tweet;
import com.codepath.apps.neattweet.Models.TweetType;
import com.codepath.apps.neattweet.Models.TwitterTimelineResponseHandler;
import com.codepath.apps.neattweet.Models.User;
import com.codepath.apps.neattweet.R;
import com.codepath.apps.neattweet.ThirdPartyDecoration.DividerItemDecoration;
import com.codepath.apps.neattweet.ThirdPartyDecoration.InsetDecoration;
import com.codepath.apps.neattweet.Utility.CustomCatLoader;
import com.codepath.apps.neattweet.Utility.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;

import butterknife.BindView;



public class TweetBaseFragment extends Fragment  {
    public ArrayList<Tweet> timeline;
    public TwitterTimelineAdapter timelineAdapter;
    @BindView(R.id.rvTimeline)
    RecyclerView rvTimeline;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.fabAddTweet)
    FloatingActionButton fabAddTweet;
    LinearLayoutManager layoutManager;
    int fetchCount = 25;
    User currentUser;
    ListMode listMode;
    UserListMode userListMode;
    String mUserId;
    CustomCatLoader mCatView;
    ProgressBar pb;

    public interface ComposeTweetActionListener {
        public void onAddNewTweetInitiated();
        public void onReplyTweetInitiated(Tweet tweet);
    }

    public ComposeTweetActionListener actionListener;

    TimelineActivity listener;
    public  TweetBaseFragment newInstance(String user_id) {
        TweetBaseFragment baseFragment = new TweetBaseFragment();
        Bundle args = new Bundle();
        args.putString("userId", user_id);
        baseFragment.setArguments(args);
        return baseFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set by derived class?
        mCatView = new CustomCatLoader();
        Bundle bundle = getArguments();
        if ( bundle != null ) {
            mUserId = bundle.getString("userId");
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TimelineActivity ) {
            actionListener = (TimelineActivity)context;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        timeline = new ArrayList<Tweet>();
        currentUser = null;
        timelineAdapter = new TwitterTimelineAdapter(getContext(),timeline);
        rvTimeline = (RecyclerView)view.findViewById(R.id.rvTimeline);
        swipeContainer = (SwipeRefreshLayout)view.findViewById(R.id.swipeContainer);
        fabAddTweet = (FloatingActionButton)view.findViewById(R.id.fabAddTweet);
        pb = (ProgressBar)view.findViewById(R.id.pbLoading);

        fabAddTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( actionListener != null ) {
                    actionListener.onAddNewTweetInitiated();
                }
            }
        });

        rvTimeline.setAdapter(timelineAdapter);
        timelineAdapter.setOnTweetReplyClickListener(new TwitterTimelineAdapter.OnTweetReplyClickListener() {
            @Override
            public void onTweetReplyClicked(Tweet tweet) {
                if (actionListener != null ) {
                    actionListener.onReplyTweetInitiated(tweet);
                }
            }
        });

        setupTimelineView();
        setupSwipeRefresh();

        //first load
        if ( userListMode == UserListMode.UserListModeFriends) {
            fetchFriendsList(25,mUserId);
        }
        else if (userListMode == UserListMode.UserListModeFollowers) {
            fetchFollowersList(25,mUserId);
        }
        else {
            fetchTweets(fetchCount);
        }

        pb.setVisibility(ProgressBar.VISIBLE);
        //mCatView.show(getFragmentManager(),"");
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

        if ( listMode == ListMode.ListModeMentions ) {
            fetchMentions(limit,maxId);
        }
        else if (listMode == ListMode.ListModeDirectMessages) {
            fetchDirectMessages(limit,maxId);
        }
        else if (listMode == ListMode.ListModeUserTimeline) {
            fetchUserTimeline(limit,maxId,mUserId);
            //Todo send out screename
        }
        else if (userListMode == UserListMode.UserListModeFavs) {
            fetchFavs(limit,maxId,mUserId);
        }
        else if (userListMode == UserListMode.UserListModeRetweets) {
            fetchRetweets(limit,maxId);
        }
        else {
            fetchHomeTimeline(limit,maxId);
        }

    }

    void fetchHomeTimeline(int limit,String maxId) {
        TwitterManager.getSharedInstance().getTimelineTweets(limit,maxId,new TwitterTimelineResponseHandler() {
            @Override
            public void timelineResults(boolean isSuccess, ArrayList<Tweet> tweets) {
                if (isSuccess ) {
                   // mCatView.dismiss();
                    pb.setVisibility(ProgressBar.INVISIBLE);
                   updateTimeline(tweets);
                }
            }
        });
    }

    void fetchMentions(int limit,String maxId) {
        TwitterManager.getSharedInstance().getMentionTweets(limit, maxId, new TwitterTimelineResponseHandler() {
            @Override
            public void timelineResults(boolean isSuccess, ArrayList<Tweet> tweets) {
                if (isSuccess ) {
//                    mCatView.dismiss();
                    pb.setVisibility(ProgressBar.INVISIBLE);
                    updateTimeline(tweets);
                }
            }
        });
    }

    void fetchDirectMessages(int limit,String maxId) {
        TwitterManager.getSharedInstance().getDirectMessages(limit, maxId, new TwitterTimelineResponseHandler() {
            @Override
            public void timelineResults(boolean isSuccess, ArrayList<Tweet> tweets) {
                if (isSuccess ) {
//                    mCatView.dismiss();
                    pb.setVisibility(ProgressBar.INVISIBLE);
                    updateTimeline(tweets);
                }
            }
        });
    }

    void fetchUserTimeline(int limit,String maxId,String userId) {
        TwitterManager.getSharedInstance().getUserTimeline(limit, maxId,userId, new TwitterTimelineResponseHandler() {
            @Override
            public void timelineResults(boolean isSuccess, ArrayList<Tweet> tweets) {
                if (isSuccess ) {
//                    mCatView.dismiss();
                    pb.setVisibility(ProgressBar.INVISIBLE);
                    updateTimeline(tweets);
                }
            }
        });
    }

    void fetchFavs(int limit,String maxId,String userId) {
        TwitterManager.getSharedInstance().getFavs(limit, maxId,userId, new TwitterTimelineResponseHandler() {
            @Override
            public void timelineResults(boolean isSuccess, ArrayList<Tweet> tweets) {
                if (isSuccess ) {
//                    mCatView.dismiss();
                    pb.setVisibility(ProgressBar.INVISIBLE);
                    updateTimeline(tweets);
                }
            }
        });
    }

    void fetchRetweets(int limit,String maxId) {
        TwitterManager.getSharedInstance().getRetweets(limit,maxId, new TwitterTimelineResponseHandler() {
            @Override
            public void timelineResults(boolean isSuccess, ArrayList<Tweet> tweets) {
                if (isSuccess ) {
//                    mCatView.dismiss();
                    pb.setVisibility(ProgressBar.INVISIBLE);
                    updateTimeline(tweets);
                }
            }
        });
    }



    private void updateTimeline(ArrayList<Tweet> tweets) {
        // record this value before making any changes to the existing list
        int curSize = timelineAdapter.getItemCount();
        timeline.addAll(tweets);
        timelineAdapter.notifyItemRangeInserted(curSize,tweets.size());
        swipeContainer.setRefreshing(false);
    }

    private void clearTweets() {
        int existingCount = timelineAdapter.getItemCount();
        timeline.clear();
        timelineAdapter.notifyItemRangeRemoved(0,existingCount);
    }

    public void performSearch(String query) {
        TwitterManager.getSharedInstance().searchTweets(query, new TwitterTimelineResponseHandler() {
            @Override
            public void timelineResults(boolean isSuccess, ArrayList<Tweet> tweets) {
                if (isSuccess) {
                    //clear
                    clearTweets();
                    timeline.addAll(tweets);
                    timelineAdapter.notifyItemRangeInserted(0,tweets.size());
                }
            }
        });
    }

    public void clearSearch() {
        clearTweets();
        fetchTweets(25);
    }

    public void fetchFriendsList(int limit,String userId) {
        TwitterManager.getSharedInstance().getFriendsList(limit, userId, new UserFriendsFollowersResponseHandler() {
            @Override
            public void userResponseList(boolean isSuccess, ArrayList<User> userList) {

                ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
                //convert the userlist to tweets just to be able to use the existing view
                //TODO this is a temp hack
                for ( User user : userList) {
                    Tweet tweet = new Tweet();
                    tweet.setTweetType(TweetType.UserItemTweet);
                    tweet.setContent(user.getDescription());
                    tweet.setUser(user);
                    tweetList.add(tweet);
                }

                updateTimeline(tweetList);
                pb.setVisibility(ProgressBar.INVISIBLE);
            }
        });
    }

    public void fetchFollowersList(int limit,String userId) {
        TwitterManager.getSharedInstance().getFollowers(limit, userId, new UserFriendsFollowersResponseHandler() {
            @Override
            public void userResponseList(boolean isSuccess, ArrayList<User> userList) {

                ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
                //convert the userlist to tweets just to be able to use the existing view
                //TODO this is a temp hack
                for ( User user : userList) {
                    Tweet tweet = new Tweet();
                    tweet.setTweetType(TweetType.UserItemTweet);
                    tweet.setContent(user.getDescription());
                    tweet.setUser(user);
                    tweetList.add(tweet);
                }

                updateTimeline(tweetList);
                pb.setVisibility(ProgressBar.INVISIBLE);
            }
        });
    }

}
