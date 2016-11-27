package com.mondo.twitterclient.user.followers.data;

/**
 * Created by mahmoud on 11/26/16.
 */

public class Tweet {
    private long id;
    private String text;
    private int reTweetCount;
    private String createdAt;
    private String language;
    private long userId;

    public Tweet() {
    }

    public Tweet(long id, String text, int reTweetCount, String createdAt, String language,
                 long userId) {
        this.id = id;
        this.text = text;
        this.reTweetCount = reTweetCount;
        this.createdAt = createdAt;
        this.language = language;
        this.userId = userId;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
