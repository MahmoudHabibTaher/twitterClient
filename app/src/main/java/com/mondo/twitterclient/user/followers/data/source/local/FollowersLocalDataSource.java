package com.mondo.twitterclient.user.followers.data.source.local;

import android.support.annotation.NonNull;

import com.mondo.twitterclient.user.followers.data.Follower;
import com.mondo.twitterclient.user.followers.data.Tweet;
import com.mondo.twitterclient.user.followers.data.source.FollowersDataSource;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observable;

/**
 * Created by mahmoud on 11/24/16.
 */

public class FollowersLocalDataSource implements FollowersDataSource {
    private static FollowersLocalDataSource INSTANCE;

    public static FollowersLocalDataSource getInstance(RealmConfiguration realmConfiguration) {
        if (INSTANCE == null) {
            INSTANCE = new FollowersLocalDataSource(realmConfiguration);
        }

        return INSTANCE;
    }

    private RealmConfiguration mRealmConfiguration;

    private FollowersLocalDataSource(RealmConfiguration configuration) {
        mRealmConfiguration = configuration;
    }

    @Override
    public Observable<List<Follower>> getFollowers(Long userId, Long cursor) {
        return Observable.create(subscriber -> {
            List<Follower> followers = new ArrayList<>();

            Realm realm = Realm.getInstance(mRealmConfiguration);

            List<FollowerRealm> followerRealms = FollowersRealmHelper.findAll(userId, cursor,
                    realm);
            FollowerModelMapper mapper = new FollowerModelMapper();
            for (FollowerRealm followerRealm : followerRealms) {
                followers.add(mapper.map(followerRealm));
            }

            realm.close();

            if (!subscriber.isUnsubscribed()) {
                subscriber.onNext(followers);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<Follower>> getNextFollowers(Long userId) {
        return null;
    }

    @Override
    public Observable<Follower> getFollower(Long userId, Long id) {
        return Observable.create(subscriber -> {
            Follower follower = null;

            Realm realm = Realm.getInstance(mRealmConfiguration);

            FollowerRealm followerRealm = FollowersRealmHelper.findById(userId, realm);
            FollowerModelMapper mapper = new FollowerModelMapper();
            if (followerRealm != null) {
                follower = mapper.map(followerRealm);
            }

            realm.close();

            if (!subscriber.isUnsubscribed()) {
                subscriber.onNext(follower);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public void saveFollower(@NonNull Follower follower) {
        Realm realm = Realm.getInstance(mRealmConfiguration);
        realm.beginTransaction();

        FollowersRealmHelper.save(follower.getId(), follower.getUserId(), follower.getName(),
                follower.getScreenName(), follower.getDescription(), follower.getImageUrl(),
                follower.getBackgroundImageUrl(), realm);

        realm.commitTransaction();
        realm.close();
    }

    @Override
    public Observable<List<Tweet>> getTweets(Long userId, Integer count) {
        return Observable.create(subscriber -> {
            List<Tweet> tweets = new ArrayList<>();

            Realm realm = Realm.getDefaultInstance();

            List<TweetRealm> tweetRealms = TweetsRealmHelper.findAll(userId, realm);
            TweetModelMapper mapper = new TweetModelMapper();

            for (TweetRealm tweetRealm : tweetRealms) {
                tweets.add(mapper.map(tweetRealm));
            }

            realm.close();

            if (!subscriber.isUnsubscribed()) {
                subscriber.onNext(tweets);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public void saveTweet(@NonNull Tweet tweet) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        FollowerRealm followerRealm = FollowersRealmHelper.findById(tweet.getUserId(), realm);
        TweetsRealmHelper.save(tweet.getId(), tweet.getText(), tweet.getReTweetCount(), tweet
                .getCreatedAt(), tweet.getLanguage(), followerRealm, realm);

        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void refreshFollowers() {

    }
}
