package com.codepath.apps.neattweet.Models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by vidhurvoora on 8/4/16.
 */
public class Tweet {

    String createdAt;//created_at
    String id; //id_str
    String content; //text
    int retweetCount; //retweet_count
    User user;
    ArrayList<String> hashTags;
    ArrayList<Media> medias;
    ArrayList<TweetURL> urls;

    public Tweet(JSONObject tweetObj)throws JSONException {
        createdAt = tweetObj.getString("created_at");
        id = tweetObj.getString("id_str");
        Log.d("Tweed Id:",id);
        content = tweetObj.getString("text");
        retweetCount = tweetObj.getInt("retweet_count");
        JSONObject userObj = tweetObj.getJSONObject("user");
        user = new User(userObj);
        medias = new ArrayList<Media>();
        urls = new ArrayList<TweetURL>();
        hashTags = new ArrayList<String>();

        //check if entities object is present

        if ( tweetObj.has("entities")) {
            JSONObject entities = tweetObj.getJSONObject("entities");
            //check if hashTags is present

            if ( entities.has("hashtags")) {
                JSONArray hashTagsArr = entities.getJSONArray("hashtags");
                hashTags = new ArrayList<String>();
                for ( int i=0;i<hashTagsArr.length();i++) {
                    JSONObject hashObj = hashTagsArr.getJSONObject(i);
                    String hashTag = hashObj.getString("text");
                    hashTags.add(hashTag);
                }
            }

            //check if media within entities is present
            if ( entities.has("media")) {
                JSONArray mediaArr = entities.getJSONArray("media");
                addMediaComponents(mediaArr);
            }

            //check if urls is present
            if ( entities.has("urls")) {
                JSONArray urlArr = entities.getJSONArray("urls");
                if (urlArr != null && urlArr.length() > 0) {
                    for (int i = 0; i < urlArr.length(); i++) {
                        JSONObject urlObj = urlArr.getJSONObject(i);
                        TweetURL tweetURL = new TweetURL(urlObj);
                        urls.add(tweetURL);
                    }
                }
            }
        }

        //Check if extended_entities is present
        if ( tweetObj.has("extended_entities")) {
            JSONObject extendedEntities = tweetObj.getJSONObject("extended_entities");
            if ( extendedEntities.has("media")) {
                JSONArray mediaArr = extendedEntities.getJSONArray("media");
                addMediaComponents(mediaArr);
            }

        }
    }

    //utility to add media components from both entities and extended_entities
    public void addMediaComponents(JSONArray mediaArr) throws JSONException{
        if ( mediaArr != null && mediaArr.length()>0) {
            for ( int i = 0; i<mediaArr.length();i++) {
                JSONObject mediaObj = mediaArr.getJSONObject(i);
                Media media = new Media(mediaObj);
                if ( media != null ) {
                    medias.add(media);
                }

            }
        }
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
