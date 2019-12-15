package com.karthik.splash.storage

import android.content.Context
import android.content.SharedPreferences
import com.karthik.splash.R
import javax.inject.Inject

class Cache {
    var context: Context
    var preferences:SharedPreferences

    @Inject
    constructor(context: Context){
        this.context = context
        preferences = context.getSharedPreferences(
                context.getString(R.string.local_cache), Context.MODE_PRIVATE)
    }

    fun isUserLoggedIn() = preferences.getBoolean(context.getString(R.string.is_user_logged_in),
            false)

    fun setUserLoggedIn() = preferences.edit()
            .putBoolean(context.getString(R.string.is_user_logged_in), true)
            .apply()

    fun isCacheAvail() = preferences.getBoolean(context
            .getString(R.string.is_response_cache_avail), false)

    fun setCacheAvail() = preferences.edit()
            .putBoolean(context.getString(R.string.is_response_cache_avail), true)
            .apply()

    fun setAuthCode(code:String?){
        if(code==null)
            return
        preferences.edit()
                .putString(context.getString(R.string.auth_code), code)
                .apply()
    }

    fun getAuthCode() = preferences.getString(context.getString(R.string.auth_code), null)

    fun setUserName(username:String) = preferences.edit()
            .putString(context.getString(R.string.user_name),username)
            .apply()

    fun getUserName() = preferences.getString(context.getString(R.string.user_name), null)

    fun logOutUser() = preferences.edit().clear().apply()
}