package com.mondo.twitterclient.user.followers.details;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mondo.twitterclient.R;
import com.mondo.twitterclient.user.followers.data.Tweet;

import java.util.List;

/**
 * Created by mahmoud on 11/26/16.
 */

public class TweetsAdapter extends BaseQuickAdapter<Tweet, BaseViewHolder> {
    public TweetsAdapter(int layoutResId,
                         List<Tweet> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Tweet tweet) {
        baseViewHolder.setText(R.id.tweet_body_text_view, tweet.getText());
    }
}
