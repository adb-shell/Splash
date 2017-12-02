package com.karthik.splash.Models.Oauth;

import com.karthik.splash.BuildConfig;

/**
 * Created by karthikrk on 29/11/17.
 */

public class OAuthBody {
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String code	;
    private String grant_type;

    public OAuthBody(String code){
        client_id = BuildConfig.SPLASH_KEY;
        client_secret =BuildConfig.SPLASH_KEY_SECRET;
        redirect_uri = BuildConfig.SPLASH_LOGIN_CALLBACK;
        grant_type="authorization_code";
        this.code = code;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
