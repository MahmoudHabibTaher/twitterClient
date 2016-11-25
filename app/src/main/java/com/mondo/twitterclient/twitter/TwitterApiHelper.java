package com.mondo.twitterclient.twitter;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by mahmoud on 11/23/16.
 */

public final class TwitterApiHelper {
    public static void addCustomApiClients(TwitterSession session) {
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient customClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor).build();
        TwitterCore.getInstance().addApiClient(session,
                new CustomTwitterApiClient(session, customClient));
    }
}
