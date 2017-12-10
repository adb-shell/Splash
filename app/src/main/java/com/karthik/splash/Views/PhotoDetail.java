package com.karthik.splash.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.karthik.splash.Adapters.CircularTransform;
import com.karthik.splash.Components.PhotoDetailComponent;
import com.karthik.splash.Contracts.PhotoDetailContract;
import com.karthik.splash.Models.PhotoDetail.PhotoDetailInfo;
import com.karthik.splash.Models.PhotosLists.Photos;
import com.karthik.splash.Modules.PhotoDetailModule;
import com.karthik.splash.R;
import com.karthik.splash.SplashApp;
import com.karthik.splash.Utils;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by karthikrk on 04/12/17.
 */

public class PhotoDetail extends AppCompatActivity implements
        PhotoDetailContract.PhotoDetailView{

    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.createdTime)
    TextView createdTime;
    @BindView(R.id.feed_detail_image)
    ImageView feedsDetailImage;
    @BindView(R.id.loader)
    ProgressBar loader;
    @BindView(R.id.additionalInfo)
    LinearLayout additionalInfo;
    @BindView(R.id.userimg)
    ImageView userImg;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.no_views)
    TextView noViews;
    @BindView(R.id.no_likes)
    TextView noLikes;
    @BindView(R.id.userLoc)
    TextView userLoc;
    @BindView(R.id.no_downloads)
    TextView noDownloads;

    @Inject
    PhotoDetailContract.PhotoDetailPresenter presenter;


    private Photos photo;
    private PhotoDetailComponent photoDetailComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        ButterKnife.bind(this);
        photo = getIntent().getParcelableExtra(Utils.Photo);
        photoDetailComponent = ((SplashApp)getApplication()).getComponent()
                .plus(new PhotoDetailModule(this));
        photoDetailComponent.inject(this);

        Picasso.with(this).load(photo.urls.regular).into(feedsDetailImage);
        username.setText(getString(R.string.By)+" "+photo.user.username);
        createdTime.setText(getString(R.string.On)+" "+Utils.parseDate(photo.createdTime));
        presenter.getPhotoDetails(photo.id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        photoDetailComponent = null;
    }

    @Override
    public void showPhotoDetails(PhotoDetailInfo photoDetailInfo) {
        Picasso.with(this)
                .load(photoDetailInfo.user.profileImage.medium)
                .transform(new CircularTransform())
                .into(userImg);
        noViews.setText(String.valueOf(photoDetailInfo.user.totalCollections));
        noLikes.setText(String.valueOf(photoDetailInfo.likes));
        userLoc.setText(photoDetailInfo.location==null?getString(R.string.unknown):photoDetailInfo.location.country);
        noDownloads.setText(String.valueOf(photoDetailInfo.downloads));
    }

    @Override
    public void showDefaultView() {
        additionalInfo.setVisibility(View.GONE);
        loader.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        additionalInfo.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        additionalInfo.setVisibility(View.VISIBLE);
        loader.setVisibility(View.GONE);
    }

    @Override
    public void showLoginRequired() {
        Toast.makeText(this,getString(R.string.like_error),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errorLikingPhoto() {
        Toast.makeText(this,getString(R.string.like_error_1),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successLikingPhoto() {
        Toast.makeText(this,getString(R.string.like_photo_success),
                Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.like)
    public void likePhoto(){
        presenter.likeThePhoto(photo.id);
    }

    @OnClick(R.id.download)
    public void downLoadClick(){
        presenter.downloadPhoto(photo.urls.full);
    }

    @OnClick(R.id.share)
    public void shareImage(){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,photo.urls.regular);
        startActivity(Intent.createChooser(sharingIntent,getString(R.string.share_photo)));
    }
}
