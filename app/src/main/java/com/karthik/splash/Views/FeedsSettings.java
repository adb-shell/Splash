package com.karthik.splash.Views;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
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
import com.karthik.splash.root.SplashApp;

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
    private final String permission = Manifest.permission.READ_EXTERNAL_STORAGE;

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
        presenter.showDownloadedImages();
    }

    @Override
    public void showLoggedInView(String username) {
        logout.setVisibility(View.VISIBLE);
        this.username.setText(String.format(getString(R.string.logged_in_as),username));
    }

    @Override
    public void showNonLoggedInView() {
        profileImage.setImageResource(R.drawable.logout);
        username.setText(getString(R.string.login));
        logout.setVisibility(View.GONE);
    }

    @Override
    public void openFolder() {
        startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
    }

    @Override
    public boolean isReadPermissionGranted() {
        return ContextCompat.checkSelfPermission(getContext(),permission)==PermissionChecker.PERMISSION_GRANTED;
    }

    @Override
    public void askReadPermission() {
        ActivityCompat.requestPermissions(getActivity(),new String[]{permission},0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults!=null && grantResults.length>0 && grantResults[0]== PermissionChecker.PERMISSION_GRANTED){
            downloads();
        }
    }
}
