package com.karthik.splash.Modules;

import android.content.Context;

import com.karthik.splash.Storage.Cache;

import dagger.Module;
import dagger.Provides;

/**
 * Created by karthikrk on 12/11/17.
 */
@Module
public class SplashAppModule {
    private Context context;

    public SplashAppModule(Context context){
        this.context = context;
    }

    @Provides
    public Context providesContext(){
        return context;
    }

    @Provides
    public Cache providesCache(Context context){
        return new Cache(context);
    }
}
