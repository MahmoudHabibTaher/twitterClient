package com.mondo.twitterclient.user.followers.data.source.local;

import io.realm.RealmObject;

/**
 * Created by mahmoud on 11/26/16.
 */

public class TweetRealm extends RealmObject {
    private long id;
    private String text;
    private int reTweetCount;
    private String createdAt;
    private String language;
    private FollowerRealm follower;

    public TweetRealm() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getReTweetCount() {
        return reTweetCount;
    }

    public void setReTweetCount(int reTweetCount) {
        this.reTweetCount = reTweetCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public FollowerRealm getFollower() {
        return follower;
    }

    public void setFollower(FollowerRealm follower) {
        this.follower = follower;
    }
}
