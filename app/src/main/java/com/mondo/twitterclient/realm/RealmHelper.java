package com.mondo.twitterclient.realm;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

/**
 * Created by mahmoud on 11/24/16.
 */

public final class RealmHelper {
    public static RealmConfiguration getFollowersRealmConfiguration() {
        return getConfiguration(RealmConfig.FOLLOWERS_FILE_NAME, RealmConfig
                .FOLLOWERS_SCHEMA_VERSION, null);
    }

    public static void initRealm(Context context) {
        Realm.init(context);
    }

    private static RealmConfiguration getConfiguration(String fileName, int schemaMigration,
                                                       RealmMigration migration) {
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder().name(fileName)
                .schemaVersion(schemaMigration);
        if (migration != null) {
            builder.migration(migration);
        }
        return builder.build();
    }
}
