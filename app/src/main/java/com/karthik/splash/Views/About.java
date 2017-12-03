package com.karthik.splash.Views;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.karthik.splash.Adapters.LibraryAdapter;
import com.karthik.splash.BuildConfig;
import com.karthik.splash.Models.Library.Library;
import com.karthik.splash.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karthikrk on 03/12/17.
 */

public class About extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{

    @BindView(R.id.librarieslist)
    RecyclerView librariesList;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collaspingToolbar;
    @BindView(R.id.expanded_title)
    TextView expandedTitle;
    private int scrollRange = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        librariesList.setLayoutManager(new LinearLayoutManager(this));
        librariesList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        librariesList.setAdapter(new LibraryAdapter(getLibraries()));
        appBarLayout.addOnOffsetChangedListener(this);
    }

    private ArrayList<Library> getLibraries(){
        ArrayList<Library> libraries = new ArrayList<>();
        libraries.add(new Library("Android support appcompat v7","26.1.0"));
        libraries.add(new Library("Android support v4","26.1.0"));
        libraries.add(new Library("Android support design","26.1.0"));
        libraries.add(new Library("Android support custom tabs","26.1.0"));
        libraries.add(new Library("Butterknife","8.8.0"));
        libraries.add(new Library("Butterknife compiler","8.8.0"));
        libraries.add(new Library("Retrofit 2","2.3.0"));
        libraries.add(new Library("Retrofit 2 converter GSON","2.2.0"));
        libraries.add(new Library("Retrofit 2 adapter rxjava 2","2.3.0"));
        libraries.add(new Library("Okhttp logging interceptor","2.3.0"));
        libraries.add(new Library("Dagger","2.1.0"));
        libraries.add(new Library("Dagger compiler","2.1.0"));
        libraries.add(new Library("Rx java 2","2.1.6"));
        libraries.add(new Library("Picasso","2.5.2"));
        libraries.add(new Library("Material image loading","1.0.2"));
        libraries.add(new Library("Chuck interceptor","1.1.0"));
        libraries.add(new Library("JUnit","4.1.2"));
        libraries.add(new Library("Test runner","1.0.1"));
        libraries.add(new Library("Expresso core","3.0.1"));
        return libraries;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if(scrollRange!=-1 && scrollRange+verticalOffset==0){
            collaspingToolbar.setTitle("About");
            expandedTitle.setVisibility(View.INVISIBLE);
        }else{
            collaspingToolbar.setTitle(" ");
            expandedTitle.setVisibility(View.VISIBLE);
        }
        scrollRange = appBarLayout.getTotalScrollRange();
    }
}
