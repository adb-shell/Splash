package com.karthik.splash.homescreen.bottomhometab


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.karthik.splash.R
import com.karthik.splash.homescreen.HomeScreen
import com.karthik.splash.homescreen.bottomhometab.tab.TabViewModel
import com.karthik.splash.homescreen.bottomhometab.tab.TabViewModelFactory
import com.karthik.splash.homescreen.bottomhometab.tab.di.TabComponent
import com.karthik.splash.homescreen.bottomhometab.tab.di.TabModule
import com.karthik.splash.homescreen.bottomhometab.tab.tab
import com.karthik.splash.root.SplashApp
import com.karthik.splash.ui.Dimensions
import com.karthik.splash.ui.SplashTheme
import javax.inject.Inject

class BottomHomeTabFragment : Fragment() {
    private lateinit var viewmodel: BottomHomeTabViewModel
    private lateinit var tabViewModel: TabViewModel
    private var bottomtabcomponent: TabComponent? = null

    @Inject lateinit var bottomtabviewmodelfactory: TabViewModelFactory

    companion object {
        fun getInstance(iscached: Boolean): BottomHomeTabFragment {
            val args = Bundle()
            args.putBoolean(HomeScreen.IS_FROM_CACHE, iscached)
            val fragment = BottomHomeTabFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel = ViewModelProvider(this).get(BottomHomeTabViewModel::class.java)
        bottomtabcomponent = (activity?.application as SplashApp)
            .getComponent()
            .plus(TabModule(isCacheAvailable = arguments?.getBoolean(HomeScreen.IS_FROM_CACHE)))
        bottomtabcomponent?.inject(this)
        tabViewModel = ViewModelProvider(this, bottomtabviewmodelfactory)
            .get(TabViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomtabcomponent = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SplashTheme {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        viewmodel.initalSelectedCategory(
                            BottomHomeTab.New(title = getString(R.string.frag_title_1))
                        )
                        val selectedCategory = viewmodel.selectedCategory.observeAsState()
                        renderUI(selectedCategory)
                    }
                }
            }
        }
    }

    @Composable
    private fun renderUI(selectedCategory: State<BottomHomeTab?>) {

        //val isCached = arguments?.getBoolean(HomeScreen.IS_FROM_CACHE) ?: false

        Column(
            modifier = Modifier
            .fillMaxWidth().fillMaxHeight()
        ) {
            renderTabHeader(selectedCategory.value, getListOfCategories()) { selectedTab ->
                viewmodel.onCategorySelected(category = selectedTab)
            }
            Box(
                modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                renderTabs(selectedCategory)
            }
        }
    }

    @Composable
    private fun renderTabs(selectedCategory: State<BottomHomeTab?>) {
        when (selectedCategory.value) {
            is BottomHomeTab.New -> {
                tab(
                    mode = selectedCategory.value as BottomHomeTab.New,
                    viewModel = tabViewModel
                )
            }
            is BottomHomeTab.Trending -> {
                tab(
                    mode = selectedCategory.value as BottomHomeTab.Trending,
                    viewModel = tabViewModel
                )
            }
            is BottomHomeTab.Featured -> {
                tab(
                    mode = selectedCategory.value as BottomHomeTab.Featured,
                    viewModel = tabViewModel
                )
            }
        }
    }

    @Composable
    private fun renderTabHeader(
        selectedCategory: BottomHomeTab?,
        listOfCategories: List<BottomHomeTab>,
        onClick: (BottomHomeTab) -> Unit
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
        BottomHomeTab.New(title = stringResource(id = R.string.frag_title_1)),
        BottomHomeTab.Trending(title = stringResource(id = R.string.frag_title_2)),
        BottomHomeTab.Featured(title = stringResource(id = R.string.frag_title_3))
    )


    @Preview
    @Composable
    fun previewTabRow(){
        renderTabHeader(
            selectedCategory = BottomHomeTab.New(
                title = stringResource(id = R.string.frag_title_1)
            ),
            listOfCategories = getListOfCategories(),
            onClick = {})
    }
}