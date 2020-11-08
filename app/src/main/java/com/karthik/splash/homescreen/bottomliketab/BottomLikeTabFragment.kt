package com.karthik.splash.homescreen.bottomliketab

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.karthik.splash.R
import com.karthik.splash.homescreen.bottomliketab.di.BottomLikeTabComponent
import com.karthik.splash.homescreen.bottomliketab.di.BottomLikeTabModule
import com.karthik.splash.homescreen.bottomliketab.network.LikeFeedNetworkState
import com.karthik.splash.homescreen.bottomtab.BottomTabAdapter
import com.karthik.splash.models.UserStatus
import com.karthik.splash.root.SplashApp
import kotlinx.android.synthetic.main.fragment_bottom_tab_like.*
import kotlinx.android.synthetic.main.login_view.*
import javax.inject.Inject

class BottomLikeTabFragment : Fragment() {

    private var bottomLikeTabComponent: BottomLikeTabComponent? = null

    @Inject
    lateinit var viewModelFactory: BottomLikeViewModelFactory

    private lateinit var viewModel: BottomLikeViewModel

    companion object {
        fun getInstance(): BottomLikeTabFragment =
                BottomLikeTabFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomLikeTabComponent = (activity!!.application as SplashApp).getComponent()
                .plus(BottomLikeTabModule())
        bottomLikeTabComponent?.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)
                .get(BottomLikeViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) =
            inflater.inflate(R.layout.fragment_bottom_tab_like, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeUserLoggedInStatus()
        observeNetworkState()
        observeLikeFeeds()

        login.setOnClickListener {
            openLoginOauthUrl(viewModel.loginurl)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomLikeTabComponent = null
    }

    private fun showLoginScreen() {
        loginwrapper.visibility = View.VISIBLE
        likesList.visibility = View.GONE
        emptywrapper.visibility = View.GONE
        toolbar.title = getString(R.string.login)
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
    }

    private fun showEmptyLikedScreen() {
        loginwrapper.visibility = View.GONE
        likesList.visibility = View.GONE
        emptywrapper.visibility = View.VISIBLE
    }

    private fun openLoginOauthUrl(oauthurl: String) {
        if (!isAdded)
            return
        val uri = Uri.parse(oauthurl)
        val intentBuilder = CustomTabsIntent.Builder()
        intentBuilder.setToolbarColor(ContextCompat.getColor(context!!,
                R.color.icons))
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(context!!,
                R.color.icons))
        val customTabsIntent = intentBuilder.build()
        customTabsIntent.launchUrl(requireContext(), uri)
    }

    private fun observeLikeFeeds() {
        viewModel.likefeeds.observe(viewLifecycleOwner, { likedphotos ->
            if (likedphotos != null && likedphotos.isNotEmpty()) {
                loginwrapper.visibility = View.GONE
                likesList.visibility = View.VISIBLE
                emptywrapper.visibility = View.GONE
                toolbar.title = getString(R.string.title_likes)
                likesList.layoutManager = LinearLayoutManager(context)
                likesList.adapter = BottomTabAdapter(ArrayList(likedphotos))
            } else {
                showEmptyLikedScreen()
            }
        })
    }

    private fun observeNetworkState() {
        viewModel.networkstate.observe(viewLifecycleOwner, { networkState ->
            when (networkState) {
                is LikeFeedNetworkState.FeedNetworkLoading     -> {
                    showProgress()
                }
                is LikeFeedNetworkState.FeedNetworkLoadSuccess -> {
                    hideProgress()
                }
                is LikeFeedNetworkState.FeedNetworkError       -> {
                    hideProgress()
                    Toast.makeText(requireContext(),
                            getString(R.string.error_connecting),
                            Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun observeUserLoggedInStatus() {
        viewModel.isuserloggedin.observe(viewLifecycleOwner, { loggedInStatus ->
            when (loggedInStatus) {
                is UserStatus.UserLoggedIn    -> {
                    viewModel.getLikedPhotos()
                }
                is UserStatus.UserNotLoggedIn -> {
                    showLoginScreen()
                }
            }
        })
    }
}