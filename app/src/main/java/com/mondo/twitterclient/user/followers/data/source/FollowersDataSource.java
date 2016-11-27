package com.mondo.twitterclient.user.followers.data.source;

import android.support.annotation.NonNull;

import com.mondo.twitterclient.user.followers.data.Follower;
import com.mondo.twitterclient.user.followers.data.Tweet;

import java.util.List;

import rx.Observable;

/**
 * Created by mahmoud on 11/23/16.
 */

public interface FollowersDataSource {
    Observable<List<Follower>> getFollowers(Long userId, Long cursor);

    Observable<List<Follower>> getNextFollowers(Long userId);

    Observable<Follower> getFollower(Long userId, Long id);

    void saveFollower(@NonNull Follower follower);

    Observable<List<Tweet>> getTweets(Long userId, Integer count);

    void saveTweet(@NonNull Tweet tweet);

    void refreshFollowers();
}
