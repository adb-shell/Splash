package com.karthik.splash.Contracts;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by karthikrk on 15/11/17.
 */

public interface FeedsHomeContract {
    interface view{
        Context getFeedsHomeContext();
    }
    interface presenter{
        ArrayList<String> getPageTitles();
    }
}
