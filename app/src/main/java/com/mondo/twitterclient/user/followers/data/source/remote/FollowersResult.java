package com.mondo.twitterclient.user.followers.data.source.remote;

import com.google.gson.annotations.SerializedName;
import com.mondo.twitterclient.user.followers.data.Follower;

import java.util.List;

/**
 * Created by mahmoud on 11/23/16.
 */

public class FollowersResult {
    @SerializedName("users")
    private List<Follower> followers;

    @SerializedName("next_cursor")
    private long nextCursor;

    @SerializedName("previous_cursor")
    private long previousCursor;

    public FollowersResult() {

    }

    public List<Follower> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Follower> followers) {
        this.followers = followers;
    }

    public long getNextCursor() {
        return nextCursor;
    }

    public void setNextCursor(long nextCursor) {
        this.nextCursor = nextCursor;
    }

    public long getPreviousCursor() {
        return previousCursor;
    }

    public void setPreviousCursor(long previousCursor) {
        this.previousCursor = previousCursor;
    }
}
