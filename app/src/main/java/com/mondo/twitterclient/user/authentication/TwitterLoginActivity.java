package com.mondo.twitterclient.user.authentication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mondo.twitterclient.R;
import com.mondo.twitterclient.ui.activities.BaseActivity;

/**
 * Created by mahmoud on 11/22/16.
 */

public class TwitterLoginActivity extends BaseActivity {
    public static Intent getStartIntent(Context context) {
        return new Intent(context, TwitterLoginActivity.class);
    }

    private TwitterLoginFragment mTwitterLoginFragment;
    private TwitterLoginContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_login);

        mTwitterLoginFragment = (TwitterLoginFragment)
                getSupportFragmentManager().findFragmentById(R.id.twitter_login_fragment);
        if (mTwitterLoginFragment == null) {
            mTwitterLoginFragment = new TwitterLoginFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.twitter_login_fragment,
                    mTwitterLoginFragment).commit();
        }

        mPresenter = new TwitterLoginPresenter
                (mTwitterLoginFragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (mTwitterLoginFragment != null) {
            mTwitterLoginFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
