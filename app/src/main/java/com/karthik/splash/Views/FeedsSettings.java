package com.karthik.splash.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.karthik.splash.Components.SettingsTabFeedsComponent;
import com.karthik.splash.Contracts.FeedsSettingsContract;
import com.karthik.splash.Modules.SettingsTabFeedsModule;
import com.karthik.splash.R;
import com.karthik.splash.SplashApp;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by karthikrk on 02/12/17.
 */

public class FeedsSettings extends Fragment implements
        FeedsSettingsContract.FeedsSettingsView{

    @BindView(R.id.profile_image)
    ImageView profileImage;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.logout)
    LinearLayout logout;
    @Inject
    FeedsSettingsContract.FeedsSettingsPresenter presenter;


    private SettingsTabFeedsComponent component;

    public static FeedsSettings getInstance(){
        return new FeedsSettings();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_feeds_settings,container,
                false);
        ButterKnife.bind(this,layoutView);
        component = ((SplashApp)getActivity().getApplication()).getComponent()
                .plus(new SettingsTabFeedsModule(this));
        component.inject(this);
        return layoutView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.decideScreen();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        component = null;
    }

    @OnClick(R.id.logout)
    public void logout(){
        presenter.logOutUser();
    }

    @OnClick(R.id.about)
    public void about(){
        getContext().startActivity(new Intent(getContext(),About.class));
    }

    @OnClick(R.id.downloads)
    public void downloads(){
        //TODO:open downloaded photos thumbnails.
    }

    @Override
    public void showLoggedInView() {
        logout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNonLoggedInView() {
        profileImage.setImageResource(R.drawable.logout);
        username.setText(getString(R.string.login));
        logout.setVisibility(View.GONE);
    }
}
