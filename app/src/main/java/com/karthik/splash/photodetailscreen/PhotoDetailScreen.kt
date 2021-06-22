package com.karthik.splash.photodetailscreen

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.esafirm.rxdownloader.RxDownloader
import com.karthik.network.home.bottomliketab.models.Photos
import com.karthik.network.photodetailscreen.models.LikeResponse
import com.karthik.network.photodetailscreen.models.PhotoDetailInfo
import com.karthik.network.photodetailscreen.models.PhotoDetailsNetworkState
import com.karthik.splash.R
import com.karthik.splash.misc.CircularTransform
import com.karthik.splash.misc.Utils
import com.karthik.splash.misc.loadImage
import com.karthik.splash.misc.toPhotos
import com.karthik.splash.photodetailscreen.di.PhotoDetailScreenComponent
import com.karthik.splash.photodetailscreen.di.PhotoDetailScreenModule
import com.karthik.splash.root.SplashApp
import kotlinx.android.synthetic.main.activity_photo_detail.*
import javax.inject.Inject

class PhotoDetailScreen : AppCompatActivity(), View.OnClickListener {

    private var photoDetailScreenComponent: PhotoDetailScreenComponent? = null
    private val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
    private val imageLoadDuration = 0

    @Inject
    lateinit var viewmodelfactory: PhotoDetailScreenViewModelFactory
    private  var photo: Photos? = null
    private lateinit var viewmodel: PhotoDetailScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)
        photo = intent.extras?.toPhotos()
        photoDetailScreenComponent = (application as SplashApp).getComponent()
                .plus(PhotoDetailScreenModule())
        photoDetailScreenComponent?.inject(this)
        viewmodel =
                ViewModelProvider(this,
                        viewmodelfactory).get(PhotoDetailScreenViewModel::class.java)

        username.text = getString(R.string.By, photo?.user?.name)
        createdtime.text = getString(R.string.On, Utils.parseDate(photo?.createdTime))
        photo?.urls?.regular?.let { url ->
            feeddetailimage.loadImage(url, imageLoadDuration)
        }


        viewmodel.getPhotoDetail(photo?.id ?: "")
        viewmodel.photodetails.observe(this, Observer<PhotoDetailInfo> { photoinfo ->
            showPhotoDetails(photoinfo)
        })
        viewmodel.photolike.observe(this,  { likeresponse ->
            Toast.makeText(this,
                    getString(R.string.like_photo_success), Toast.LENGTH_SHORT).show()
        })
        viewmodel.networkState.observe(this, { networkstate ->
                    when (networkstate) {
                        is PhotoDetailsNetworkState.PhotoDetailsNetworkLoadError   ->
                            showDefaultView()
                        is PhotoDetailsNetworkState.PhotoLikeNetworkLoadError      ->
                            errorLikingPhoto()
                        is PhotoDetailsNetworkState.PhotoDetailsNetworkLoadSuccess ->
                            hideLoading()
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        photoDetailScreenComponent = null
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.likewrapper -> {
                if (viewmodel.isUserLoggedIn()) {
                    showLoading()
                    viewmodel.likeThePhoto(photo?.id ?: "")
                    return
                }
                showLoginRequired()
            }
            R.id.donwloadwrapper -> {
                if (ContextCompat.checkSelfPermission(this,
                                permission) == PermissionChecker.PERMISSION_GRANTED) {
                    startDownloading()
                    return
                }
                ActivityCompat.requestPermissions(this, arrayOf(permission), 0)
            }
            R.id.sharewrapper -> {
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "text/html"
                sharingIntent.putExtra(Intent.EXTRA_TEXT, photo?.urls?.regular)
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_photo)))
            }
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty() && grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
            startDownloading()
            return
        }
        Toast.makeText(this, getString(R.string.error_permission), Toast.LENGTH_LONG).show()
    }

    private fun showPhotoDetails(photoDetailInfo: PhotoDetailInfo) {
        photoDetailInfo.user?.profileImage?.medium?.let { url ->
            userimg.loadImage(url, imageLoadDuration, CircularTransform())
        }
        nolikes.text = photoDetailInfo.likes.toString()
        noviews.text = photoDetailInfo.user?.totalCollections.toString()
        userloc.text = getUserLocation(photoDetailInfo)
        nodownloads.text = photoDetailInfo.downloads.toString()

        likewrapper.setOnClickListener(this)
        donwloadwrapper.setOnClickListener(this)
        sharewrapper.setOnClickListener(this)
    }

    private fun errorLikingPhoto() =
            Toast.makeText(this,
                    getString(R.string.like_error_1), Toast.LENGTH_LONG).show()

    private fun getUserLocation(photoDetailInfo: PhotoDetailInfo?): String? {
        return photoDetailInfo?.location?.country?.let {
            photoDetailInfo.location?.country
        } ?: getString(R.string.unknown)
    }

    private fun showLoginRequired() =
            Toast.makeText(this,
                    getString(R.string.like_error), Toast.LENGTH_LONG).show()

    private fun hideLoading() {
        additionalinfo.visibility = View.VISIBLE
        loader.visibility = View.GONE
    }

    private fun showDefaultView() {
        additionalinfo.visibility = View.GONE
        loader.visibility = View.GONE
    }

    private fun showLoading() {
        additionalinfo.visibility = View.GONE
        loader.visibility = View.VISIBLE
    }

    private fun startDownloading() {
        RxDownloader.getInstance(this)
                .download(photo?.urls?.full, photo?.id, Utils.photomimetype)
                .subscribe({ path ->
                    Toast.makeText(this,
                            getString(R.string.success_downloading, path),
                            Toast.LENGTH_SHORT).show()
                }, {
                    Toast.makeText(this, getString(R.string.error_downloading), Toast.LENGTH_LONG)
                            .show()
                })
    }
}
