package com.karthik.splash.homescreen.bottomsettingstab

import android.Manifest
import android.app.DownloadManager
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karthik.splash.R
import com.karthik.splash.R.layout.fragment_bottom_tab_settings
import com.karthik.splash.aboutscreen.AboutScreen
import com.karthik.splash.root.SplashApp
import kotlinx.android.synthetic.main.fragment_bottom_tab_settings.*
import javax.inject.Inject

class BottomSettingsTabFragment:Fragment(), BottomSettingsTabContract.view,View.OnClickListener {

    private var bottomSettingsTabComponent:BottomSettingsTabComponent?=null
    private val permission = Manifest.permission.READ_EXTERNAL_STORAGE

    @Inject
    lateinit var presenter:BottomSettingsTabContract.presenter

    companion object{
        fun getInstance() = BottomSettingsTabFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootview = inflater.inflate(fragment_bottom_tab_settings,container,false)
        bottomSettingsTabComponent = (activity!!.application as SplashApp).getComponent()
                .plus(BottomSettingsTabModule(this))
        bottomSettingsTabComponent?.inject(this)
        return rootview
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        logout.setOnClickListener(this)
        about.setOnClickListener(this)
        downloads.setOnClickListener(this)
        presenter.decideScreen()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomSettingsTabComponent = null
    }

    override fun showLoggedInView(name: String) {
        logout.visibility = View.VISIBLE
        username.text = getString(R.string.logged_in_as,name)
    }


    override fun showNonLoggedInView() {
        profileimage.setImageResource(R.drawable.logout)
        username.text = getString(R.string.login)
        logout.visibility = View.GONE
    }

    override fun openFolder() = startActivity(Intent(DownloadManager.ACTION_VIEW_DOWNLOADS))

    override fun isReadPermissionGranted() =
            ContextCompat.checkSelfPermission(context!!, permission) == PermissionChecker.PERMISSION_GRANTED;

    override fun askReadPermission() {
        ContextCompat.checkSelfPermission(context!!, permission) == PermissionChecker.PERMISSION_GRANTED;
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(grantResults.isNotEmpty() && grantResults[0] == PermissionChecker.PERMISSION_GRANTED)
            presenter.showDownloadedImages()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.logout->presenter.logOutUser()
            R.id.about-> context?.startActivity(Intent(context, AboutScreen::class.java))
            R.id.downloads->presenter.showDownloadedImages()
        }
    }
}