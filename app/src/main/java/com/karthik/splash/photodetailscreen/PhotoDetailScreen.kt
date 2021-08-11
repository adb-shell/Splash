package com.karthik.splash.photodetailscreen

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.ViewModelProvider
import com.esafirm.rxdownloader.RxDownloader
import com.google.accompanist.coil.rememberCoilPainter
import com.karthik.network.home.bottomliketab.models.Photos
import com.karthik.network.home.bottomliketab.models.Urls
import com.karthik.network.photodetailscreen.models.PhotoDetailInfo
import com.karthik.network.photodetailscreen.models.User
import com.karthik.splash.R
import com.karthik.splash.misc.Utils
import com.karthik.splash.misc.toPhotos
import com.karthik.splash.photodetailscreen.di.PhotoDetailScreenComponent
import com.karthik.splash.photodetailscreen.di.PhotoDetailScreenModule
import com.karthik.splash.root.SplashApp
import com.karthik.splash.ui.*
import com.karthik.splash.ui.Dimensions.Companion.eightDp
import com.karthik.splash.ui.Dimensions.Companion.hundredDp
import com.karthik.splash.ui.Dimensions.Companion.thirtyTwodp
import com.karthik.splash.ui.Dimensions.Companion.twentyFourDp
import javax.inject.Inject

class PhotoDetailScreen : AppCompatActivity() {

    private var photoDetailScreenComponent: PhotoDetailScreenComponent? = null
    private val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE

    @Inject
    lateinit var viewmodelfactory: PhotoDetailScreenViewModelFactory
    private var photo: Photos? = null
    private lateinit var viewmodel: PhotoDetailScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        photo = intent.extras?.getBundle(Utils.photo)?.toPhotos()
        photoDetailScreenComponent = (application as SplashApp).getComponent()
            .plus(PhotoDetailScreenModule())
        photoDetailScreenComponent?.inject(this)
        viewmodel = ViewModelProvider(
            this,
            viewmodelfactory
        ).get(PhotoDetailScreenViewModel::class.java)

        viewmodel.getPhotoDetail(photo?.id ?: "")

        setContent {
            val screenStatus = viewmodel.screenStatus.observeAsState()
            SplashTheme {
                renderUI(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    screenStatus = screenStatus
                )
            }
        }

