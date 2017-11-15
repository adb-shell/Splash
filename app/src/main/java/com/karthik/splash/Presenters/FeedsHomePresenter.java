package com.karthik.splash.Presenters;

import com.karthik.splash.Contracts.FeedsHomeContract;
import com.karthik.splash.R;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by karthikrk on 15/11/17.
 */

public class FeedsHomePresenter implements FeedsHomeContract.presenter{
    private FeedsHomeContract.view feedsView;


    @Inject
    public FeedsHomePresenter(FeedsHomeContract.view feedsView){
        this.feedsView = feedsView;
    }

    @Override
    public ArrayList<String> getPageTitles() {
        ArrayList<String> tabList = new ArrayList<>();
        tabList.add(feedsView.getFeedsHomeContext()
                .getString(R.string.frag_title_1));
        tabList.add(feedsView.getFeedsHomeContext()
                .getString(R.string.frag_title_2));
        tabList.add(feedsView.getFeedsHomeContext()
                .getString(R.string.frag_title_3));
        return tabList;
    }
}
