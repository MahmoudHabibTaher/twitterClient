package com.mondo.twitterclient.user.followers.data.source.local;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by mahmoud on 11/26/16.
 */

public final class TweetsRealmHelper {
    public static RealmResults<TweetRealm> findAll(long userId, Realm realm) {
        return realm.where(TweetRealm.class).equalTo("follower.id", userId).findAll();
    }

    public static TweetRealm findById(long id, Realm realm) {
        return realm.where(TweetRealm.class).equalTo("id", id).findFirst();
    }

    public static void save(long id, String text, int reTweetCount, String createdAt, String
            language, FollowerRealm follower, Realm realm) {
        TweetRealm tweet = findById(id, realm);
        if (tweet == null) {
            tweet = realm.createObject(TweetRealm.class);
            tweet.setId(id);
        }

        tweet.setText(text);
        tweet.setReTweetCount(reTweetCount);
        tweet.setCreatedAt(createdAt);
        tweet.setLanguage(language);
        tweet.setFollower(follower);
    }
}
