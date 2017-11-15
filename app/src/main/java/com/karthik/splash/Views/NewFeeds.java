package com.karthik.splash.Views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karthik.splash.R;

import butterknife.ButterKnife;

/**
 * Created by karthikrk on 15/11/17.
 */

public class NewFeeds extends Fragment{

    private final String Mode = "Mode";


    public NewFeeds getInstance(int mode){
        Bundle bundle = new Bundle();
        bundle.putInt(Mode,mode);
        NewFeeds fragment = new NewFeeds();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.fragment_new,container,false);
       ButterKnife.bind(this,rootView);
       return rootView;
    }
}
