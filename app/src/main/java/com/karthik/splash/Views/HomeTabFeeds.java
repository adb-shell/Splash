package com.karthik.splash.Views;

import android.os.Bundle;
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

import com.karthik.splash.Adapters.FeedsPhotoAdapter;
import com.karthik.splash.Contracts.HomeFeedsTabContract;
import com.karthik.splash.Components.HomeTabFeedsComponent;
import com.karthik.splash.Models.PhotosLists.Photos;
import com.karthik.splash.Modules.HomeTabFeedsModule;
import com.karthik.splash.R;
import com.karthik.splash.SplashApp;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karthikrk on 15/11/17.
 */

public class HomeTabFeeds extends Fragment implements HomeFeedsTabContract.View,PaginatedView{

    private static final String Mode = "Mode";
    private static final String Cache = "cache";
    private HomeTabFeedsComponent homeTabFeedsComponent;
    private FeedsPhotoAdapter feedsAdapter;

    @BindView(R.id.FeedsList)
    RecyclerView feedsList;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.no_internet_image)
    ImageView noInternetImage;
    @BindView(R.id.no_internet_text)
    TextView noInternetText;

    @Inject
    HomeFeedsTabContract.Presenter presenter;

    public static HomeTabFeeds getInstance(int mode,boolean isFromCache){
        Bundle bundle = new Bundle();
        bundle.putInt(Mode,mode);
        bundle.putBoolean(Cache,isFromCache);
        HomeTabFeeds fragment = new HomeTabFeeds();
        fragment.setRetainInstance(true);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new,container,false);
        ButterKnife.bind(this,rootView);
        homeTabFeedsComponent = ((SplashApp)getActivity().getApplication())
                .getComponent()
                .plus(new HomeTabFeedsModule(this,getContext()));
        homeTabFeedsComponent.inject(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.getFeeds(getArguments().getInt(Mode),getArguments().getBoolean(Cache),1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.clearResources();
        homeTabFeedsComponent = null;
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showPhotosList(List<Photos> photos) {
        if(!presenter.isPaginatedItems()){
            feedsList.setVisibility(View.VISIBLE);
            feedsList.setLayoutManager(new LinearLayoutManager(getContext()));
            feedsAdapter = new FeedsPhotoAdapter(photos,this);
            feedsList.setAdapter(feedsAdapter);
            return;
        }
        feedsAdapter.addPaginatedItems(photos);
    }

    @Override
    public void showEmptyScreen() {
        feedsList.setVisibility(View.GONE);
        noInternetText.setVisibility(View.VISIBLE);
        noInternetText.setText(getString(R.string.error_connecting));
    }

    @Override
    public void showNoInternetScreen() {
        feedsList.setVisibility(View.GONE);
        noInternetImage.setVisibility(View.VISIBLE);
        noInternetText.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean isFeedListVisible() {
        return feedsList.getVisibility()==View.VISIBLE;
    }

    @Override
    public void getPage(int pageNo) {
        presenter.getPaginatedFeeds(getArguments().getInt(Mode),pageNo);
    }

    @Override
    public int getMaxPageLimit() {
        return presenter.getPageMaxLimit();
    }
}
