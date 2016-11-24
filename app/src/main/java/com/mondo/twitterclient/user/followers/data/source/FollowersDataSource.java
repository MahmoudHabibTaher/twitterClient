package com.mondo.twitterclient.user.followers.data.source;

import android.support.annotation.NonNull;

import com.mondo.twitterclient.user.followers.data.Follower;

import java.util.List;

import rx.Observable;

/**
 * Created by mahmoud on 11/23/16.
 */

public interface FollowersDataSource {
    Observable<List<Follower>> getFollowers(long userId, long cursor);

    Observable<Follower> getFollower(long userId, long id);

    void saveFollower(@NonNull Follower follower);
}
