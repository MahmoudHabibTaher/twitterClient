package com.mondo.twitterclient.user.followers.data.source.remote;

import com.mondo.twitterclient.user.followers.data.Follower;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mahmoud on 11/23/16.
 */

public interface FollowersService {
    @GET("/1.1/followers/list.json")
    Call<FollowersResult> getFollowers(@Query("user_id") Long userId
            , @Query("screen_name") String screenName
            , @Query("cursor") Long cursor
            , @Query("count") Integer count
            , @Query("skip_status") Boolean skipStatus
            , @Query("include_user_entities") Boolean includeUserEntities);

    Call<Follower> getFollower(@Query("user_id") Long userId,@Query("screen_name") String
            screenName, @Query("include_entities") Boolean includeEntities);
}
