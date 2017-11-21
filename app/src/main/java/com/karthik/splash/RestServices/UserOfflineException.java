package com.karthik.splash.RestServices;



import java.io.IOException;

/**
 * Created by karthikrk on 19/11/17.
 */

public class UserOfflineException extends IOException {

    @Override
    public String getMessage() {
        return "No internet connection";
    }
}
