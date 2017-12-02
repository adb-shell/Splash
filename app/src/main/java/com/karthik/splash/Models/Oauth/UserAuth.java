package com.karthik.splash.Models.Oauth;

import com.google.gson.annotations.SerializedName;

/**
 * Created by karthikrk on 29/11/17.
 */

public class UserAuth {
    @SerializedName("access_token")
    public String accessToken;
    @SerializedName("token_type")
    public String tokenType;
    @SerializedName("scope")
    public String scope;
    @SerializedName("created_at")
    public String createdAt;
}
