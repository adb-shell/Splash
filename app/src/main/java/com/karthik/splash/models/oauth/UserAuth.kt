package com.karthik.splash.models.oauth

import com.google.gson.annotations.SerializedName

/**
 * Created by karthikrk on 29/11/17.
 */

data class UserAuth(
        @SerializedName("access_token") var accessToken: String? = null,
        @SerializedName("token_type") var tokenType: String? = null,
        @SerializedName("scope") var scope: String? = null,
        @SerializedName("created_at") var createdAt: String? = null
)
