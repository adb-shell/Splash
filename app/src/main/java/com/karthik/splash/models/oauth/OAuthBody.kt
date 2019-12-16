package com.karthik.splash.models.oauth

import com.karthik.splash.BuildConfig

/**
 * Created by karthikrk on 29/11/17.
 */

class OAuthBody(var code: String?) {
    var client_id: String? = null
    var client_secret: String? = null
    var redirect_uri: String? = null
    var grant_type: String? = null

    init {
        client_id = BuildConfig.SPLASH_KEY
        client_secret = BuildConfig.SPLASH_KEY_SECRET
        redirect_uri = BuildConfig.SPLASH_LOGIN_CALLBACK
        grant_type = "authorization_code"
    }
}
