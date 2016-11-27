package com.mondo.twitterclient.user.followers.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.mondo.twitterclient.R;
import com.mondo.twitterclient.realm.RealmHelper;
import com.mondo.twitterclient.ui.activities.BaseActivity;
import com.mondo.twitterclient.user.followers.data.source.FollowersRepository;
import com.mondo.twitterclient.user.followers.data.source.local.FollowersLocalDataSource;
import com.mondo.twitterclient.user.followers.data.source.remote.FollowersRemoteDataSource;
import com.twitter.sdk.android.core.TwitterCore;

/**
 * Created by mahmoud on 11/26/16.
 */

public class FollowerDetailsActivity extends BaseActivity {
    private static final String EXTRA_ID = "com.mondo.twitterclient.user.followers.details.extras" +
            ".ID";
    private static final String EXTRA_NAME = "com.mondo.twitterclient.user.followers.details" +
            ".extras" +
            ".NAME";

    public static Intent getStartIntent(Context context, long id, String name) {
        Intent intent = new Intent(context, FollowerDetailsActivity.class);
        intent.putExtra(EXTRA_ID, id);
        intent.putExtra(EXTRA_NAME, name);
        return intent;
    }

    private FollowerDetailsPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_details);

        long id = getIntent().getLongExtra(EXTRA_ID, -1);
        if (id == -1) {
            Toast.makeText(this, "Couldn't load follower", Toast.LENGTH_SHORT).show();
            finish();
        }

        long userId = TwitterCore.getInstance().getSessionManager().getActiveSession().getUserId();

        String name = getIntent().getStringExtra(EXTRA_NAME);
        setToolbarTitle(name);

        FollowerDetailsFragment followerDetailsFragment = (FollowerDetailsFragment)
                getSupportFragmentManager().findFragmentById(R.id.follower_details_fragment);
        if (followerDetailsFragment == null) {
            followerDetailsFragment = FollowerDetailsFragment.newInstance(id);
            getSupportFragmentManager().beginTransaction().add(R.id.follower_details_fragment,
                    followerDetailsFragment).commit();
        }

        mPresenter = new FollowerDetailsPresenter(userId, id, FollowersRepository.getInstance
                (FollowersRemoteDataSource.getInstance(), FollowersLocalDataSource.getInstance
                        (RealmHelper.getFollowersRealmConfiguration())), followerDetailsFragment);
    }
}
