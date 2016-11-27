package com.mondo.twitterclient.user.followers.details;

import android.support.annotation.NonNull;

import com.mondo.twitterclient.user.followers.data.Follower;
import com.mondo.twitterclient.user.followers.data.Tweet;
import com.mondo.twitterclient.user.followers.data.source.FollowersDataSource;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mahmoud on 11/26/16.
 */

public class FollowerDetailsPresenter implements FollowerDetailsContract.Presenter {
    private long mUserId;
    private long mFollowerId;
    private FollowersDataSource mRepository;
    private FollowerDetailsContract.View mView;
    private CompositeSubscription mSubscriptions;

    public FollowerDetailsPresenter(long userId, long followerId, @NonNull FollowersDataSource
            repository
            , @NonNull FollowerDetailsContract.View view) {
        mUserId = userId;
        mFollowerId = followerId;
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadFollower(long id) {
        mView.setLoadingIndicatorEnabled(true);

        mSubscriptions.clear();
        Subscription subscription = mRepository.getFollower(mUserId, id).subscribeOn(Schedulers
                .io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::onFollowerLoaded,
                this::onFollowerLoadError, this::onFollowerLoadComplete);
        mSubscriptions.add(subscription);
    }

    @Override
    public void loadTweets(long id) {
        mView.setLoadingTweetsIndicatorEnabled(true);

        mSubscriptions.clear();
        Subscription subscription = mRepository.getTweets(id, 10).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this::onTweetsLoaded,
                        this::onTweetsLoadError, this::onTweetsLoadComplete);
        mSubscriptions.add(subscription);
    }

    private void onTweetsLoaded(List<Tweet> tweets) {
        if (!tweets.isEmpty()) {
            mView.showTweets(tweets);
        } else {
            mView.showNoTweets();
        }
    }

    private void onTweetsLoadError(Throwable throwable) {
        mView.showErrorLoadingTweets();
    }

    private void onTweetsLoadComplete() {
        mView.setLoadingTweetsIndicatorEnabled(false);
    }

    private void onFollowerLoaded(@NonNull Follower follower) {
        mView.showFollower(follower);
    }

    private void onFollowerLoadError(Throwable throwable) {
        mView.showErrorLoadingFollower();
    }

    private void onFollowerLoadComplete() {
        mView.setLoadingIndicatorEnabled(false);
        loadTweets(mFollowerId);
    }

    @Override
    public void subscribe() {
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.unsubscribe();
    }
}
