package com.codepath.apps.neattweet.Manager;

import android.content.Context;
import android.util.Log;

import com.codepath.apps.neattweet.Models.Banner;
import com.codepath.apps.neattweet.Models.BannerResponseHandler;
import com.codepath.apps.neattweet.Models.Tweet;
import com.codepath.apps.neattweet.Models.TwitterTimelineResponseHandler;
import com.codepath.apps.neattweet.Models.User;
import com.codepath.apps.neattweet.NeatTwitterApplication;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TwitterManager {

    private static TwitterManager sInstance;
    private static TwitterClient client;

    public static synchronized TwitterManager getSharedInstance() {
        if ( sInstance == null ) {
            sInstance = new TwitterManager();
            client = NeatTwitterApplication.getRestClient();
        }
        return sInstance;
    }


    public ArrayList<Tweet> fetchTimelineFromJsonArray(JSONArray timelineArr) throws JSONException {

        ArrayList<Tweet> timeline = new ArrayList<Tweet>();
        for (int i=0;i<timelineArr.length();i++) {
            JSONObject timelineObj = timelineArr.getJSONObject(i);
            Tweet tweet = new Tweet(timelineObj);
            timeline.add(tweet);
        }
        return timeline;

    }

    public ArrayList<User> fetchUserListFromJsonArray(JSONArray userArr) throws JSONException {

        ArrayList<User> userList = new ArrayList<User>();
        for (int i=0;i<userArr.length();i++) {
            JSONObject userObj = userArr.getJSONObject(i);
            User user = new User(userObj);
            userList.add(user);
        }
        return userList;

    }

    public ArrayList<Tweet> getTimelineTweets(int limit,String maxId,final TwitterTimelineResponseHandler handler) {

        client.getHomeTimeline(limit,maxId,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //parse the jsonarray
                try {
                    ArrayList<Tweet> timeline = fetchTimelineFromJsonArray(response);
                    handler.timelineResults(true,timeline);
                } catch (JSONException e) {
                    e.printStackTrace();
                    handler.timelineResults(false,null);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //failure
                Log.d("Failed timeline fetch",errorResponse.toString());
                handler.timelineResults(false,null);
            }
        });
        return null;
    }

    public ArrayList<Tweet> getMentionTweets(int limit,String maxId,final TwitterTimelineResponseHandler handler) {

        client.getMentionsTimeline(limit,maxId,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //parse the jsonarray
                try {
                    ArrayList<Tweet> timeline = fetchTimelineFromJsonArray(response);
                    handler.timelineResults(true,timeline);
                } catch (JSONException e) {
                    e.printStackTrace();
                    handler.timelineResults(false,null);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //failure
                Log.d("Failed timeline fetch",errorResponse.toString());
                handler.timelineResults(false,null);
            }
        });
        return null;
    }

    public ArrayList<Tweet> getDirectMessages(int limit,String maxId,final TwitterTimelineResponseHandler handler) {

        client.getDirectMessages(limit,maxId,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //parse the jsonarray
                try {
                    ArrayList<Tweet> timeline = fetchTimelineFromJsonArray(response);
                    handler.timelineResults(true,timeline);
                } catch (JSONException e) {
                    e.printStackTrace();
                    handler.timelineResults(false,null);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //failure
                Log.d("Failed timeline fetch",errorResponse.toString());
                handler.timelineResults(false,null);
            }
        });
        return null;
    }



    public ArrayList<Tweet> getUserTimeline(int limit,String maxId,String userId,final TwitterTimelineResponseHandler handler) {

        client.getUserTimeline(limit,maxId,userId,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //parse the jsonarray
                try {
                    ArrayList<Tweet> timeline = fetchTimelineFromJsonArray(response);
                    handler.timelineResults(true,timeline);
                } catch (JSONException e) {
                    e.printStackTrace();
                    handler.timelineResults(false,null);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //failure
                Log.d("Failed timeline fetch",errorResponse.toString());
                handler.timelineResults(false,null);
            }
        });
        return null;
    }

    public Banner getBanner(String userId, final BannerResponseHandler handler) {

        client.getBanner(userId,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //parse the jsonarray
                try {
                    if ( response.has("sizes")) {
                        JSONObject sizes = response.getJSONObject("sizes");
                        JSONObject mobileBanner = sizes.getJSONObject("1500x500");
                        Banner banner = new Banner(mobileBanner);
                        handler.bannerResults(true,banner);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    handler.bannerResults(false,null);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //failure
                Log.d("Failed timeline fetch",errorResponse.toString());
                handler.bannerResults(false,null);
            }
        });
        return null;
    }

    public void getFavs(int limit,String maxId,String userId,final TwitterTimelineResponseHandler handler) {

        client.getFavs(limit,maxId,userId,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //parse the jsonarray
                try {
                    ArrayList<Tweet> timeline = fetchTimelineFromJsonArray(response);
                    handler.timelineResults(true,timeline);
                } catch (JSONException e) {
                    e.printStackTrace();
                    handler.timelineResults(false,null);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //failure
                Log.d("Failed timeline fetch",errorResponse.toString());
                handler.timelineResults(false,null);
            }
        });

    }

    public void getRetweets(int limit,String maxId,final TwitterTimelineResponseHandler handler) {

        client.getRetweets(limit,maxId,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //parse the jsonarray
                try {
                    ArrayList<Tweet> timeline = fetchTimelineFromJsonArray(response);
                    handler.timelineResults(true,timeline);
                } catch (JSONException e) {
                    e.printStackTrace();
                    handler.timelineResults(false,null);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //failure
                Log.d("Failed timeline fetch",errorResponse.toString());
                handler.timelineResults(false,null);
            }
        });
    }

    public ArrayList<Tweet> searchTweets(String query,final TwitterTimelineResponseHandler handler) {

        client.searchTweets(query,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //parse the jsonarray
                try {
                    ArrayList<Tweet> timeline = fetchTimelineFromJsonArray(response);
                    handler.timelineResults(true,timeline);
                } catch (JSONException e) {
                    e.printStackTrace();
                    handler.timelineResults(false,null);
                }
            }

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //parse the jsonarray
                try {
                    if (response.has("statuses")) {
                        JSONArray statusArr = response.getJSONArray("statuses");
                        ArrayList<Tweet> timeline = fetchTimelineFromJsonArray(statusArr);
                        handler.timelineResults(true,timeline);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    handler.timelineResults(false,null);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //failure
                Log.d("Failed timeline fetch",errorResponse.toString());
                handler.timelineResults(false,null);
            }
        });
        return null;
    }
    public void postTweet(Context context, String content, final TweetResponseHandler handler) {
        client.postTweet(context,content,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Tweet tweet = new Tweet(response);
                    handler.onTweetPosted(true,tweet);
                } catch (JSONException e) {
                    e.printStackTrace();
                    handler.onTweetPosted(false, null);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                handler.onTweetPosted(false,null);
            }
        });

    }

    public void getCurrentUser( final UserInfoResponseHandler handler){
        client.getCurrentUserInfo(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    User user = new User(response);
                    handler.onUserInfo(true,user);
                } catch (JSONException e) {
                    e.printStackTrace();
                    handler.onUserInfo(false,null);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                handler.onUserInfo(false,null);
            }
        });
    }

    public void markTweetAsFav(String tweetId, boolean isFav){
        client.markFavTweet(tweetId,isFav,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //TODO handle accordingly
               // super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public void retweetATweet(String tweetId, boolean isRetweet) {
        client.retweetATweet(tweetId,isRetweet,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //TODO handle accordingly
                //super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
            }


        });
    }

    public void getFriendsList(int limit,String userId,final UserFriendsFollowersResponseHandler handler) {

        client.getFriends(limit,userId,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //parse the jsonarray
                try {
                    if ( response.has("users")) {
                        JSONArray userArr = response.getJSONArray("users");
                        ArrayList<User> userList = fetchUserListFromJsonArray(userArr) ;
                        handler.userResponseList(true,userList);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    handler.userResponseList(false,null);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //failure
                Log.d("Failed timeline fetch",errorResponse.toString());
                handler.userResponseList(false,null);
            }
        });
    }

    public void getFollowers(int limit,String userId,final UserFriendsFollowersResponseHandler handler) {

        client.getFollowers(limit,userId,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //parse the jsonarray
                try {
                    if ( response.has("users")) {
                        JSONArray userArr = response.getJSONArray("users");
                        ArrayList<User> userList = fetchUserListFromJsonArray(userArr) ;
                        handler.userResponseList(true,userList);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    handler.userResponseList(false,null);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //failure
                Log.d("Failed timeline fetch",errorResponse.toString());
                handler.userResponseList(false,null);
            }
        });
    }





}
