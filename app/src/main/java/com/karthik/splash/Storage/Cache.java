package com.karthik.splash.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.karthik.splash.R;

import javax.inject.Inject;

/**
 * Created by karthikrk on 12/11/17.
 */

public class Cache {
    private SharedPreferences preferences;
    private Context context;

    @Inject
    public Cache(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(
                context.getString(R.string.local_cache),Context.MODE_PRIVATE);
    }

    public boolean isUserLoggedIn(){
        return preferences.getBoolean(context.getString(R.string.is_user_logged_in)
                ,false);
    }

    public void setUserLoggedIn(){
        preferences.edit()
                .putBoolean(context.getString(R.string.is_user_logged_in),true)
                .apply();
    }

    public boolean isCacheAvail(){
        return preferences.getBoolean(context
                        .getString(R.string.is_response_cache_avail),false);
    }

    public void setCacheAvail(){
        preferences.edit()
                .putBoolean(context.getString(R.string.is_response_cache_avail),true)
                .apply();

    }

    public void setAuthCode(String code){
        preferences.edit()
                .putString(context.getString(R.string.auth_code),code)
                .apply();
    }

    public String getAuthCode(){
        return preferences.getString(context.getString(R.string.auth_code),null);
    }

    public void setUserName(String userName){
        preferences.edit()
                .putString(context.getString(R.string.user_name),userName)
                .apply();
    }

    public String getUserName(){
        return preferences.getString(context.getString(R.string.user_name),null);
    }

    public void logOutUser(){
        preferences.edit().clear().apply();
    }
}
