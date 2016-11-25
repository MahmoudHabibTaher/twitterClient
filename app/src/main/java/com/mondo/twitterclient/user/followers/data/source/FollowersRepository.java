package com.mondo.twitterclient.user.followers.data.source;

import android.support.annotation.NonNull;

import com.mondo.twitterclient.user.followers.data.Follower;

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

    private boolean mCacheDirty;

    @Override
    public Observable<List<Follower>> getFollowers(long userId, long cursor) {
        Observable<List<Follower>> remoteFollowers = getRemoteFollowers(userId, cursor);

        if (mCacheDirty) {
            return remoteFollowers;
        }

        Observable<List<Follower>> localFollowers = getLocalFollowers(userId, cursor);
        return Observable.concat(localFollowers, remoteFollowers).filter(followers -> !followers
                .isEmpty()).first();
    }

    private Observable<List<Follower>> getRemoteFollowers(long userId, long cursor) {
        return mRemoteDataSource.getFollowers(userId, cursor).flatMap(
                followers -> Observable.from(followers).doOnNext(
                        this::saveFollower).toList()).doOnCompleted(() -> mCacheDirty = false);
    }

    private Observable<List<Follower>> getLocalFollowers(long userId, long cursor) {
        return mLocalDataSource.getFollowers(userId, cursor);
    }

    @Override
    public Observable<List<Follower>> getNextFollowers(long userId) {
        return getRemoteNextFollowers(userId);
    }

    private Observable<List<Follower>> getRemoteNextFollowers(long userId) {
        return mRemoteDataSource.getNextFollowers(userId).flatMap(
                followers -> Observable.from(followers).doOnNext(
                        this::saveFollower).toList()).doOnCompleted(() -> mCacheDirty = false);
    }

    @Override
    public Observable<Follower> getFollower(long userId, long id) {
        Observable<Follower> remoteFollower = getRemoteFollower(userId, id);

        if (mCacheDirty) {
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
        }).doOnCompleted(() -> mCacheDirty = false);
    }

    private Observable<Follower> getLocalFollower(long userId, long id) {
        return mLocalDataSource.getFollower(userId, id);
    }

    @Override
    public void saveFollower(@NonNull Follower follower) {
        mLocalDataSource.saveFollower(follower);
    }

    public void refreshFollowers() {
        mCacheDirty = true;
    }
}
