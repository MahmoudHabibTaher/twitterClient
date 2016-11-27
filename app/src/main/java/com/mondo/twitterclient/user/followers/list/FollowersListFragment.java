package com.mondo.twitterclient.user.followers.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mondo.twitterclient.R;
import com.mondo.twitterclient.user.followers.data.Follower;
import com.mondo.twitterclient.user.followers.details.FollowerDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mahmoud on 11/24/16.
 */

public class FollowersListFragment extends Fragment implements FollowersListContract.View {
    private static final String TAG = FollowersListFragment.class.getSimpleName();

    public static FollowersListFragment newInstance() {
        return new FollowersListFragment();
    }

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.followers_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.no_followers_text_view)
    TextView mNoFollowersTextView;

    @BindView(R.id.error_loading_followers_text_view)
    TextView mErrorLoadingFollowersTextView;

    private FollowersListContract.Presenter mPresenter;
    private FollowersAdapter mFollowersAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followers_list, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color
                .colorAccent));
        mSwipeRefreshLayout.setOnRefreshListener(() -> mPresenter.loadFollowers(true));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager
                .VERTICAL, false));
        mFollowersAdapter = new FollowersAdapter(R.layout.layout_follower_list_item,
                new ArrayList<>());
        mFollowersAdapter.setEnableLoadMore(true);
        mFollowersAdapter.setOnLoadMoreListener(() -> mPresenter.loadMore());
        mFollowersAdapter.setOnFollowerClickListener(follower -> mPresenter.onFollowerClick
                (follower));
        mRecyclerView.setAdapter(mFollowersAdapter);

        mErrorLoadingFollowersTextView.setOnClickListener(view -> onRetryClick());
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
    public void setLoadingIndicatorVisible(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        mProgressBar.setVisibility(visibility);
    }

    @Override
    public void showFollowers(@NonNull List<Follower> followers) {
        mFollowersAdapter.setNewData(followers);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMoreFollowers(@NonNull List<Follower> followers) {
        mFollowersAdapter.addData(followers);
        mFollowersAdapter.loadMoreComplete();
    }

    @Override
    public void setLoadingMoreFollowersErrorVisible(boolean visible) {
        if (visible) {
            mFollowersAdapter.loadMoreFail();
        }
    }

    @Override
    public void setNoMoreFollowersVisible(boolean visible) {
        if (visible) {
            mFollowersAdapter.loadMoreEnd();
        }
    }

    @Override
    public void setNoFollowersVisible(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        mNoFollowersTextView.setVisibility(visibility);
    }

    @Override
    public void setErrorLoadingFollowersVisible(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        mErrorLoadingFollowersTextView.setVisibility(visibility);
    }

    @Override
    public void openFollowerDetailsUi(long id, String name) {
        startActivity(FollowerDetailsActivity.getStartIntent(getContext(), id, name));
    }

    @Override
    public void setPresenter(FollowersListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void onRetryClick() {
        mPresenter.loadFollowers(true);
    }
}
