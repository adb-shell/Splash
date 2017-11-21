package com.karthik.splash.Adapters;




import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.karthik.splash.Views.HomeTabFeeds;

import java.util.ArrayList;

/**
 * Created by karthikrk on 15/11/17.
 */

public class FeedViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<String> pageTitles;
    private boolean isFromCache;

    public FeedViewPagerAdapter(FragmentManager fm,
                                ArrayList<String> pageTitles,
                                boolean isFromCache) {
        super(fm);
        this.pageTitles = pageTitles;
        this.isFromCache = isFromCache;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return HomeTabFeeds.getInstance(1,isFromCache);
            case 2:
                return HomeTabFeeds.getInstance(2,isFromCache);
            default:
                return HomeTabFeeds.getInstance(0,isFromCache);
        }
    }

    @Override
    public int getCount() {
        return pageTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles.get(position);
    }
}
