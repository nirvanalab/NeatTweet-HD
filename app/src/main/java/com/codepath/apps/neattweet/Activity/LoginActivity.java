package com.codepath.apps.neattweet.Activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.codepath.apps.neattweet.Manager.TwitterClient;
import com.codepath.apps.neattweet.R;
import com.codepath.oauth.OAuthLoginActionBarActivity;

import butterknife.BindView;

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {

	@BindView(R.id.ivTweetBird) ImageView ivTweetBird;
	private AnimationDrawable frameAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		ivTweetBird = (ImageView) findViewById(R.id.ivTweetBird);
		ivTweetBird.setBackgroundResource(R.drawable.frame_animation_list);
		frameAnimation = (AnimationDrawable) ivTweetBird.getBackground();
		frameAnimation.start();



	}

	@Override
	protected void onResume() {
		super.onResume();
//		ivTweetBird.post(new Runnable() {
//			@Override
//			public void run() {
//				AnimationDrawable frameAnimation =
//						(AnimationDrawable) ivTweetBird.getBackground();
//				frameAnimation.start();
//			}
//		});
	}

	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {
		Intent i = new Intent(this, TimelineActivity.class);
		startActivity(i);
	}

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
		e.printStackTrace();
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to login
	public void loginToRest(View view) {
		getClient().connect();
	}

}
