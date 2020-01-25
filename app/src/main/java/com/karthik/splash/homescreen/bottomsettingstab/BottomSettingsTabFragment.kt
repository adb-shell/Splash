package com.karthik.splash.homescreen.bottomsettingstab

import android.Manifest
import android.app.DownloadManager
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.karthik.splash.R
import com.karthik.splash.R.layout.fragment_bottom_tab_settings
import com.karthik.splash.aboutscreen.AboutScreen
import com.karthik.splash.homescreen.bottomsettingstab.di.BottomSettingsTabComponent
import com.karthik.splash.homescreen.bottomsettingstab.di.BottomSettingsTabModule
import com.karthik.splash.root.SplashApp
import kotlinx.android.synthetic.main.fragment_bottom_tab_settings.*
import javax.inject.Inject

class BottomSettingsTabFragment: Fragment(),View.OnClickListener {

    private var bottomSettingsTabComponent: BottomSettingsTabComponent?=null
    private val permission = Manifest.permission.READ_EXTERNAL_STORAGE

    @Inject
    lateinit var viewModelFactory: BottomSettingsViewModelFactory
    private lateinit var viewModel: BottomSettingsViewModel

    companion object{
        fun getInstance() = BottomSettingsTabFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomSettingsTabComponent = (activity!!.application as SplashApp).getComponent()
                .plus(BottomSettingsTabModule())
        bottomSettingsTabComponent?.inject(this)
        viewModel = ViewModelProvider(this,viewModelFactory).get(BottomSettingsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(fragment_bottom_tab_settings,container,false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        logout.setOnClickListener(this)
        about.setOnClickListener(this)
        downloads.setOnClickListener(this)
        viewModel.isuserloggedin.observe(viewLifecycleOwner, Observer<Boolean> {isloggedin->
            if(isloggedin && !viewModel.getUserName().isNullOrEmpty()){
                showLoggedInView(viewModel.getUserName()!!)
            }else{
                showNonLoggedInView()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomSettingsTabComponent = null
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(grantResults.isNotEmpty() && grantResults[0] == PermissionChecker.PERMISSION_GRANTED)
            openFolder()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.logout->viewModel.logoutUser()
            R.id.about-> context?.startActivity(Intent(context, AboutScreen::class.java))
            R.id.downloads->{
                if(context==null)
                    return

                if(ContextCompat.checkSelfPermission(context!!, permission)
                        == PermissionChecker.PERMISSION_GRANTED){
                    openFolder()
                }else{
                    requestPermissions(arrayOf(permission),2)
                }
            }
        }
    }

    private fun showLoggedInView(name: String) {
        logout.visibility = View.VISIBLE
        username.text = getString(R.string.logged_in_as,name)
    }


    private fun showNonLoggedInView() {
        profileimage.setImageResource(R.drawable.logout)
        username.text = getString(R.string.login)
        logout.visibility = View.GONE
    }

    private fun openFolder() = startActivity(Intent(DownloadManager.ACTION_VIEW_DOWNLOADS))
}