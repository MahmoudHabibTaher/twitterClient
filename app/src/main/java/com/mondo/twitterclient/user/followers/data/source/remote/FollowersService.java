package com.mondo.twitterclient.user.followers.data.source.remote;

import com.mondo.twitterclient.user.followers.data.Follower;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mahmoud on 11/23/16.
 */

public interface FollowersService {
    @GET("followers/list.json")
    Observable<FollowersResult> getFollowers(@Query("user_id") Long userId
            , @Query("screen_name") String screenName
            , @Query("cursor") Long cursor
            , @Query("count") Integer count
            , @Query("skip_status") Boolean skipStatus
            , @Query("include_user_entities") Boolean includeUserEntities);

    Observable<Follower> getFollower(@Query("user_id") Long userId,@Query("screen_name") String
            screenName, @Query("include_entities") Boolean includeEntities);
}
