package com.mondo.twitterclient.user.followers.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mondo.twitterclient.R;
import com.mondo.twitterclient.realm.RealmHelper;
import com.mondo.twitterclient.ui.activities.BaseActivity;
import com.mondo.twitterclient.user.followers.data.source.FollowersRepository;
import com.mondo.twitterclient.user.followers.data.source.local.FollowersLocalDataSource;
import com.mondo.twitterclient.user.followers.data.source.remote.FollowersRemoteDataSource;

/**
 * Created by mahmoud on 11/24/16.
 */

public class FollowersListActivity extends BaseActivity {
    public static Intent getStartIntent(Context context) {
        return new Intent(context, FollowersListActivity.class);
    }

    private FollowersListContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers_list);

        setToolbarTitle(getString(R.string.followers_list_activity_title));

        FollowersListFragment followersListFragment = (FollowersListFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.followers_list_fragment);
        if (followersListFragment == null) {
            followersListFragment = FollowersListFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.followers_list_fragment,
                    followersListFragment).commit();
        }

        mPresenter = new FollowersListPresenter(FollowersRepository.getInstance(
                FollowersRemoteDataSource.getInstance(),
                FollowersLocalDataSource.getInstance(RealmHelper.getFollowersRealmConfiguration()
                )), followersListFragment);
    }
}
