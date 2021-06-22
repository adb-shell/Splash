package com.karthik.splash.homescreen.bottomtab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.karthik.network.home.bottomtab.models.PhotoFeedNetworkState
import com.karthik.splash.R
import com.karthik.splash.homescreen.bottomtab.di.BottomTabComponent
import com.karthik.splash.homescreen.bottomtab.di.BottomTabModule
import com.karthik.splash.root.SplashApp
import kotlinx.android.synthetic.main.fragment_new.*
import javax.inject.Inject

class BottomTabFragment : Fragment() {

    private var bottomtabcomponent: BottomTabComponent? = null
    private lateinit var feedsadapter: BottomFeedAdapter
    private var iscacheavailable: Boolean? = false
    private lateinit var bottomtabviewmodel: BottomTabViewModel

    @Inject
    lateinit var bottomtabviewmodelfactory: BottomTabViewModelFactory

    companion object {
        const val Mode = "Mode"
        const val Cache = "Cache"
        fun getInstance(mode: BottomTabTypes, isfromcache: Boolean): BottomTabFragment {
            val args = Bundle()
            args.putParcelable(Mode, mode)
            args.putBoolean(Cache, isfromcache)
            val fragment = BottomTabFragment()
            fragment.retainInstance = true
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iscacheavailable = arguments?.getBoolean(Cache)
        val mode = arguments?.getParcelable<BottomTabTypes>(Mode)
        bottomtabcomponent = (activity?.application as SplashApp)
                .getComponent()
                .plus(BottomTabModule(iscacheavailable, mode))
        bottomtabcomponent?.inject(this)
        bottomtabviewmodel = ViewModelProvider(this, bottomtabviewmodelfactory)
                .get(BottomTabViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? =
            inflater.inflate(R.layout.fragment_new, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        feedsadapter = BottomFeedAdapter()
        feedslist.layoutManager = LinearLayoutManager(context)
        feedslist.adapter = feedsadapter

        /**
         * Observe the feeds.
         */
        bottomtabviewmodel.feeds.observe(viewLifecycleOwner, { photos ->
            feedsadapter.submitList(photos)
        })

        /**
         * Observe the network state.
         */
        bottomtabviewmodel.networkState.observe(viewLifecycleOwner, { state ->
            hideProgressBar()
            when (state) {
                is PhotoFeedNetworkState.FeedNetworkLoadSuccess         -> feedslist.visibility =
                        View.VISIBLE
                is PhotoFeedNetworkState.FeedNetworkError               -> showEmptyScreen()
                is PhotoFeedNetworkState.FeedNetworkNoInternet          -> showNoInternetScreen()
                is PhotoFeedNetworkState.FeedNetworkPaginationLoading   ->
                    feedsadapter.showPaginationProgress()
                is PhotoFeedNetworkState.FeedNetworkPaginationLoadSuccess,
                is PhotoFeedNetworkState.FeedNetworkPaginationLoadError ->
                    feedsadapter.hidePaginationProgress()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomtabcomponent = null
    }

    private fun showEmptyScreen() {
        feedslist.visibility = View.GONE
        nointernettext.visibility = View.VISIBLE
        nointernettext.text = getString(R.string.error_connecting)
    }

    private fun showNoInternetScreen() {
        feedslist.visibility = View.GONE
        nointernetimage.visibility = View.VISIBLE
        nointernettext.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progress.visibility = View.GONE
    }
}