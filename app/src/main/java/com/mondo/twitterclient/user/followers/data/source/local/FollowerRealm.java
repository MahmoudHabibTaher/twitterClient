package com.mondo.twitterclient.user.followers.data.source.local;

import io.realm.RealmObject;

/**
 * Created by mahmoud on 11/24/16.
 */

public class FollowerRealm extends RealmObject {
    private long id;
    private long userId;
    private String name;
    private String screenName;
    private String description;
    private String imageUrl;
    private long cursor;

    public FollowerRealm() {

    }

    public FollowerRealm(long id, long userId, String name, String screenName, String
            description, String imageUrl, long cursor) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.screenName = screenName;
        this.description = description;
        this.imageUrl = imageUrl;
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

    public long getCursor() {
        return cursor;
    }

    public void setCursor(long cursor) {
        this.cursor = cursor;
    }
}
