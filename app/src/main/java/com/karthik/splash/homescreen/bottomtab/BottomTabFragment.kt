package com.karthik.splash.homescreen.bottomtab

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.karthik.splash.models.PhotosLists.Photos
import com.karthik.splash.R
import com.karthik.splash.homescreen.bottomtab.di.BottomTabComponent
import com.karthik.splash.homescreen.bottomtab.di.BottomTabModule
import com.karthik.splash.root.SplashApp
import kotlinx.android.synthetic.main.fragment_new.*
import javax.inject.Inject

class BottomTabFragment: Fragment(){

    private var bottomTabComponent: BottomTabComponent?=null
    private lateinit var feedsAdapter:BottomTabAdapter
    private var iscacheavailable:Boolean?=false
    private lateinit var bottomTabViewModel:BottomTabViewModel

    @Inject
    lateinit var bottomtabviewmodelfactory: BottomTabViewModel.BottomTabViewModelFactory



    companion object{
        const val Mode = "Mode"
        const val Cache = "Cache"
        fun getInstance(mode:BottomTabTypes,isfromcache:Boolean):BottomTabFragment{
            val args = Bundle()
            args.putParcelable(Mode,mode)
            args.putBoolean(Cache,isfromcache)
            val fragment = BottomTabFragment()
            fragment.retainInstance = true
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iscacheavailable = arguments?.getBoolean(Cache)
        bottomTabComponent = (activity!!.application as SplashApp)
                .getComponent()
                .plus(BottomTabModule(iscacheavailable,context))
        bottomTabComponent?.inject(this)

        bottomTabViewModel = activity?.run {
            ViewModelProvider(this,bottomtabviewmodelfactory).get(BottomTabViewModel::class.java)
        }?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_new, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mode = arguments?.getParcelable<BottomTabTypes>(Mode)
        bottomTabViewModel.getFeeds(mode)
        observeTheAppropriateLiveData(mode)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomTabComponent = null
    }

    private fun showPhotosList(photos: ArrayList<Photos>) {

        /*if (!presenter.isPaginatedItems()) {*/
            feedslist.visibility = View.VISIBLE
            feedslist.layoutManager = LinearLayoutManager(context)
            feedsAdapter = BottomTabAdapter(photos, null)
            feedslist.adapter = feedsAdapter
            //return
       /* }*/
        //feedsAdapter.addPaginatedItems(photos)
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

    private fun observeTheAppropriateLiveData(mode: BottomTabTypes?) {
        when (mode) {
            is BottomTabTypes.New -> {
                bottomTabViewModel.newfeeds.observe(viewLifecycleOwner, Observer<PhotoFeedState> {
                    processResult(it)
                })
            }
            is BottomTabTypes.Featured -> {
                bottomTabViewModel.featuredfeeds.observe(viewLifecycleOwner, Observer<PhotoFeedState> {
                    processResult(it)
                })
            }
            is BottomTabTypes.Trending -> {
                bottomTabViewModel.trendingfeeds.observe(viewLifecycleOwner, Observer<PhotoFeedState> {
                    processResult(it)
                })
            }
        }
    }

    private fun processResult(photoFeedState: PhotoFeedState){
        hideProgressBar()
        when(photoFeedState){
            is PhotoFeedState.FeedState->showPhotosList(photoFeedState.photos)
            is PhotoFeedState.FeedError->showEmptyScreen()
            is PhotoFeedState.FeedNoInternet->showNoInternetScreen()
        }
    }
}