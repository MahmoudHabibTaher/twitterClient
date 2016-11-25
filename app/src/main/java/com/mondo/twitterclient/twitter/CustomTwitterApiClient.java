package com.mondo.twitterclient.twitter;

import com.mondo.twitterclient.user.followers.data.source.remote.FollowersService;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

import okhttp3.OkHttpClient;

/**
 * Created by mahmoud on 11/24/16.
 */

public class CustomTwitterApiClient extends TwitterApiClient {
    public CustomTwitterApiClient() {
    }

    public CustomTwitterApiClient(OkHttpClient client) {
        super(client);
    }

    public CustomTwitterApiClient(TwitterSession session) {
        super(session);
    }

    public CustomTwitterApiClient(TwitterSession session,
                                  OkHttpClient client) {
        super(session, client);
    }

    public FollowersService getFollowersService() {
        return getService(FollowersService.class);
    }
}
