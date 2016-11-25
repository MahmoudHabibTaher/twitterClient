package com.mondo.twitterclient.user.followers.list;

import android.support.annotation.NonNull;

import com.mondo.twitterclient.BasePresenter;
import com.mondo.twitterclient.BaseView;
import com.mondo.twitterclient.user.followers.data.Follower;

import java.util.List;

/**
 * Created by mahmoud on 11/24/16.
 */

public interface FollowersListContract {
    interface View extends BaseView<Presenter> {
        void setLoadingIndicatorVisible(boolean visible);

        void showFollowers(@NonNull List<Follower> followers);

        void showMoreFollowers(@NonNull List<Follower> followers);

        void setNoFollowersVisible(boolean visible);

        void setErrorLoadingFollowersVisible(boolean visible);

        void openFollowerDetailsUi(long id);
    }

    interface Presenter extends BasePresenter {
        void loadFollowers(boolean forceUpdate);

        void loadMore();

        void setActiveUserId(long userId);

        void onFollowerClick(@NonNull Follower follower);
    }
}
