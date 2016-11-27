package com.mondo.twitterclient.user.followers.details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mondo.twitterclient.R;
import com.mondo.twitterclient.user.followers.data.Follower;
import com.mondo.twitterclient.user.followers.data.Tweet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mahmoud on 11/26/16.
 */

public class FollowerDetailsFragment extends Fragment implements FollowerDetailsContract.View {
    private static final String ARG_ID = "ARG_ID";

    public static FollowerDetailsFragment newInstance(long id) {
        FollowerDetailsFragment followerDetailsFragment = new FollowerDetailsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ID, id);
        followerDetailsFragment.setArguments(args);
        return followerDetailsFragment;
    }

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.profile_pic_image_view)
    ImageView mProfilePicImageView;

    @BindView(R.id.profile_background_pic_image_view)
    ImageView mProfileBackgroundPicImageView;

    @BindView(R.id.error_loading_follower_text_view)
    TextView mErrorLoadingFollowerTextView;

    @BindView(R.id.tweets_recycler_view)
    RecyclerView mTweetsRecyclerView;

    @BindView(R.id.tweets_loading_progress_bar)
    ProgressBar mTweetsLoadingProgressBar;

    @BindView(R.id.no_tweets_text_view)
    TextView mNoTweetsTextView;

    @BindView(R.id.error_loading_tweets_text_view)
    TextView mErrorLoadingTweetsTextView;

    private FollowerDetailsContract.Presenter mPresenter;
    private long mId;

    private TweetsAdapter mTweetsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mId = args.getLong(ARG_ID, -1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follower_details, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        mErrorLoadingFollowerTextView.setOnClickListener(view -> mPresenter.loadFollower(mId));

        mTweetsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        mTweetsAdapter = new TweetsAdapter(R.layout.layout_tweet_list_item, new ArrayList<>());
        mTweetsRecyclerView.setAdapter(mTweetsAdapter);

        mErrorLoadingTweetsTextView.setOnClickListener(view -> mPresenter.loadTweets(mId));
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
        mPresenter.loadFollower(mId);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }

    @Override
    public void setLoadingIndicatorEnabled(boolean enabled) {
        int visibility = enabled ? View.VISIBLE : View.GONE;
        mProgressBar.setVisibility(visibility);
        if (enabled) {
            mErrorLoadingFollowerTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showFollower(@NonNull Follower follower) {
        updateFollowerImageUrl(follower);
        String imageUrl = follower.getImageUrl();
        Glide.with(this).load(imageUrl).crossFade().centerCrop().into(mProfilePicImageView);

        String backgroundImageUrl = follower.getBackgroundImageUrl();
        Glide.with(this).load(backgroundImageUrl).crossFade().centerCrop().into(
                mProfileBackgroundPicImageView);
    }

    private void updateFollowerImageUrl(Follower follower) {
        String imageUrl = follower.getImageUrl();
        if (imageUrl != null) {
            imageUrl = imageUrl.replace("normal", "bigger");
        }
        follower.setImageUrl(imageUrl);
    }

    @Override
    public void showErrorLoadingFollower() {
        mErrorLoadingFollowerTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setLoadingTweetsIndicatorEnabled(boolean enabled) {
        if (enabled) {
            mTweetsLoadingProgressBar.setVisibility(View.VISIBLE);
            mTweetsRecyclerView.setVisibility(View.GONE);
            mNoTweetsTextView.setVisibility(View.GONE);
        } else {
            mTweetsLoadingProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showTweets(@NonNull List<Tweet> tweets) {
        mTweetsAdapter.setNewData(tweets);
        mTweetsRecyclerView.setVisibility(View.VISIBLE);
        mNoTweetsTextView.setVisibility(View.GONE);
        mErrorLoadingFollowerTextView.setVisibility(View.GONE);
    }

    @Override
    public void showNoTweets() {
        mNoTweetsTextView.setVisibility(View.VISIBLE);
        mErrorLoadingFollowerTextView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorLoadingTweets() {
        mErrorLoadingFollowerTextView.setVisibility(View.VISIBLE);
        mTweetsLoadingProgressBar.setVisibility(View.GONE);
        mTweetsRecyclerView.setVisibility(View.GONE);
        mNoTweetsTextView.setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(FollowerDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
