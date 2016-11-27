package com.mondo.twitterclient.user.followers.details;

import android.support.annotation.NonNull;

import com.mondo.twitterclient.BasePresenter;
import com.mondo.twitterclient.BaseView;
import com.mondo.twitterclient.user.followers.data.Follower;
import com.mondo.twitterclient.user.followers.data.Tweet;

import java.util.List;

/**
 * Created by mahmoud on 11/25/16.
 */

public interface FollowerDetailsContract {
    interface View extends BaseView<Presenter> {
        void setLoadingIndicatorEnabled(boolean enabled);

        void showFollower(@NonNull Follower follower);

        void showErrorLoadingFollower();

        void setLoadingTweetsIndicatorEnabled(boolean enabled);

        void showTweets(@NonNull List<Tweet> tweets);

        void showNoTweets();

        void showErrorLoadingTweets();
    }

    interface Presenter extends BasePresenter {
        void loadFollower(long id);

        void loadTweets(long id);
    }
}
