package com.karthik.splash.Views;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.karthik.splash.BuildConfig;
import com.karthik.splash.Contracts.FeedsContract;
import com.karthik.splash.DI.FeedsScreenComponent;
import com.karthik.splash.Modules.FeedsScreenModule;
import com.karthik.splash.R;
import com.karthik.splash.SplashApp;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Feeds extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,FeedsContract.View{


    public final static String IS_FROM_CACHE = "IS_FROM_CACHE";
    private final String code = "code";
    private FeedsScreenComponent feedsScreenComponent;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @Inject
    FeedsContract.Presenter feedsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);
        ButterKnife.bind(this);
        feedsScreenComponent = ((SplashApp)getApplication()).getComponent()
                .plus(new FeedsScreenModule(this));
        feedsScreenComponent.inject(this);
        navigation.setOnNavigationItemSelectedListener(this);
        inflateHome();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        feedsPresenter.clearResource();
        feedsScreenComponent = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent!=null && intent.getData()!=null
                && !intent.getData().getAuthority().isEmpty()
                && BuildConfig.SPLASH_CALLBACK.equals(intent.getData().getAuthority())){
            feedsPresenter.getUserDetail(intent.getData().getQueryParameter(code));
        }
    }

    public static Intent getIntent(Context context, boolean isCache){
        Intent intent = new Intent(context,Feeds.class);
        intent.putExtra(IS_FROM_CACHE,isCache);
        return intent;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        feedsPresenter.onNavigationItemSelected(item.getItemId());
        return true;
    }

    @Override
    public void inflateHome() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,FeedsHome.
                getInstance(getIntent().getBooleanExtra(IS_FROM_CACHE,false)));
        transaction.commit();
    }

    @Override
    public void inflateLikes() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,FeedsLike
                .getInstance());
        transaction.commit();
    }

    @Override
    public void inflateSettings() {

    }

    @Override
    public int getSelectedItem() {
        return navigation.getSelectedItemId();
    }

    @Override
    public void displayUnableToLogin() {
        Toast.makeText(this,getString(R.string.unable_to_login),
                Toast.LENGTH_SHORT).show();
    }
}
