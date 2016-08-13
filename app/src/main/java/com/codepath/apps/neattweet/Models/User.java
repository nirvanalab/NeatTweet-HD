package com.codepath.apps.neattweet.Models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by vidhurvoora on 8/4/16.
 */
@Parcel
public class User {
    String name;
    String screenName;
    String profileImageUrl; //profile_image_url
    String location; //location
    String id; //id
    String profileTextColour;//profile_text_color
    String followersCount;//followers_count
    String profileBackgroundImageUrl;//profile_background_image_url_https
    String profileBackgroundColor;//profile_background_color
    String description; //description
    String statusesCount; //statuses_count
    String friendsCount; //friends_count

    public User(){

    }
    public User(JSONObject userObj) throws JSONException {
        name = userObj.getString("name");
        screenName = userObj.getString("screen_name");
        profileImageUrl = userObj.getString("profile_image_url");
        location = userObj.getString("location");
        id = userObj.getString("id");
        profileTextColour = userObj.getString("profile_text_color");
       // followersCount = userObj.getLong("followersCount");
        profileBackgroundImageUrl = userObj.getString("profile_background_image_url_https");
        profileBackgroundColor = userObj.getString("profile_background_color");
        description = userObj.getString("description");
        statusesCount = userObj.getString("statuses_count");
        friendsCount = userObj.getString("friends_count");
        followersCount = userObj.getString("followers_count");
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getLocation() {
        return location;
    }

    public String getId() {
        return id;
    }

    public String getProfileTextColour() {
        return profileTextColour;
    }

    public String getFollowersCount() {
        return followersCount;
    }

    public String getProfileBackgroundImageUrl() {
        return profileBackgroundImageUrl;
    }

    public String getProfileBackgroundColor() {
        return profileBackgroundColor;
    }

    public String getDescription() {
        return description;
    }

    public String getStatusesCount() {
        return statusesCount;
    }

    public String getFriendsCount() {
        return friendsCount;
    }



}
