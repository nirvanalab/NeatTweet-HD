package com.codepath.apps.neattweet.Manager;

import com.codepath.apps.neattweet.Models.Tweet;

/**
 * Created by vidhurvoora on 8/4/16.
 */

public interface TweetResponseHandler {
    public void onTweetPosted(boolean isSuccess, Tweet tweet);
}
