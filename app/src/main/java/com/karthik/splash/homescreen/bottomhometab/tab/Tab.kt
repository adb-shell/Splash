package com.karthik.splash.homescreen.bottomhometab.tab

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.karthik.splash.R
import com.karthik.splash.homescreen.bottomhometab.BottomHomeTab
import com.karthik.splash.ui.Dimensions
import com.karthik.splash.ui.FeedRow
import com.karthik.splash.ui.ProgressIndicator

@Composable
fun tab(mode: BottomHomeTab, viewModel: TabViewModel) {
    val photosStream = viewModel.getPhotosStream(type = mode)
    val photos = photosStream.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        items(photos) { photo ->
            FeedRow(photo = photo)
        }

        /**
         * Applying the appropriate states to the UI
         */
        photos.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        ProgressIndicator()
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        paginationProgressIndicator()
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    item {
                        showFullScreenError()
                    }
                }
                loadState.append is LoadState.Error -> {
                    //TODO: show pagination screen error
                }
            }
        }
    }
}

@Composable
fun showFullScreenError() {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(id = R.string.error_connecting))
    }
}

@Composable
fun paginationProgressIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimensions.hundredDp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun previewErrorScreen() {
    showFullScreenError()
}

@Preview
@Composable
fun previewPaginationProgress() {
    paginationProgressIndicator()
}