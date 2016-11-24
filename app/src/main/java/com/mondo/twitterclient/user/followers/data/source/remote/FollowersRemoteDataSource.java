package com.mondo.twitterclient.user.followers.data.source.remote;

import android.support.annotation.NonNull;

import com.mondo.twitterclient.user.followers.data.Follower;
import com.mondo.twitterclient.user.followers.data.source.FollowersDataSource;
import com.mondo.twitterclient.user.followers.data.source.NoResultException;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by mahmoud on 11/23/16.
 */

public class FollowersRemoteDataSource implements FollowersDataSource {
    private static final int DEFAULT_COUNT_PER_PAGE = 20;

    private static FollowersRemoteDataSource INSTANCE;

    public static FollowersRemoteDataSource getInstance(Retrofit retrofit) {
        if (INSTANCE == null) {
            INSTANCE = new FollowersRemoteDataSource(retrofit);
        }
        return INSTANCE;
    }

    private FollowersService mFollowersService;

    private FollowersRemoteDataSource(Retrofit retrofit) {
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(TwitterApiHelper.BASE_URL)
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(
//                        GsonConverterFactory.create()).build();
        mFollowersService = retrofit.create(FollowersService.class);
    }

    @Override
    public Observable<List<Follower>> getFollowers(long userId, long cursor) {
        return mFollowersService.getFollowers(userId, null, cursor, DEFAULT_COUNT_PER_PAGE, true,
                true).flatMap(this::onGetFollowersResult);
    }

    @Override
    public Observable<Follower> getFollower(long userId, long id) {
        return mFollowersService.getFollower(id, null, false);
    }

    @Override
    public void saveFollower(@NonNull Follower follower) {
        // Not implemented
    }

    private Observable<List<Follower>> onGetFollowersResult(FollowersResult result) {
        if (result != null) {
            return Observable.just(result.getFollowers());
        } else {
            return Observable.error(new NoResultException());
        }
    }
}
