package com.karthik.splash.Views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.karthik.splash.Models.PhotosLists.Photos;
import com.karthik.splash.R;
import com.karthik.splash.Utils;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karthikrk on 04/12/17.
 */

public class PhotoDetail extends AppCompatActivity{

    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.createdTime)
    TextView createdTime;
    @BindView(R.id.nocollection)
    TextView numberOfColections;
    @BindView(R.id.nophotos)
    TextView numberOfPhotos;
    @BindView(R.id.nolikes)
    TextView numberOfLikes;
    @BindView(R.id.feed_detail_image)
    ImageView feedsDetailImage;
    private Photos photo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        ButterKnife.bind(this);
        photo = getIntent().getParcelableExtra(Utils.Photo);
        setupData();
    }

    private void setupData() {
        Picasso.with(this).load(photo.urls.regular).into(feedsDetailImage);
        username.setText(getString(R.string.By)+" "+photo.user.username);
        createdTime.setText(getString(R.string.On)+" "+Utils.parseDate(photo.createdTime));
        numberOfLikes.setText(""+photo.numberLikes);
        numberOfColections.setText(""+photo.user.totalCollection);
        numberOfPhotos.setText(""+photo.user.totalPhotos);
    }
}
