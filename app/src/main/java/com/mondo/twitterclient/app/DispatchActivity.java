package com.mondo.twitterclient.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mondo.twitterclient.R;
import com.mondo.twitterclient.user.authentication.TwitterLoginActivity;
import com.mondo.twitterclient.user.followers.list.FollowersListActivity;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

public class DispatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if (session != null) {
            startUserFollowersActivity();
        } else {
            startTwitterLoginActivity();
        }
        finish();
    }

    private void startUserFollowersActivity() {
        startActivity(FollowersListActivity.getStartIntent(this));
    }

    private void startTwitterLoginActivity() {
        startActivity(TwitterLoginActivity.getStartIntent(this));
    }
}
