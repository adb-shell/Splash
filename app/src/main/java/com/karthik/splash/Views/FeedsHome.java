package com.karthik.splash.Views;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karthik.splash.Adapters.FeedViewPagerAdapter;
import com.karthik.splash.Contracts.FeedsHomeContract;
import com.karthik.splash.DI.FeedsHomeComponent;
import com.karthik.splash.Modules.FeedsHomeModule;
import com.karthik.splash.R;
import com.karthik.splash.SplashApp;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karthikrk on 14/11/17.
 */

public class FeedsHome extends Fragment implements FeedsHomeContract.view{

    private FeedsHomeComponent feedsHomeComponent;

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @Inject
    FeedsHomeContract.presenter presenter;


    public static FeedsHome getInstance(boolean isCached){
        Bundle bundle = new Bundle();
        bundle.putBoolean(Feeds.IS_FROM_CACHE,isCached);
        FeedsHome home = new FeedsHome();
        home.setArguments(bundle);
        return home;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feeds_home,container,false);
        ButterKnife.bind(this,rootView);
        feedsHomeComponent = ((SplashApp)getActivity().getApplication()).getComponent()
                .plus(new FeedsHomeModule(this));
        feedsHomeComponent.inject(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        feedsHomeComponent = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intializeTab();
    }

    @Override
    public Context getFeedsHomeContext() {
        return getActivity();
    }


    private void intializeTab() {
        viewPager.setAdapter(new FeedViewPagerAdapter(
                getChildFragmentManager(),
                presenter.getPageTitles(),
                getArguments().getBoolean(Feeds.IS_FROM_CACHE)));
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }
}
