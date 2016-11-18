package com.mondo.twitterclient.app;

import android.app.Application;

import com.mondo.twitterclient.twitter.TwitterConfig;
import com.twitter.sdk.android.Twitter;

import io.fabric.sdk.android.Fabric;

/**
 * Created by mahmoud on 11/18/16.
 */

public class TwitterClientApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initFabricWithTwitter();
    }

    private void initFabricWithTwitter() {
        Fabric.with(this, new Twitter(TwitterConfig.getTwitterAuthConfig()));
    }
}
