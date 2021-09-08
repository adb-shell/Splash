package com.karthik.splash.homescreen.bottomhometab

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.karthik.splash.BuildConfig
import com.karthik.splash.R
import com.karthik.splash.aboutscreen.AboutScreen
import com.karthik.splash.homescreen.HomeClickEvents
import com.karthik.splash.misc.Utils


private const val PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE
private const val USERSCOPE = "public+read_user+read_photos+write_likes"
private const val LOGINURL = "${BuildConfig.SPLASH_LOGIN_URL}?client_id=${BuildConfig.SPLASH_KEY}" +
        "&redirect_uri=${BuildConfig.SPLASH_LOGIN_CALLBACK}&response_type=code&scope=$USERSCOPE"

internal fun handleHomeClicks(context: Context, clickEvent: HomeClickEvents) {
    when (clickEvent) {
        HomeClickEvents.NotLoggedIn -> {
            openLoginOauthUrl(context = context, oauthurl = LOGINURL)
        }
        is HomeClickEvents.PhotoClick -> {
            Utils.navigateToPhotoDetailScreen(
                photo = clickEvent.photos,
                context = context
            )
        }
        HomeClickEvents.AboutClick -> {
            context.startActivity(Intent(context, AboutScreen::class.java))
        }
        HomeClickEvents.DownloadsClick -> {
            if (ContextCompat.checkSelfPermission(
                    context,
                    PERMISSION
                )
                == PermissionChecker.PERMISSION_GRANTED
            ) {
                context.startActivity(Intent(DownloadManager.ACTION_VIEW_DOWNLOADS))
            } else {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(PERMISSION),
                    2
                )
            }
        }
        else -> throw IllegalStateException("Unable to process the click event")
    }
}

fun openLoginOauthUrl(context: Context, oauthurl: String) {
    val uri = Uri.parse(oauthurl)
    val intentBuilder = CustomTabsIntent.Builder()
    intentBuilder.setToolbarColor(
        ContextCompat.getColor(
            context,
            R.color.icons
        )
    )
    intentBuilder.setSecondaryToolbarColor(
        ContextCompat.getColor(
            context,
            R.color.icons
        )
    )
    val customTabsIntent = intentBuilder.build()
    customTabsIntent.launchUrl(context, uri)
}