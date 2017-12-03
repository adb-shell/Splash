package com.karthik.splash.Views;

import android.content.Context;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.karthik.splash.Contracts.SplashContract;
import com.karthik.splash.Components.SplashScreenComponent;
import com.karthik.splash.Modules.SplashScreenModule;
import com.karthik.splash.R;
import com.karthik.splash.SplashApp;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karthikrk on 12/11/17.
 */

public class Splash extends AppCompatActivity implements SplashContract.SplashView{

    private SplashScreenComponent splashScreenComponent;
    private final int SPLASH_DELAY = 1000;

    @BindView(R.id.splash_image)
    ImageView splashImage;
    @BindView(R.id.splash_display_text)
    TextView splashDisplayText;
    @Inject
    SplashContract.SplashPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        splashScreenComponent = ((SplashApp)getApplication()).getComponent()
                .plus(new SplashScreenModule(this));
        splashScreenComponent.inject(this);
        new Handler().postDelayed(presenter::decideScreens,SPLASH_DELAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashScreenComponent = null;
    }

    @Override
    public Context getSplashContext() {
        return this;
    }

    @Override
    public void showNoInternetScreen() {
        splashDisplayText.setText(getString(R.string.no_internet));
        splashImage.setImageResource(R.drawable.no_internet);
    }

    @Override
    public void showDashBoardScreen(boolean shouldShowCache) {
        startActivity(Feeds.getIntent(this,shouldShowCache));
    }

    @Override
    public void closeScreen() {
        finish();
    }
}
