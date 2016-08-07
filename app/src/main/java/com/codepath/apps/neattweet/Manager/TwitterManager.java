package com.codepath.apps.neattweet.Manager;

import android.content.Context;
import android.util.Log;

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

}
