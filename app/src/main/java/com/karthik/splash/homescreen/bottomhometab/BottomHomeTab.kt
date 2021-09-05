package com.karthik.splash.homescreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.karthik.splash.R
import com.karthik.splash.homescreen.bottomhometab.BottomHomeTabType
import com.karthik.splash.homescreen.bottomhometab.HomeViewModel
import com.karthik.splash.ui.SplashTheme
import androidx.compose.runtime.State
import androidx.compose.ui.tooling.preview.Preview
import com.karthik.splash.homescreen.bottomhometab.tab.TabViewModel
import com.karthik.splash.homescreen.bottomhometab.tab.tab
import com.karthik.splash.ui.Dimensions

@ExperimentalMaterialApi
@Composable
fun BottomHomeTab(homeviewmodel: HomeViewModel, tabviewmodel: TabViewModel) {
    SplashTheme {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            homeviewmodel.initalSelectedCategory(
                BottomHomeTabType.New(title = stringResource(id = R.string.frag_title_1))
            )
            val selectedCategory = homeviewmodel.selectedCategory.observeAsState()
            renderCategory(homeviewmodel, tabviewmodel, selectedCategory)
        }
    }
}
@ExperimentalMaterialApi
@Composable
fun renderCategory(
    viewmodel: HomeViewModel,
    tabViewModel: TabViewModel,
    selectedCategory: State<BottomHomeTabType?>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        renderTabHeader(selectedCategory.value, getListOfCategories()) { selectedTab ->
            viewmodel.onCategorySelected(category = selectedTab)
        }
        renderTabs(selectedCategory, tabViewModel)
    }
}

@ExperimentalMaterialApi
@Composable
private fun renderTabs(selectedCategory: State<BottomHomeTabType?>, tabViewModel: TabViewModel) {
    when (selectedCategory.value) {
        is BottomHomeTabType.New -> {
            tab(
                mode = selectedCategory.value as BottomHomeTabType.New,
                viewModel = tabViewModel
            )
        }
        is BottomHomeTabType.Trending -> {
            tab(
                mode = selectedCategory.value as BottomHomeTabType.Trending,
                viewModel = tabViewModel
            )
        }
        is BottomHomeTabType.Featured -> {
            tab(
                mode = selectedCategory.value as BottomHomeTabType.Featured,
                viewModel = tabViewModel
            )
        }
    }
}

@Composable
private fun renderTabHeader(
    selectedCategory: BottomHomeTabType?,
    listOfCategories: List<BottomHomeTabType>,
    onClick: (BottomHomeTabType) -> Unit
) {
    val selectedIndex =
        if (listOfCategories.indexOf(selectedCategory) >= 0) listOfCategories.indexOf(
            selectedCategory
        ) else 0

    TabRow(
        selectedTabIndex = selectedIndex,
        modifier = Modifier.wrapContentHeight()
    ) {
        listOfCategories.forEachIndexed { index, bottomTab ->
            Tab(
                selected = index == selectedIndex,
                onClick = { onClick(bottomTab) }) {
                Text(
                    modifier = Modifier.padding(Dimensions.sixteenDp),
                    text = bottomTab.title,
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }
}

@Composable
private fun getListOfCategories() = listOf(
    BottomHomeTabType.New(title = stringResource(id = R.string.frag_title_1)),
    BottomHomeTabType.Trending(title = stringResource(id = R.string.frag_title_2)),
    BottomHomeTabType.Featured(title = stringResource(id = R.string.frag_title_3))
)

@Preview
@Composable
fun previewTabRow(){
    renderTabHeader(
        selectedCategory = BottomHomeTabType.New(
            title = stringResource(id = R.string.frag_title_1)
        ),
        listOfCategories = getListOfCategories(),
        onClick = {})
}
