package com.karthik.splash.photodetailscreen

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
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
import com.karthik.splash.ui.Dimensions
import com.karthik.splash.ui.Dimensions.Companion.sixteenDp
import com.karthik.splash.ui.Dimensions.Companion.twentyFourDp
import com.karthik.splash.ui.Dimensions.Companion.twoHundredDp
import com.karthik.splash.ui.ProgressIndicator
import com.karthik.splash.ui.SplashBrandLayout
import com.karthik.splash.ui.SplashTheme
import javax.inject.Inject

class PhotoDetailScreen : AppCompatActivity() {

    private var photoDetailScreenComponent: PhotoDetailScreenComponent? = null
    private val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
    private val imageLoadDuration = 0

    @Inject
    lateinit var viewmodelfactory: PhotoDetailScreenViewModelFactory
    private var photo: Photos? = null
    private lateinit var viewmodel: PhotoDetailScreenViewModel

    private val bottomIcons = listOf(
        BottomIcons.Like,
        BottomIcons.Share,
        BottomIcons.Downloads
    )

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
                renderUI(screenStatus)
            }
        }
    }

    @Composable
    private fun renderUI(screenStatus: State<ScreenStatus?>) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
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
                    renderPhotoDetailScreen(screenStatus.value as ScreenStatus.PhotoDetailScreen)
                }
            }
        }
    }

    @Composable
    private fun renderPhotoDetailScreen(photoDetailScreen: ScreenStatus.PhotoDetailScreen) {

        val photoInfo = photoDetailScreen.photoInfo

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f),
                painter = rememberCoilPainter(
                    request = photoInfo.urls.regular
                ),
                contentDescription = photoInfo.id,
                contentScale = ContentScale.FillHeight
            )
            Card(
                modifier = Modifier
                    .padding(start = sixteenDp, end = sixteenDp, bottom = 100.dp)
                    .wrapContentHeight()
                    .align(Alignment.BottomCenter),
                elevation = Dimensions.fourDp
            ) {
                renderBottomCardLayout(photoInfo)
            }
        }
    }

    @Composable
    private fun renderBottomCardLayout(photoInfo: PhotoDetailInfo) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(twoHundredDp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(sixteenDp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = photoInfo.user?.name ?: "",
                    modifier = Modifier.weight(0.5f),
                    style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(
                        id = R.string.published_on,
                        Utils.parseDate(photo?.createdTime)
                    ),
                    modifier = Modifier.weight(0.5f),
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = "Tags",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = sixteenDp),
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )
            Text(
                text = "#extra#qwerty#sllslslsl#qazxc#plmn",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(sixteenDp),
                style = MaterialTheme.typography.subtitle2.copy(
                    color = MaterialTheme.colors.primary
                ),
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(sixteenDp)
            ) {
                bottomIcons.forEach { icon ->
                    val modifier = Modifier.weight(0.33f)
                    renderBottomIcons(id = icon.id, modifier = modifier)
                }
            }
        }
    }

    @Composable
    private fun renderBottomIcons(id: Int, modifier: Modifier) {
        Icon(
            modifier = modifier,
            painter = painterResource(id = id),
            contentDescription = stringResource(
                id = R.string.photo_detail
            )
        )
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
            )
        )
    }
}
