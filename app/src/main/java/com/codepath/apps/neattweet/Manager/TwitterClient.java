package com.codepath.apps.neattweet.Manager;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "GOD8lMFTzSsO5LbI6wv7LJ6uP";       // Change this
	public static final String REST_CONSUMER_SECRET = "iSEZ9JONHtjtqjjcfPXDEtfovpIh2NTZYF2IpFmVId4TAmtmg4"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://neattwitter"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	public void getHomeTimeline(int limit,String maxId,AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", Integer.toString(limit));
		if (maxId != null ) {
			params.put("max_id",maxId);
		}

		client.get(apiUrl, params, handler);
	}

	public void getMentionsTimeline(int limit,String maxId,AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", Integer.toString(limit));
		if (maxId != null ) {
			params.put("max_id",maxId);
		}

		client.get(apiUrl, params, handler);
	}

    public void getDirectMessages(int limit,String maxId,AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("direct_messages/sent.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("count", Integer.toString(limit));
        if (maxId != null ) {
            params.put("max_id",maxId);
        }

        client.get(apiUrl, params, handler);
    }

    public void getUserTimeline(int limit,String maxId,String userId,AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("count", Integer.toString(limit));
        if (maxId != null ) {
            params.put("max_id",maxId);
        }
        if (userId != null ) {
            params.put("user_id",userId);
        }
        client.get(apiUrl, params, handler);
    }

	public void getFavs(int limit,String maxId,String userId,AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("favorites/list.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", Integer.toString(limit));
		if (maxId != null ) {
			params.put("max_id",maxId);
		}
		if (userId != null ) {
			params.put("user_id",userId);
		}
		client.get(apiUrl, params, handler);
	}

	public void getRetweets(int limit,String maxId,AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/retweets_of_me.json");

		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", Integer.toString(limit));
        if (maxId != null ) {
            params.put("max_id",maxId);
        }
		client.get(apiUrl, params, handler);
	}


	public void postTweet(Context context, String content,AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", content);
		client.post(context,apiUrl,params,handler);

	}

	public void getCurrentUserInfo(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		RequestParams params = new RequestParams();
		client.get(apiUrl,params,handler);
	}

	public void markFavTweet(String tweetId,boolean isFav, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("favorites/create.json");
		if ( !isFav ) {
			apiUrl = getApiUrl("favorites/destroy.json");
		}
		RequestParams params = new RequestParams();
		params.put("id", tweetId);
		client.post(context,apiUrl,params,handler);
	}

	public void retweetATweet(String tweetId,boolean isRetweet, AsyncHttpResponseHandler handler) {

		String apiUrl = getApiUrl("statuses/retweet/");
		if (!isRetweet) {
			apiUrl = getApiUrl("statuses/unretweet");
		}
		apiUrl += tweetId + ".json";
		RequestParams params = new RequestParams();
		params.put("id", tweetId);
		client.post(context,apiUrl,params,handler);
	}


    public void getBanner(String userId,AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("users/profile_banner.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        if (userId != null ) {
            params.put("user_id",userId);
        }
        client.get(apiUrl, params, handler);
    }

}