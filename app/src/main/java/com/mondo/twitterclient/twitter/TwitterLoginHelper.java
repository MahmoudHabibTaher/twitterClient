package com.mondo.twitterclient.twitter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

/**
 * Created by mahmoud on 11/22/16.
 */

public class TwitterLoginHelper {
    private TwitterAuthClient mTwitterAuthClient;
    private Callback<TwitterSession> mCallback = new Callback<TwitterSession>() {
        @Override
        public void success(Result<TwitterSession> result) {
            TwitterSession session = result.data;
            TwitterApiHelper.addCustomApiClients(session);
            if (mLoginCallback != null) {
                mLoginCallback.onLoginSuccess(result.data);
            }
        }

        @Override
        public void failure(TwitterException exception) {
            if (mLoginCallback != null) {
                mLoginCallback.onLoginFail(exception.getMessage());
            }
        }
    };
    private LoginCallback mLoginCallback;

    public TwitterLoginHelper() {
        mTwitterAuthClient = new TwitterAuthClient();
    }

    public void login(@NonNull Activity activity) {
        mTwitterAuthClient.authorize(activity, mCallback);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);
    }

    public void setLoginCallback(LoginCallback loginCallback) {
        mLoginCallback = loginCallback;
    }

    public LoginCallback getLoginCallback() {
        return mLoginCallback;
    }

    public interface LoginCallback {
        void onLoginSuccess(TwitterSession session);

        void onLoginFail(String message);
    }
}
