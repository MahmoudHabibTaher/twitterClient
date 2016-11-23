package com.mondo.twitterclient.ui.activities;

import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mondo.twitterclient.R;

/**
 * Created by mahmoud on 11/22/16.
 */

public class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private String mToolbarTitle = "";
    private boolean mBackEnabled;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        getToolbar();
    }

    private Toolbar getToolbar() {
        if (mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mToolbar != null) {
                mToolbar.setTitle(getToolbarTitle());
                setSupportActionBar(mToolbar);
                invalidateActionBar();
            }
        }
        return mToolbar;
    }

    private void invalidateActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            boolean backEnabled = isBackEnabled();
            actionBar.setHomeButtonEnabled(backEnabled);
            actionBar.setDisplayShowHomeEnabled(backEnabled);
            actionBar.setDisplayHomeAsUpEnabled(backEnabled);
        }
    }

    protected void setToolbarTitle(@StringRes int titleResId) {
        setToolbarTitle(getString(titleResId));
    }

    protected void setToolbarTitle(String title) {
        mToolbarTitle = title;
        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
    }

    protected String getToolbarTitle() {
        return mToolbarTitle;
    }

    protected void setBackEnabled(boolean enabled) {
        mBackEnabled = enabled;
        invalidateActionBar();
    }

    protected boolean isBackEnabled() {
        return mBackEnabled;
    }
}
