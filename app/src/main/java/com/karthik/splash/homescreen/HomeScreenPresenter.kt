package com.karthik.splash.homescreen

import com.karthik.splash.models.oauth.OAuthBody
import com.karthik.splash.models.oauth.UserAuth
import com.karthik.splash.R
import com.karthik.splash.storage.Cache
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver

class HomeScreenPresenter(private val view: HomeScreenContract.View,
                          private val cache: Cache,
                          private val homeScreenNetworkLayer: HomeScreenNetworkLayer):HomeScreenContract.Presenter {

    private val disposable = CompositeDisposable()


    override fun onNavigationItemSelected(id: Int) {
        if(view.selectedItem==id)
            return

        when(id){
            R.id.navigation_home-> view.inflateHome()
            R.id.navigation_likes-> view.inflateLikes()
            R.id.navigation_settings-> view.inflateSettings()
        }
    }

    override fun getUserDetail(code: String?) {
        disposable.add(homeScreenNetworkLayer.postOAuth(OAuthBody(code)).subscribeWith(object:DisposableSingleObserver<UserAuth>(){
            override fun onSuccess(userAuth: UserAuth) {
                cache.setUserLoggedIn()
                cache.setAuthCode(userAuth.accessToken)
                view.inflateLikes()
            }

            override fun onError(e: Throwable) {
                view.displayUnableToLogin()
                view.inflateLikes()
            }
        }))
    }

    override fun clearResource() {
        if(!disposable.isDisposed){
            disposable.dispose()
        }
    }
}