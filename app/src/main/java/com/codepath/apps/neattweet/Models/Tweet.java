package com.codepath.apps.neattweet.Models;

import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Tweet {

    String createdAt;//created_at
    String id; //id_str
    String content; //text
    int retweetCount; //retweet_count
    int favoriteCount; //favorite_count
    boolean isFavorited;//favorited
    boolean isRetweeted;//retweeted
    User user;
    ArrayList<String> hashTags;
    ArrayList<Media> medias;
    ArrayList<TweetURL> urls;
    TweetType tweetType;
    String webCardsBaseUrl = "https://twitter.com/i/cards/tfw/v1/";
    String relativeDate;
    String webSnapshotUrl;


    public Tweet(JSONObject tweetObj)throws JSONException {

        createdAt = tweetObj.getString("created_at");
        id = tweetObj.getString("id_str");
        Log.d("Tweed Id:",id);
        content = tweetObj.getString("text");
        retweetCount = tweetObj.getInt("retweet_count");
        favoriteCount = tweetObj.getInt("favorite_count");
        isFavorited = tweetObj.getBoolean("favorited");
        isRetweeted = tweetObj.getBoolean("retweeted");
        JSONObject userObj = tweetObj.getJSONObject("user");
        user = new User(userObj);
        medias = new ArrayList<Media>();
        urls = new ArrayList<TweetURL>();
        hashTags = new ArrayList<String>();

        //default tweet type is text
        tweetType = TweetType.TextTweet;
        //check if entities object is present
        relativeDate = getRelativeTimeAgo(createdAt);

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

                    //webSnapshotUrl = webCardsBaseUrl+id;
                    //tweetType = TweetType.ImageTweet;

                    //Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(mediaUrl).getContent());

                   // Drawable snapshot = loadImageFromWebOperations(mediaUrl);
                   // webSnapshot = snapshot;


                    //set the tweet type as webview type
                    //tweetType = TweetType.WebviewTweet;

                    //hack add media object
//                    Media media = new Media();
//                    String mediaUrl = webCardsBaseUrl+id;
//                    media.setMediaUrl(mediaUrl);
//                    media.setType("photo");
//                    medias.add(media);
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

        //determine if it
        if ( ! medias.isEmpty() ) {
            tweetType = TweetType.ImageTweet;
            for (Media media : medias) {
                if ( media.videoUrl != null ) {
                    tweetType = TweetType.VideoTweet;
                    break;
                }
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

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public Drawable loadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
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

    public ArrayList<String> getHashTags() {
        return hashTags;
    }


    public ArrayList<Media> getMedias() {
        return medias;
    }

    public ArrayList<TweetURL> getUrls() {
        return urls;
    }

    public TweetType getTweetType() {
        return tweetType;
    }

    public String getRelativeDate() {
        return relativeDate;
    }

    public String getWebSnapshotUrl() {
        return webSnapshotUrl;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public boolean isRetweeted() {
        return isRetweeted;
    }
}
