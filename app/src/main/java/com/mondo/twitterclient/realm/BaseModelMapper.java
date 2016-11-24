package com.mondo.twitterclient.realm;

import io.realm.RealmObject;

/**
 * Created by mahmoud on 11/24/16.
 */

public interface BaseModelMapper<T extends RealmObject, V> {
    V map(T from);
}
