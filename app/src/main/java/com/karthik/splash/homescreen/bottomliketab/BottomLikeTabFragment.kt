package com.karthik.splash.homescreen.bottomliketab

import android.net.Uri
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karthik.splash.models.PhotosLists.Photos
import com.karthik.splash.R
import com.karthik.splash.homescreen.bottomtab.BottomTabAdapter
import com.karthik.splash.root.SplashApp
import kotlinx.android.synthetic.main.fragment_bottom_tab_like.*
import kotlinx.android.synthetic.main.login_view.*
import javax.inject.Inject

class BottomLikeTabFragment: Fragment(),BottomLikeTabContract.view {

    private var bottomLikeTabComponent:BottomLikeTabComponent?=null

    @Inject
    lateinit var presenter:BottomLikeTabContract.presenter

    companion object{
        fun getInstance():BottomLikeTabFragment = BottomLikeTabFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootview = inflater.inflate(R.layout.fragment_bottom_tab_like,container,false)
        bottomLikeTabComponent = (activity!!.application as SplashApp).getComponent()
                .plus(BottomLikeTabModule(this))
        bottomLikeTabComponent?.inject(this)
        return rootview
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.decideScreenType()
        login.setOnClickListener {
            presenter.login()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.clearResources()
        bottomLikeTabComponent = null
    }

    override fun showLoginScreen() {
        loginwrapper.visibility = View.VISIBLE
        likesList.visibility = View.GONE
        emptywrapper.visibility = View.GONE
        toolbar.title = getString(R.string.login)
    }

    override fun showLikesList(likedPhotos:ArrayList<Photos>) {
        loginwrapper.visibility = View.GONE
        likesList.visibility = View.VISIBLE
        emptywrapper.visibility = View.GONE
        toolbar.title = getString(R.string.title_likes)
        likesList.layoutManager = LinearLayoutManager(context)
        likesList.adapter = BottomTabAdapter(likedPhotos,null)
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun showEmptyLikedScreen() {
        loginwrapper.visibility = View.GONE
        likesList.visibility = View.GONE
        emptywrapper.visibility = View.VISIBLE
    }

    override fun openLoginOauthUrl(oauthurl: String) {
        if(!isAdded)
            return
        val uri = Uri.parse(oauthurl)
        val intentBuilder = CustomTabsIntent.Builder()
        intentBuilder.setToolbarColor(ContextCompat.getColor(context!!,
                R.color.icons))
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(context!!,
                R.color.icons))
        val customTabsIntent = intentBuilder.build()
        customTabsIntent.launchUrl(context!!,uri)
    }
}