package com.codepath.apps.neattweet.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vidhurvoora on 8/5/16.
 */
public class Media {
    String id; //id_str
    String mediaUrl; //media_url
    String type; //type
    String videoType;//video_info->variants->[]->content_type
    String videoUrl; //video_info->variants->[]->url

    public Media() {

    }
    public Media(JSONObject mediaObj) throws JSONException {
        id = mediaObj.getString("id_str");
        mediaUrl = mediaObj.getString("media_url");
        type = mediaObj.getString("type");
        //check if video stuff is there
        if ( mediaObj.has("video_info")) {
            JSONObject videoInfo = mediaObj.getJSONObject("video_info");
            if (videoInfo.has("variants")) {
                JSONArray variants = videoInfo.getJSONArray("variants");
                if (variants != null && variants.length() > 0) {
                    //for ( int i = 0; i< variants.length(); i++ ) {
                    //get the first for now
                    JSONObject info = variants.getJSONObject(0);
                    videoType = info.getString("content_type");
                    videoUrl = info.getString("url");

                    //}
                }
            }
        }

    }

    public String getId() {
        return id;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public String getType() {
        return type;
    }

    public String getVideoType() {
        return videoType;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
