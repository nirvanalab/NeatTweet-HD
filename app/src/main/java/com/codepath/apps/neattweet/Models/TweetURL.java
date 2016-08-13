package com.codepath.apps.neattweet.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vidhurvoora on 8/5/16.
 */

public class TweetURL {
    String url;
    String expandedUrl;
    String displayUrl;

    public  TweetURL(JSONObject urlObj) throws JSONException {
        url = urlObj.getString("url");
        expandedUrl = urlObj.getString("expanded_url");
        displayUrl = urlObj.getString("display_url");
    }
}