        viewmodel.buttonClicked.observe(this, { clickEvent ->
            handleButtonClick(clickEvent)
        })
    }

    @Composable
    private fun renderUI(screenStatus: State<ScreenStatus?>, modifier: Modifier) {
        Surface(
            modifier = modifier
        ) {
            when (screenStatus.value) {
                is ScreenStatus.ShowProgress -> {
                    ProgressIndicator()
                }
                is ScreenStatus.ErrorLikingPhoto -> {
                    Toast.makeText(
                        this,
                        getString(R.string.like_error_1),
                        Toast.LENGTH_LONG
                    ).show()
                }
                is ScreenStatus.PhotoLikeSuccess -> {
                    Toast.makeText(
                        this,
                        getString(R.string.like_photo_success),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is ScreenStatus.ErrorFetchingPhotoDetail -> {
                    renderErrorScreen(tryAgain = {
                        viewmodel.getPhotoDetail(photo?.id ?: "")
                    })
                }
                is ScreenStatus.PhotoDetailScreen -> {
                    renderPhotoDetailScreen(
                        screenStatus.value as ScreenStatus.PhotoDetailScreen,
                        shareClick = { viewmodel.shareClicked() },
                        downloadClick = { viewmodel.downloadClicked() }
                    )
                }
            }
        }
    }

    private fun handleButtonClick(clickEvent: PhotoDetailEvent?) {
        when (clickEvent) {
            PhotoDetailEvent.SHARE_CLICK -> {
                shareImage()
            }
            PhotoDetailEvent.DOWNLOAD_CLICK -> {
                if (ContextCompat.checkSelfPermission(
                        this,
                        permission
                    ) == PermissionChecker.PERMISSION_GRANTED
                ) {
                    startDownloading()
                    return
                }
                ActivityCompat.requestPermissions(this, arrayOf(permission), 0)
            }
            PhotoDetailEvent.LIKE_CLICK -> {
                viewmodel.likeThePhoto(id = photo?.id ?: "")
            }
        }
    }

    @Composable
    private fun renderPhotoDetailScreen(
        photoDetailScreen: ScreenStatus.PhotoDetailScreen,
        shareClick: () -> Unit,
        downloadClick: () -> Unit
    ) {

        val photoInfo = photoDetailScreen.photoInfo

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                painter = rememberCoilPainter(
                    request = photoInfo.urls.regular
                ),
                contentDescription = photoInfo.id,
                contentScale = ContentScale.Crop
            )

            //just for vertical gradiant for text to be visible
            addVerticalGradient()

            addMainImageAndContent(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = thirtyTwodp, bottom = hundredDp, end = thirtyTwodp),
                photoInfo = photoInfo
            )

            addActionableButtons(
                shareButtonModifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(thirtyTwodp),
                downloadButtonModifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(thirtyTwodp),
                shareClick = shareClick,
                downloadClick = downloadClick
            )
        }
    }

    @Composable
    private fun addMainImageAndContent(modifier: Modifier, photoInfo: PhotoDetailInfo) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier
        ) {
            Text(
                text = photoInfo.user?.name ?: "",
                style = MaterialTheme.typography.h3.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Start,
                color = Color.White
            )
            Text(
                text = stringResource(
                    id = R.string.published_on,
                    Utils.parseDate(photoInfo.createdAt)
                ),
                modifier = Modifier.padding(start = eightDp),
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Start,
                color = Color.White
            )
        }
    }

    @Composable
    private fun addActionableButtons(
        shareButtonModifier: Modifier,
        downloadButtonModifier: Modifier,
        shareClick: () -> Unit,
        downloadClick: () -> Unit
    ) {
        Button(
            onClick = downloadClick,
            modifier = downloadButtonModifier,
            shape = RoundedCornerShape(ShapeConstants.ButtonRoundPercentage)
        ) {
            Image(
                painter = painterResource(id = R.drawable.downloads),
                contentDescription = stringResource(id = R.string.downloads)
            )
        }

        IconButton(
            onClick = shareClick,
            modifier = shareButtonModifier
        ) {
            Icon(
                painter = painterResource(id = R.drawable.share),
                contentDescription = stringResource(id = R.string.share_photo)
            )
        }
    }

    @Composable
    private fun renderErrorScreen(tryAgain: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(twentyFourDp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SplashBrandLayout(
                imageResourceId = R.drawable.broken_heart,
                content = stringResource(id = R.string.photo_detail_error),
                modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically),
                colorFilter = if (isSystemInDarkTheme())
                    ColorFilter.tint(color = Color.White) else null
            )
            Button(
                onClick = tryAgain,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(twentyFourDp)
            ) {
                Text(text = stringResource(id = R.string.try_again))
            }
        }
    }


    private fun shareImage() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/html"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, photo?.urls?.regular)
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_photo)))
    }

    override fun onDestroy() {
        super.onDestroy()
        photoDetailScreenComponent = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
            startDownloading()
            return
        }
        Toast.makeText(this, getString(R.string.error_permission), Toast.LENGTH_LONG).show()
    }

    private fun startDownloading() {
        Toast.makeText(this, getString(R.string.downloading), Toast.LENGTH_SHORT).show()
        RxDownloader.getInstance(this)
            .download(photo?.urls?.full, photo?.id, Utils.photomimetype)
            .subscribe({ path ->
                Toast.makeText(
                    this,
                    getString(R.string.success_downloading, path),
                    Toast.LENGTH_SHORT
                ).show()
            }, {
                Toast.makeText(this, getString(R.string.error_downloading), Toast.LENGTH_LONG)
                    .show()
            })
    }


    @Preview
    @Composable
    fun previewErrorState() {
        renderErrorScreen({})
    }

    @Preview
    @Composable
    fun previewPhotoDetailScreen() {
        renderPhotoDetailScreen(
            photoDetailScreen = ScreenStatus.PhotoDetailScreen(
                photoInfo = PhotoDetailInfo(
                    urls = Urls(
                        full = "",
                        regular = ""
                    ),
                    user = User(
                        name = "lucas Farve"
                    ),
                    createdAt = "2020-07-01T18:31:27-04:00"
                )
            ),
            {}, {}
        )
    }
}
