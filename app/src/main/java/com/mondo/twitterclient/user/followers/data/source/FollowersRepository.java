package com.mondo.twitterclient.user.followers.data.source;

import android.support.annotation.NonNull;

import com.mondo.twitterclient.user.followers.data.Follower;
import com.mondo.twitterclient.user.followers.data.Tweet;
import com.mondo.twitterclient.utils.PrefsManager;

import java.util.List;

import rx.Observable;

/**
 * Created by mahmoud on 11/24/16.
 */

public class FollowersRepository implements FollowersDataSource {
    private static FollowersRepository INSTANCE;

    public static FollowersRepository getInstance(@NonNull FollowersDataSource remoteDataSource,
                                                  @NonNull FollowersDataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new FollowersRepository(remoteDataSource, localDataSource);
        }

        return INSTANCE;
    }

    private FollowersDataSource mRemoteDataSource;
    private FollowersDataSource mLocalDataSource;

    private FollowersRepository(@NonNull FollowersDataSource remoteDataSource,
                                @NonNull FollowersDataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    @Override
    public Observable<List<Follower>> getFollowers(Long userId, Long cursor) {
        Observable<List<Follower>> remoteFollowers = getRemoteFollowers(userId, cursor);

        if (PrefsManager.getInstance().isCacheDirty()) {
            return remoteFollowers;
        }

        Observable<List<Follower>> localFollowers = getLocalFollowers(userId, cursor);
        return Observable.concat(localFollowers, remoteFollowers).filter(followers -> !followers
                .isEmpty()).first();
    }

    private Observable<List<Follower>> getRemoteFollowers(Long userId, Long cursor) {
        return mRemoteDataSource.getFollowers(userId, cursor).flatMap(
                followers -> Observable.from(followers).doOnNext(follower -> {
                    follower.setUserId(userId);
                    saveFollower(follower);
                }).toList()).doOnCompleted(() -> setCacheDirty(false));
    }

    private Observable<List<Follower>> getLocalFollowers(Long userId, Long cursor) {
        return mLocalDataSource.getFollowers(userId, cursor);
    }

    @Override
    public Observable<List<Follower>> getNextFollowers(Long userId) {
        return getRemoteNextFollowers(userId);
    }

    private Observable<List<Follower>> getRemoteNextFollowers(long userId) {
        return mRemoteDataSource.getNextFollowers(userId).flatMap(
                followers -> Observable.from(followers).doOnNext(follower -> {
                    follower.setUserId(userId);
                    saveFollower(follower);
                }).toList()).doOnCompleted(() -> setCacheDirty(false));
    }

    @Override
    public Observable<Follower> getFollower(Long userId, Long id) {
        Observable<Follower> remoteFollower = getRemoteFollower(userId, id);

        if (PrefsManager.getInstance().isCacheDirty()) {
            return remoteFollower;
        }

        Observable<Follower> localFollower = getLocalFollower(userId, id);
        return Observable.concat(localFollower, remoteFollower).filter(
                follower -> follower != null).first();
    }

    private Observable<Follower> getRemoteFollower(long userId, long id) {
        return mRemoteDataSource.getFollower(userId, id).doOnNext(follower -> {
            follower.setUserId(userId);
            saveFollower(follower);
        }).doOnCompleted(() -> setCacheDirty(false));
    }

    private Observable<Follower> getLocalFollower(long userId, long id) {
        return mLocalDataSource.getFollower(userId, id);
    }

    @Override
    public void saveFollower(@NonNull Follower follower) {
        mLocalDataSource.saveFollower(follower);
    }

    @Override
    public Observable<List<Tweet>> getTweets(Long userId, Integer count) {
        Observable<List<Tweet>> remoteTweets = getRemoteTweets(userId, count);

        if (PrefsManager.getInstance().isCacheDirty()) {
            return remoteTweets;
        }

        Observable<List<Tweet>> localTweets = getLocalTweets(userId, count);
        return Observable.concat(localTweets, remoteTweets).filter(
                tweets -> !tweets.isEmpty()).first();
    }

    private Observable<List<Tweet>> getRemoteTweets(Long userId, Integer count) {
        return mRemoteDataSource.getTweets(userId, count).flatMap(tweets -> Observable.from
                (tweets).doOnNext(this::saveTweet).toList());
    }

    private Observable<List<Tweet>> getLocalTweets(Long userId, Integer count) {
        return mLocalDataSource.getTweets(userId, count);
    }

    @Override
    public void saveTweet(@NonNull Tweet tweet) {
        mLocalDataSource.saveTweet(tweet);
    }

    public void refreshFollowers() {
        setCacheDirty(true);
    }

    private void setCacheDirty(boolean dirty) {
        PrefsManager.getInstance().setCacheDirty(dirty);
    }
}
