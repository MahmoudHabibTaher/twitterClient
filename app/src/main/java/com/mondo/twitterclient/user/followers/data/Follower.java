package com.mondo.twitterclient.user.followers.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mahmoud on 11/23/16.
 */

public class Follower {
    @SerializedName("id")
    private long id;
    private long userId;
    @SerializedName("name")
    private String name;
    @SerializedName("screen_name")
    private String screenName;
    @SerializedName("description")
    private String description;
    @SerializedName("profile_image_url")
    private String imageUrl;
    @SerializedName("profile_banner_url")
    private String backgroundImageUrl;
    private long cursor;

    public Follower() {

    }

    public Follower(long id, long userId, String name, String screenName, String description, String
            imageUrl, String backgroundImageUrl, long cursor) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.screenName = screenName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.backgroundImageUrl = backgroundImageUrl;
        this.cursor = cursor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public long getCursor() {
        return cursor;
    }

    public void setCursor(long cursor) {
        this.cursor = cursor;
    }
}
