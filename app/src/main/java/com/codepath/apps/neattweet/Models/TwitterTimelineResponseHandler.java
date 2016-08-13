package com.codepath.apps.neattweet.Models;

import java.util.ArrayList;

public interface TwitterTimelineResponseHandler {
	public void timelineResults(boolean isSuccess, ArrayList<Tweet> tweets);
}

