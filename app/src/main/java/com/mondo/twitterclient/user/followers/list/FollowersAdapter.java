package com.mondo.twitterclient.user.followers.list;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mondo.twitterclient.R;
import com.mondo.twitterclient.user.followers.data.Follower;

import java.util.List;

/**
 * Created by mahmoud on 11/24/16.
 */

public class FollowersAdapter extends BaseQuickAdapter<Follower, BaseViewHolder> {
    public FollowersAdapter(int layoutResId,
                            List<Follower> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Follower follower) {
        String name = follower.getName();
        String screenName = follower.getScreenName();
        String description = follower.getDescription();
        String imageUrl = follower.getImageUrl();

        baseViewHolder.setText(R.id.name_text_view, name).setText(R.id.screen_name_text_view,
                mContext.getString(R.string.screen_name_string_holder, screenName));

        if (description != null) {
            baseViewHolder.setText(R.id.description_text_view, description);
            baseViewHolder.setVisible(R.id.description_text_view, true);
        } else {
            baseViewHolder.setVisible(R.id.description_text_view, false);
        }

        Glide.with(mContext).load(imageUrl).crossFade().into((ImageView) baseViewHolder.getView(R.id
                .profile_pic_image_view));
    }
}
