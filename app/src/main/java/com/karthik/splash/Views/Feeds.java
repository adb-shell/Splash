package com.karthik.splash.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.karthik.splash.Contracts.FeedsContract;
import com.karthik.splash.DI.FeedsScreenComponent;
import com.karthik.splash.Modules.FeedsScreenModule;
import com.karthik.splash.Presenters.FeedsPresenter;
import com.karthik.splash.R;
import com.karthik.splash.SplashApp;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Feeds extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,FeedsContract.View{


    private final static String IS_FROM_CACHE = "IS_FROM_CACHE";
    private FeedsScreenComponent feedsScreenComponent;


    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @Inject
    FeedsPresenter feedsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);
        ButterKnife.bind(this);
        feedsScreenComponent = ((SplashApp)getApplication()).getComponent()
                .plus(new FeedsScreenModule(this));
        feedsScreenComponent.inject(this);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        feedsScreenComponent = null;
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

    }

    @Override
    public void inflateLikes() {

    }

    @Override
    public void inflateSettings() {

    }
}
