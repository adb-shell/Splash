package com.karthik.splash.Adapters;




import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.karthik.splash.Views.NewFeeds;

import java.util.ArrayList;

/**
 * Created by karthikrk on 15/11/17.
 */

public class FeedViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<String> pageTitles;

    public FeedViewPagerAdapter(FragmentManager fm, ArrayList<String> pageTitles) {
        super(fm);
        this.pageTitles = pageTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return new NewFeeds();
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
