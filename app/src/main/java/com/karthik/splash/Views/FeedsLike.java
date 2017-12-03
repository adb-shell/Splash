package com.karthik.splash.Views;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;


import com.karthik.splash.Adapters.FeedsPhotoAdapter;
import com.karthik.splash.Contracts.FeedsLikesContract;
import com.karthik.splash.Components.LikeTabFeedsComponent;
import com.karthik.splash.Models.PhotosLists.Photos;
import com.karthik.splash.Modules.LikeTabFeedsModule;
import com.karthik.splash.R;
import com.karthik.splash.SplashApp;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by karthikrk on 25/11/17.
 */

public class FeedsLike extends Fragment implements FeedsLikesContract.view{

    private LikeTabFeedsComponent component;

    @BindView(R.id.login_wrapper)
    View loginWrapper;
    @BindView(R.id.empty_screen_wrapper)
    LinearLayout emptyWrapper;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.likesList)
    RecyclerView likesList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    FeedsLikesContract.presenter presenter;


    public static FeedsLike getInstance(){
        FeedsLike feedsLike = new FeedsLike();
        return feedsLike;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_feeds_like,container,
                false);
        ButterKnife.bind(this,layout);
        component = ((SplashApp)getActivity().getApplication()).getComponent()
                .plus(new LikeTabFeedsModule(this));
        component.inject(this);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.decideScreenType();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.clearResources();
        component = null;
    }

    @Override
    public void showLoginScreen() {
        loginWrapper.setVisibility(View.VISIBLE);
        likesList.setVisibility(View.GONE);
        emptyWrapper.setVisibility(View.GONE);
        toolbar.setTitle(getString(R.string.login));
    }

    @Override
    public void showLikesList(List<Photos> photos) {
        loginWrapper.setVisibility(View.GONE);
        likesList.setVisibility(View.VISIBLE);
        emptyWrapper.setVisibility(View.GONE);
        toolbar.setTitle(getString(R.string.title_likes));
        likesList.setLayoutManager(new LinearLayoutManager(getContext()));
        likesList.setAdapter(new FeedsPhotoAdapter(photos,null));
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyLikedScreen() {
        loginWrapper.setVisibility(View.GONE);
        likesList.setVisibility(View.GONE);
        emptyWrapper.setVisibility(View.VISIBLE);
    }



    @Override
    public void openLoginOauthUrl(String oauthurl) {
        Uri uri = Uri.parse(oauthurl);
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        intentBuilder.setToolbarColor(ContextCompat.getColor(getContext(),
                R.color.icons));
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(getContext(),
                R.color.icons));
        CustomTabsIntent customTabsIntent = intentBuilder.build();
        customTabsIntent.launchUrl(getActivity(),uri);
    }

    @OnClick(R.id.login)
    public void login(){
        presenter.login();
    }
}
