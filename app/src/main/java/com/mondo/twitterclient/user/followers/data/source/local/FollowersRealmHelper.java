package com.mondo.twitterclient.user.followers.data.source.local;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * Created by mahmoud on 11/24/16.
 */

public final class FollowersRealmHelper {
    public static List<FollowerRealm> findAll(long userId, @Nullable Long cursor,
                                              @NonNull Realm realm) {
        RealmQuery<FollowerRealm> query = realm.where(FollowerRealm.class);
        query.equalTo("userId", userId);
        if (cursor != null && cursor != -1) {
            query.equalTo("cursor", cursor);
        }
        return query.findAll();
    }

    public static FollowerRealm findById(long id, @NonNull
            Realm realm) {
        return realm.where(FollowerRealm.class).equalTo("id", id)
                .findFirst();
    }

    public static FollowerRealm save(long id, long userId, @NonNull String
            name, @NonNull String screenName, @Nullable String description,
                                     @Nullable String imageUrl, @Nullable String backgroundImageUrl,
                                     @NonNull Realm realm) {
        FollowerRealm follower = findById(id, realm);
        if (follower == null) {
            follower = realm.createObject(FollowerRealm.class);
            follower.setId(id);
            follower.setUserId(userId);
        }

        follower.setName(name);
        follower.setScreenName(screenName);
        follower.setDescription(description);
        follower.setImageUrl(imageUrl);
        follower.setBackgroundImageUrl(backgroundImageUrl);

        return follower;
    }
}
