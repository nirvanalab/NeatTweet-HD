package com.codepath.apps.neattweet.Manager;

import com.codepath.apps.neattweet.Models.User;

public interface UserInfoResponseHandler {
    public void onUserInfo(boolean isSuccess, User user);
}
