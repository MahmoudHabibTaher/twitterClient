package com.mondo.twitterclient.user.authentication;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.mondo.twitterclient.BasePresenter;
import com.mondo.twitterclient.BaseView;

/**
 * Created by mahmoud on 11/21/16.
 */

public interface TwitterLoginContract {
    interface View extends BaseView<Presenter> {
        void showUserFollowersUi();

        void showLoginError();
    }

    interface Presenter extends BasePresenter {
        void login(@NonNull Activity activity);

        void onActivityResult(int requestCode, int resultCode, Intent data);
    }
}
