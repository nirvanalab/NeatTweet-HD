package com.codepath.apps.neattweet.Manager;

import com.codepath.apps.neattweet.Models.User;

import java.util.ArrayList;

public interface UserFriendsFollowersResponseHandler {
    public void userResponseList(boolean isSuccess, ArrayList<User> userList);
}
