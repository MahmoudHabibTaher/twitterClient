package com.mondo.twitterclient.app;

import android.app.Application;

import com.mondo.twitterclient.realm.RealmHelper;
import com.mondo.twitterclient.twitter.TwitterApiHelper;
import com.mondo.twitterclient.twitter.TwitterConfig;
import com.mondo.twitterclient.utils.NetworkHelper;
import com.mondo.twitterclient.utils.PrefsManager;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import io.fabric.sdk.android.Fabric;

/**
 * Created by mahmoud on 11/18/16.
 */

public class TwitterClientApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initFabricWithTwitter();
        initRealm();

        PrefsManager.init(getApplicationContext());

        if (NetworkHelper.isInternetConnected(getApplicationContext())) {
            PrefsManager.getInstance().setCacheDirty(true);
        }
    }

    private void initFabricWithTwitter() {
        Fabric.with(this, new Twitter(TwitterConfig.getTwitterAuthConfig()));
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if (session != null) {
            TwitterApiHelper.addCustomApiClients(session);
        }
    }

    private void initRealm() {
        RealmHelper.initRealm(this);
    }
}
