package com.karthik.network.home.models

import com.google.gson.annotations.SerializedName
import com.karthik.network.Constants.Companion.SPLASH_KEY
import com.karthik.network.Constants.Companion.SPLASH_KEY_SECRET
import com.karthik.network.Constants.Companion.SPLASH_LOGIN_CALLBACK

/**
 * Created by karthikrk on 29/11/17.
 */

class OAuthBody(var code: String?) {
    @SerializedName("client_id")
    var clientId: String? = null
    @SerializedName("client_secret")
    var clientSecret: String? = null
    @SerializedName("redirect_uri")
    var redirectUri: String? = null
    @SerializedName("grant_type")
    var grantType: String? = null

    init {
        clientId = SPLASH_KEY
        clientSecret = SPLASH_KEY_SECRET
        redirectUri = SPLASH_LOGIN_CALLBACK
        grantType = "authorization_code"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is OAuthBody) return false

        return this.code?.equals(other.code) ?: false
    }

    override fun hashCode(): Int {
        var result = code?.hashCode() ?: 0
        result = 31 * result + (clientId?.hashCode() ?: 0)
        result = 31 * result + (clientSecret?.hashCode() ?: 0)
        result = 31 * result + (redirectUri?.hashCode() ?: 0)
        result = 31 * result + (grantType?.hashCode() ?: 0)
        return result
    }
}
