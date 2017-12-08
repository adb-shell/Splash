package com.karthik.splash;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;

import com.karthik.splash.Models.PhotosLists.Photos;
import com.karthik.splash.RestServices.UserOfflineException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by karthikrk on 12/11/17.
 */

public class Utils {
    public enum NetworkErrorType {OFFLINE,IOEXCEPTION}
    public enum ResponseType {NEW,TRENDING,FEATURED,LIKES}
    public static final String Photo="photo";
    private static final String UnsplashDateFormat = "yyyy-MM-dd'T'HH:mm:ss";
    public static boolean isInternetAvailable(Context context){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static String convertArrayListToString(List<? extends Object> responseList){
        return new Gson().toJson(responseList);
    }

    @Nullable
    public static ArrayList<? extends Object> convertStringToArrayList(String response,ResponseType responseType){
        if(responseType==ResponseType.NEW||responseType==ResponseType.TRENDING||responseType==ResponseType.FEATURED){
            Type type = new TypeToken<ArrayList<Photos>>() {}.getType();
            return new Gson().fromJson(response,type);
        }
        return null;
    }

    public static Utils.NetworkErrorType getErrorType(Throwable e){
        if(e instanceof UserOfflineException)
            return Utils.NetworkErrorType.OFFLINE;
        return Utils.NetworkErrorType.IOEXCEPTION;
    }

    public static String parseDate(String receivedDate){
        SimpleDateFormat formatter = new SimpleDateFormat(UnsplashDateFormat);
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(formatter.parse(receivedDate));
            return cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
