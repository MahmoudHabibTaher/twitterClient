package com.mondo.twitterclient.user.followers.list;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mondo.twitterclient.user.followers.data.Follower;
import com.mondo.twitterclient.user.followers.data.source.FollowersDataSource;
import com.twitter.sdk.android.Twitter;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mahmoud on 11/24/16.
 */

public class FollowersListPresenter implements FollowersListContract.Presenter {

    private static final String TAG = FollowersListPresenter.class.getSimpleName();

    private long mUserId;
    private FollowersDataSource mRepository;
    private FollowersListContract.View mView;
    private CompositeSubscription mSubscription;

    private boolean mFirstLoad = true;

    public FollowersListPresenter(@NonNull FollowersDataSource repository, @NonNull
            FollowersListContract.View view) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
        mUserId = Twitter.getSessionManager().getActiveSession().getUserId();
    }

    @Override
    public void loadFollowers(boolean forceUpdate) {
        if (mFirstLoad || forceUpdate) {
            mFirstLoad = false;
            mView.setLoadingIndicatorVisible(true);
            mView.setErrorLoadingFollowersVisible(false);
            mView.setNoFollowersVisible(false);
            mRepository.refreshFollowers();
        }

        mSubscription.clear();

        Subscription subscription = mRepository.getFollowers(mUserId, -1).subscribeOn(Schedulers
                .io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::onFollowersLoaded,
                this::onFollowersLoadError, this::onFollowersLoadComplete);
        mSubscription.add(subscription);
    }

    private void onFollowersLoaded(List<Follower> followers) {
        mView.showFollowers(followers);
        if (followers.isEmpty()) {
            mView.setNoFollowersVisible(true);
        }
    }

    private void onFollowersLoadError(Throwable throwable) {
        Log.d(TAG, "Error Loading followers " + throwable);
        mView.setErrorLoadingFollowersVisible(true);
        mView.setLoadingIndicatorVisible(false);
    }

    private void onFollowersLoadComplete() {
        mView.setLoadingIndicatorVisible(false);
    }

    @Override
    public void loadMore() {

    }

    @Override
    public void setActiveUserId(long userId) {
        mUserId = userId;
    }

    @Override
    public void onFollowerClick(@NonNull Follower follower) {

    }

    @Override
    public void subscribe() {
        mSubscription = new CompositeSubscription();
        loadFollowers(false);
    }

    @Override
    public void unSubscribe() {
        mSubscription.unsubscribe();
    }
}
