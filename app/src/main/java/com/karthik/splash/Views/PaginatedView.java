package com.karthik.splash.Views;

/**
 * Created by karthikrk on 23/11/17.
 */

public interface PaginatedView {
    void getPage(int pageNo);
    int getMaxPageLimit();
}
