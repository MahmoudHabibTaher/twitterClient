package com.mondo.twitterclient.user.authentication;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.mondo.twitterclient.twitter.TwitterLoginHelper;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by mahmoud on 11/21/16.
 */

public class TwitterLoginPresenter implements TwitterLoginContract.Presenter {
    private TwitterLoginContract.View mView;
    private TwitterLoginHelper mTwitterLoginHelper;

    private TwitterLoginHelper.LoginCallback mCallback = new TwitterLoginHelper.LoginCallback() {
        @Override
        public void onLoginSuccess(TwitterSession session) {
            mView.showUserFollowersUi();
        }

        @Override
        public void onLoginFail(String message) {
            mView.showLoginError();

        }
    };

    public TwitterLoginPresenter(TwitterLoginContract.View view) {
        mView = view;
        mView.setPresenter(this);

        mTwitterLoginHelper = new TwitterLoginHelper();
        mTwitterLoginHelper.setLoginCallback(mCallback);
    }

    @Override
    public void subscribe() {
        mTwitterLoginHelper.setLoginCallback(mCallback);
    }

    @Override
    public void unSubscribe() {
//        mTwitterLoginHelper.setLoginCallback(null);
    }

    @Override
    public void login(@NonNull Activity activity) {
        mTwitterLoginHelper.login(activity);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mTwitterLoginHelper.onActivityResult(requestCode, resultCode, data);
    }
}
