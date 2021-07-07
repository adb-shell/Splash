package com.karthik.splash.aboutscreen

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.karthik.splash.R
import com.karthik.splash.models.library.Library
import com.karthik.splash.ui.*
import java.util.*

class AboutScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashTheme {
                //TODO: animate like collasping toolbar.
                Column {
                    renderTopBar()
                    renderTheList(library = getLibraries())
                }
            }
        }
    }

    @Composable
    fun renderTopBar() {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = topBarHeight,
            color = if (isSystemInDarkTheme()) MaterialTheme.colors.surface else MaterialTheme.colors.primaryVariant,
            contentColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(ActivityMargin),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                splashBrandLayout(
                    imageResourceId = R.drawable.cold_image,
                    content = stringResource(id = R.string.app_name),
                    modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically),
                    colorFilter = ColorFilter.tint(color = Color.White)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(smallSpacerHeight)
                )
                Text(
                    text = stringResource(id = R.string.version),
                    style = MaterialTheme.typography.caption
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(largeSpacerHeight)
                )
                Text(
                    text = stringResource(id = R.string.unsplash_powered),
                    style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(smallSpacerHeight)
                )
                Text(
                    text = stringResource(id = R.string.unsplash_intro_text),
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    @Composable
    private fun renderTheList(
        library: List<Library>
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(library) { library ->
                renderRow(library = library)
            }
        }
    }

    @Composable
    private fun renderRow(library: Library) {
        Surface(
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(
                        top = ActivityMargin,
                        start = ActivityMargin,
                        end = ActivityMargin
                    ),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = library.libName, style = MaterialTheme.typography.h6)
                Text(
                    text = stringResource(id = R.string.Version) + " ${library.version}",
                    style = MaterialTheme.typography.caption
                )
                Divider(
                    modifier = Modifier.padding(
                        vertical = ActivityMargin
                    ),
                    color = getDividerColor()
                )
            }
        }
    }

    @Composable
    private fun getDividerColor() = if (isSystemInDarkTheme()) {
        Color.White.copy(alpha = 0.08f)
    } else MaterialTheme.colors.onSurface.copy(alpha = 0.08f)

    @Preview
    @Composable
    private fun previewTopbar() {
        SplashTheme {
            renderTopBar()
        }
    }


    @Composable
    @Preview(
        showBackground = true,
        backgroundColor = 0XFFFFFF
    )
    private fun previewRowItem() {
        Column {
            renderRow(library = getLibraries()[0])
        }
    }

    private fun getLibraries(): ArrayList<Library> {
        val libraries = ArrayList<Library>()
        libraries.add(Library("Android X appcompat", "1.1.0"))
        libraries.add(Library("Material", "1.1.0"))
        libraries.add(Library("Android X browser", "1.2.0"))
        libraries.add(Library("Android X recyclerView", "1.1.0"))
        libraries.add(Library("Retrofit 2", "2.6.0"))
        libraries.add(Library("Retrofit 2 converter GSON", "2.2.0"))
        libraries.add(Library("Okhttp logging interceptor", "3.4.1"))
        libraries.add(Library("Dagger", "2.3.6"))
        libraries.add(Library("Dagger compiler", "2.3.6"))
        libraries.add(Library("RxDownloader", "1.0.1"))
        libraries.add(Library("Picasso", "2.5.2"))
        libraries.add(Library("Material image loading", "1.0.2"))
        libraries.add(Library("Android X lifecycle extension", "2.2.0"))
        libraries.add(Library("Android X lifecycle common java 8", "2.2.0"))
        libraries.add(Library("Android X room runtime", "2.3.0"))
        libraries.add(Library("Android X room compiler", "2.3.0"))
        libraries.add(Library("Android X room co-routine adapter", "2.3.0"))
        libraries.add(Library("Android X paging", "2.1.1"))
        libraries.add(Library("Coroutines Android", "1.4.2"))
        libraries.add(Library("Chuck interceptor", "1.1.0"))
        libraries.add(Library("JUnit", "4.1.2"))
        libraries.add(Library("Test runner", "1.0.1"))
        libraries.add(Library("Expresso core", "3.0.1"))
        libraries.add(Library("Android X core", "2.1.0"))
        libraries.add(Library("Mockito kotlin", "3.2.0"))
        return libraries
    }
}