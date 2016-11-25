package com.mondo.twitterclient.user.followers.data.source.local;

import android.support.annotation.NonNull;

import com.mondo.twitterclient.user.followers.data.Follower;
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
    public Observable<List<Follower>> getFollowers(long userId, long cursor) {
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
    public Observable<Follower> getFollower(long userId, long id) {
        return Observable.create(subscriber -> {
            Follower follower = null;

            Realm realm = Realm.getInstance(mRealmConfiguration);

            FollowerRealm followerRealm = FollowersRealmHelper.findById(userId, id, realm);
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
        Observable.create(subscriber -> {
            Realm realm = Realm.getInstance(mRealmConfiguration);
            realm.beginTransaction();

            FollowersRealmHelper.save(follower.getId(), follower.getUserId(), follower.getName(),
                    follower.getScreenName(), follower.getDescription(), follower.getImageUrl(),
                    realm);

            realm.commitTransaction();
            realm.close();
        });
    }

    @Override
    public void refreshFollowers() {

    }
}
