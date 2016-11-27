package com.mondo.twitterclient.user.followers.data.source.local;

import com.mondo.twitterclient.realm.BaseModelMapper;
import com.mondo.twitterclient.user.followers.data.Tweet;

/**
 * Created by mahmoud on 11/26/16.
 */

public class TweetModelMapper implements BaseModelMapper<TweetRealm, Tweet> {
    @Override
    public Tweet map(TweetRealm from) {
        Tweet tweet = new Tweet();
        tweet.setId(from.getId());
        tweet.setText(from.getText());
        tweet.setCreatedAt(from.getCreatedAt());
        tweet.setReTweetCount(from.getReTweetCount());
        tweet.setUserId(from.getFollower().getId());
        return tweet;
    }
}
