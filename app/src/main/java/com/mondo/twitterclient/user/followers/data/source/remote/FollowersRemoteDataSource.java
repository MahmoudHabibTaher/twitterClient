package com.mondo.twitterclient.user.followers.data.source.remote;

import android.support.annotation.NonNull;

import com.mondo.twitterclient.twitter.CustomTwitterApiClient;
import com.mondo.twitterclient.user.followers.data.Follower;
import com.mondo.twitterclient.user.followers.data.source.FollowersDataSource;
import com.mondo.twitterclient.user.followers.data.source.NoResultException;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;

import java.util.List;

import retrofit2.Call;
import rx.Observable;

/**
 * Created by mahmoud on 11/23/16.
 */

public class FollowersRemoteDataSource implements FollowersDataSource {
    private static FollowersRemoteDataSource INSTANCE;

    public static FollowersRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FollowersRemoteDataSource();
        }
        return INSTANCE;
    }

    private FollowersService mFollowersService;

    private long mNextCursor;

    private FollowersRemoteDataSource() {
        CustomTwitterApiClient apiClient = (CustomTwitterApiClient) TwitterCore.getInstance()
                .getApiClient(TwitterCore.getInstance().getSessionManager().getActiveSession());
        mFollowersService = apiClient.getFollowersService();
    }

    @Override
    public Observable<List<Follower>> getFollowers(long userId, long cursor) {
        return Observable.create(subscriber -> {
            Call<FollowersResult> call = mFollowersService.getFollowers(userId, null, cursor,
                    null, true,
                    true);
            call.enqueue(new Callback<FollowersResult>() {
                @Override
                public void success(Result<FollowersResult> result) {
                    FollowersResult data = result.data;
                    if (data != null) {
                        mNextCursor = data.getNextCursor();
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(data.getFollowers());
                            subscriber.onCompleted();
                        }
                    } else {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onError(new NoResultException());
                            subscriber.onCompleted();
                        }
                    }
                }

                @Override
                public void failure(TwitterException exception) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(exception);
                        subscriber.onCompleted();
                    }
                }
            });
        });
    }

    @Override
    public Observable<List<Follower>> getNextFollowers(long userId) {
        return getFollowers(userId, mNextCursor);
    }

    @Override
    public Observable<Follower> getFollower(long userId, long id) {
        return Observable.create(subscriber -> {
            Call<Follower> call = mFollowersService.getFollower(id, null, false);
            call.enqueue(new Callback<Follower>() {
                @Override
                public void success(Result<Follower> result) {
                    Follower follower = result.data;
                    if (follower != null) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(follower);
                            subscriber.onCompleted();
                        }
                    } else {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onError(new NoResultException());
                            subscriber.onCompleted();
                        }
                    }
                }

                @Override
                public void failure(TwitterException exception) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(exception);
                        subscriber.onCompleted();
                    }
                }
            });
        });
    }

    @Override
    public void saveFollower(@NonNull Follower follower) {
        // Not implemented
    }

    @Override
    public void refreshFollowers() {
        // Not Implemented
    }
}
