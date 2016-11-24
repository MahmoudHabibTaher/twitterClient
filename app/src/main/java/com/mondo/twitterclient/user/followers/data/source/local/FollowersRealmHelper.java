package com.mondo.twitterclient.user.followers.data.source.local;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.Sort;

/**
 * Created by mahmoud on 11/24/16.
 */

public final class FollowersRealmHelper {
    public static List<FollowerRealm> findAll(long userId, @Nullable Long cursor,
                                              @NonNull Realm realm) {
        RealmQuery<FollowerRealm> query = realm.where(FollowerRealm.class);
        query.equalTo("userId", userId);
        if (cursor != null) {
            query.equalTo("cursor", cursor);
        }
        return query.findAllSorted("name", Sort.ASCENDING);
    }

    public static FollowerRealm findById(long id, long userId, @NonNull
            Realm realm) {
        return realm.where(FollowerRealm.class).equalTo("id", id).equalTo("userId", userId)
                .findFirst();
    }

    public static FollowerRealm save(long id, long userId, @NonNull String
            name, @NonNull String screenName, @Nullable String description,
                                     @Nullable String imageUrl, @NonNull Realm realm) {
        FollowerRealm follower = findById(id, userId, realm);
        if (follower == null) {
            follower = realm.createObject(FollowerRealm.class);
            follower.setId(id);
            follower.setUserId(userId);
        }

        follower.setName(name);
        follower.setScreenName(screenName);
        follower.setDescription(description);
        follower.setImageUrl(imageUrl);

        return follower;
    }

    public static void deleteAll(long userId, @NonNull Realm realm) {
        findAll(userId, null, realm);
    }
}
