package com.karthik.splash.photodetailscreen

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.karthik.splash.Adapters.CircularTransform
import com.karthik.splash.Models.PhotoDetail.PhotoDetailInfo
import com.karthik.splash.Models.PhotosLists.Photos
import com.karthik.splash.R
import com.karthik.splash.Utils
import com.karthik.splash.root.SplashApp
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_photo_detail.*
import javax.inject.Inject

class PhotoDetailScreen:AppCompatActivity(), PhotoDetailScreenContract.view,View.OnClickListener {

    private var photoDetailScreenComponent: PhotoDetailScreenComponent?=null
    private lateinit var photo: Photos
    private val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE

    @Inject
    lateinit var presenter: PhotoDetailScreenContract.presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)
        photo = intent.getParcelableExtra(Utils.Photo)
        photoDetailScreenComponent = (application as SplashApp).getComponent()
                .plus(PhotoDetailScreenModule(this))
        photoDetailScreenComponent?.inject(this)
        username.text = getString(R.string.By,photo.user.name)
        createdtime.text = getString(R.string.On,Utils.parseDate(photo.createdTime))
        Picasso.with(this).load(photo.urls.regular).into(feeddetailimage)
        likewrapper.setOnClickListener(this)
        donwloadwrapper.setOnClickListener(this)
        sharewrapper.setOnClickListener(this)
        presenter.getPhotoDetails(photo.id)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearResources()
        photoDetailScreenComponent = null
    }

    override fun showPhotoDetails(photoDetailInfo: PhotoDetailInfo) {
        Picasso.with(this)
                .load(photoDetailInfo.user?.profileImage?.medium)
                .transform(CircularTransform())
                .into(userimg)
        Picasso.with(this)
                .load(photoDetailInfo.user?.profileImage?.medium)
                .transform(CircularTransform())
                .into(userimg)
        nolikes.text = photoDetailInfo.likes.toString()
        noviews.text = photoDetailInfo.user?.totalCollections.toString()
        userloc.text = getUserLocation(photoDetailInfo)
        nodownloads.text = photoDetailInfo.downloads.toString()
    }



    override fun showDefaultView() {
        additionalinfo.visibility = View.GONE
        loader.visibility = View.GONE
    }

    override fun showLoading() {
        additionalinfo.visibility = View.GONE
        loader.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        additionalinfo.visibility = View.VISIBLE
        loader.visibility = View.GONE
    }

    override fun showLoginRequired() = Toast.makeText(this,
            getString(R.string.like_error),Toast.LENGTH_LONG).show()

    override fun errorLikingPhoto() = Toast.makeText(this,
            getString(R.string.like_error_1),Toast.LENGTH_LONG).show()

    override fun successLikingPhoto() {
        Toast.makeText(this,
                getString(R.string.like_photo_success),Toast.LENGTH_SHORT).show()
    }

    override fun showFileDownloadedSuccessMessage(path: String?) {
        Toast.makeText(this,
                getString(R.string.success_downloading,path),Toast.LENGTH_SHORT).show()
    }

    override fun showFileErrorDownloading() = Toast.makeText(this,
                getString(R.string.error_downloading), Toast.LENGTH_LONG).show()

    override fun isPermissionGranted(): Boolean =
            ContextCompat.checkSelfPermission(this,permission) == PermissionChecker.PERMISSION_GRANTED;

    override fun askPermission() =
            ActivityCompat.requestPermissions(this, arrayOf(permission), 0)

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(grantResults.isNotEmpty() && grantResults[0] == PermissionChecker.PERMISSION_GRANTED){

        }
        Toast.makeText(this, getString(R.string.error_permission), Toast.LENGTH_LONG).show()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.likewrapper-> presenter.likeThePhoto(photo.id)
            R.id.donwloadwrapper-> presenter.downloadPhoto(this, photo.id, photo.urls.full)
            R.id.sharewrapper->{
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "text/html"
                sharingIntent.putExtra(Intent.EXTRA_TEXT, photo.urls.regular)
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_photo)))
            }
        }
    }

    private fun getUserLocation(photoDetailInfo: PhotoDetailInfo?): String {
        return if (photoDetailInfo?.location == null || photoDetailInfo.location.country == null) {
            getString(R.string.unknown)
        } else {
            photoDetailInfo.location.country
        }
    }
}