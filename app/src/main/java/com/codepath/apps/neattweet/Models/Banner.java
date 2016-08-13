package com.codepath.apps.neattweet.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vidhurvoora on 8/13/16.
 */
public class Banner {
    int height;
    int width;
    String url;
    String type;

    public Banner(JSONObject object) throws JSONException {
        height = object.getInt("h");
        width = object.getInt("w");
        url = object.getString("url");
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
