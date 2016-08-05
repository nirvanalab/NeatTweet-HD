package com.codepath.apps.neattweet.Manager;

import android.util.Log;

import com.codepath.apps.neattweet.Models.Tweet;
import com.codepath.apps.neattweet.NeatTwitterApplication;
import com.codepath.apps.neattweet.Models.TwitterTimelineResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by vidhurvoora on 8/4/16.
 */
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

}
