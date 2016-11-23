package com.mondo.twitterclient.user.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mondo.twitterclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mahmoud on 11/21/16.
 */

public class TwitterLoginFragment extends Fragment implements TwitterLoginContract.View {
    private TwitterLoginContract.Presenter mPresenter;

    @BindView(R.id.twitter_login_button)
    Button mTwitterLoginButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_twitter_login, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        mTwitterLoginButton.setOnClickListener(view -> mPresenter.login(getActivity()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }

    @Override
    public void showUserFollowersUi() {
        Toast.makeText(getActivity(), "Login Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginError() {
        Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(TwitterLoginContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
