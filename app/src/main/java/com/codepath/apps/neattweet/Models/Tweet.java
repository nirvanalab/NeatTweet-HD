package com.codepath.apps.neattweet.Models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vidhurvoora on 8/4/16.
 */
public class Tweet {

    String createdAt;//created_at
    String id; //id_str
    String content; //text
    int retweetCount; //retweet_count
    User user;

    public Tweet(JSONObject tweetObj)throws JSONException {
        createdAt = tweetObj.getString("created_at");
        id = tweetObj.getString("id_str");
        Log.d("Tweed Id:",id);
        content = tweetObj.getString("text");
        retweetCount = tweetObj.getInt("retweet_count");
        JSONObject userObj = tweetObj.getJSONObject("user");
        user = new User(userObj);
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public User getUser() {
        return user;
    }
}
