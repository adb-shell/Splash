package com.karthik.splash.homescreen.bottomtab

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karthik.splash.Models.PhotosLists.Photos
import com.karthik.splash.R
import com.karthik.splash.root.SplashApp
import kotlinx.android.synthetic.main.fragment_new.*
import javax.inject.Inject

class BottomTabFragment:Fragment(), BottomTabContract.View, PaginatedView {

    private var bottomTabComponent: BottomTabComponent?=null
    private lateinit var feedsAdapter:BottomTabAdapter

    @Inject
    lateinit var presenter:BottomTabContract.Presenter

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_new, container, false)
        bottomTabComponent = (activity!!.application as SplashApp)
                .getComponent()
                .plus(BottomTabModule(this,context))
        bottomTabComponent?.inject(this)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mode = arguments?.getParcelable<BottomTabTypes>(Mode)
        val cache = arguments?.getBoolean(Cache)
        presenter.getFeeds(mode,cache,1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.clearResources()
        bottomTabComponent = null
    }

    override fun hideProgressBar() {
        progress.visibility = View.GONE
    }

    override fun showPhotosList(photos: ArrayList<Photos>) {
        if (!presenter.isPaginatedItems()) {
            feedslist.visibility = View.VISIBLE
            feedslist.layoutManager = LinearLayoutManager(context)
            feedsAdapter = BottomTabAdapter(photos, this)
            feedslist.adapter = feedsAdapter
            return
        }
        feedsAdapter.addPaginatedItems(photos)
    }

    override fun showEmptyScreen() {
        feedslist.visibility = View.GONE
        nointernettext.visibility = View.VISIBLE
        nointernettext.text = getString(R.string.error_connecting)
    }

    override fun showNoInternetScreen() {
        feedslist.visibility = View.GONE
        nointernetimage.visibility = View.VISIBLE
        nointernettext.visibility = View.VISIBLE
    }

    override fun isFeedListVisible(): Boolean = feedslist.visibility == View.VISIBLE

    override fun getPage(pageNo: Int) {
        val mode = arguments?.getParcelable<BottomTabTypes>(Mode)
        presenter.getPaginatedFeeds(mode, pageNo)
    }

    override fun getMaxPageLimit(): Int = presenter.getPageMaxLimit()
}