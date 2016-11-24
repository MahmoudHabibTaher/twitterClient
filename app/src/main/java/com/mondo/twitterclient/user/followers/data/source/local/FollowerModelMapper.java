package com.mondo.twitterclient.user.followers.data.source.local;

import com.mondo.twitterclient.realm.BaseModelMapper;
import com.mondo.twitterclient.user.followers.data.Follower;

/**
 * Created by mahmoud on 11/24/16.
 */

public class FollowerModelMapper implements BaseModelMapper<FollowerRealm, Follower> {
    @Override
    public Follower map(FollowerRealm from) {
        Follower follower = null;
        if (from != null) {
            follower = new Follower(from.getId(), from.getUserId(), from.getName(), from
                    .getScreenName(), from.getDescription(), from.getImageUrl(), from.getCursor());
        }
        return follower;
    }
}
