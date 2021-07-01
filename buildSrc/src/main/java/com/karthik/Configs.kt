package com.karthik

import com.karthik.Configs.VersionsConfig.androidXBrowserVersion
import com.karthik.Configs.VersionsConfig.androidXCoreTestVersion
import com.karthik.Configs.VersionsConfig.androidXVersion
import com.karthik.Configs.VersionsConfig.archComponentPagingVersion
import com.karthik.Configs.VersionsConfig.archComponentRoomVersion
import com.karthik.Configs.VersionsConfig.archCoreTestingVersion
import com.karthik.Configs.VersionsConfig.archLifecycleComponentVersion
import com.karthik.Configs.VersionsConfig.chuckerVersion
import com.karthik.Configs.VersionsConfig.composeActivityVersion
import com.karthik.Configs.VersionsConfig.composeVersion
import com.karthik.Configs.VersionsConfig.daggerVersion
import com.karthik.Configs.VersionsConfig.junitExtensionVersion
import com.karthik.Configs.VersionsConfig.junitVersion
import com.karthik.Configs.VersionsConfig.kotlinCoroutinesAndroidVersion
import com.karthik.Configs.VersionsConfig.kotlinCoroutinesCore
import com.karthik.Configs.VersionsConfig.materialVersion
import com.karthik.Configs.VersionsConfig.mockitoVersion
import com.karthik.Configs.VersionsConfig.picassoMaterialVersion
import com.karthik.Configs.VersionsConfig.picassoVersion
import com.karthik.Configs.VersionsConfig.retrofitConvVersion
import com.karthik.Configs.VersionsConfig.retrofitLoggerVersion
import com.karthik.Configs.VersionsConfig.retrofitVersion
import com.karthik.Configs.VersionsConfig.rxDownloaderVersion
import com.karthik.Configs.VersionsConfig.viewModelKtxVersion

class Configs {
    object VersionsConfig {
        const val junitVersion = "4.12"
        const val junitExtensionVersion = "1.1.1"
        const val daggerVersion = "2.36"
        const val retrofitVersion = "2.6.0"
        const val retrofitConvVersion = "2.2.0"
        const val retrofitLoggerVersion = "3.4.1"
        const val picassoVersion = "2.5.2"
        const val chuckerVersion = "3.1.2"
        const val picassoMaterialVersion = "1.0.2"
        const val rxDownloaderVersion = "1.0.1"
        const val archLifecycleComponentVersion = "2.2.0-rc03"
        const val archComponentRoomVersion = "2.3.0"
        const val archComponentPagingVersion = "2.1.1"
        const val viewModelKtxVersion = "2.2.0"
        const val androidXVersion = "1.1.0"
        const val androidXBrowserVersion = "1.2.0"
        const val androidXCoreTestVersion = "3.1.0"
        const val materialVersion = "1.1.0"


        const val versionCode = 1
        const val versionName = "0.4"
        const val archCoreTestingVersion = "2.1.0"
        const val mockitoVersion = "3.2.0"
        const val kotlinCoroutinesAndroidVersion = "1.4.2"
        const val kotlinCoroutinesCore = "1.5.0"

        const val composeVersion = "1.0.0-beta09"
        const val composeActivityVersion = "1.3.0-beta02"
    }

    object SplashUrlConfig {
        const val splashBaseUrl = "SPLASH_BASE_URL"
        const val splashKey = "SPLASH_KEY"
        const val splashKeySecret = "SPLASH_KEY_SECRET"
        const val splashLoginUrl = "SPLASH_LOGIN_URL"
        const val splashLoginCallback = "SPLASH_LOGIN_CALLBACK"
        const val splashCallback = "SPLASH_CALLBACK"
        const val splashTokenUrl = "SPLASH_TOKEN_URL"

        const val splashLoginUrlValue = "\"https://unsplash.com/oauth/authorize\""
        const val splashTokenUrlValue = "\"https://unsplash.com/oauth/token\""
        const val splashLoginCallbackValue = "\"splash://splash-auth-callback\""
        const val splashCallbackValue = "\"splash-auth-callback\""
        const val splashBaseUrlValue = "\"https://api.unsplash.com/\""
        const val splashKeyValue ="\"\""
        const val splashKeySecretValue ="\"\""
    }

    object AppConfig {
        const val applicationId = "com.karthik.splash"
        const val kotlinVersion = "1.5.10"
        const val androidToolsVersion = "7.0.0-beta04"

        const val compileSDKVersion = "29"
        const val minSDKVersion = 21

        const val demoApllicationSuffix = ".debug"
        const val demoApllicationVersionNameSuffix = "-debug"
        const val appstoreApplicationSuffix = ".release"
        const val appstoreApllicationVersionNameSuffix = "-release"
    }

    object DependenciesConfig {
        const val androidXAppcompat = "androidx.appcompat:appcompat:${androidXVersion}"
        const val googleMaterial = "com.google.android.material:material:${materialVersion}"
        const val androidXBrowser = "androidx.browser:browser:${androidXBrowserVersion}"
        const val recyclerView = "androidx.recyclerview:recyclerview:${androidXVersion}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${retrofitVersion}"
        const val retrofitConvertor = "com.squareup.retrofit2:converter-gson:${retrofitConvVersion}"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${retrofitLoggerVersion}"
        const val dagger = "com.google.dagger:dagger:${daggerVersion}"
        const val daggerCompiler = "com.google.dagger:dagger-compiler:${daggerVersion}"
        const val picasso = "com.squareup.picasso:picasso:${picassoVersion}"
        const val materialLoading = "com.github.florent37:materialimageloading:${picassoMaterialVersion}"
        const val rxDownloader = "com.github.esafirm:RxDownloader:${rxDownloaderVersion}"

        /**
         * coroutines
         */
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${kotlinCoroutinesCore}"
        const val coroutinesAndroid  = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${kotlinCoroutinesAndroidVersion}"
        const val viewmodelKTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${viewModelKtxVersion}"

        /**
         * Architecture components.
         */
        const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${archLifecycleComponentVersion}"
        const val lifecycleJava8 = "androidx.lifecycle:lifecycle-common-java8:${archLifecycleComponentVersion}"
        const val roomRuntime = "androidx.room:room-runtime:${archComponentRoomVersion}"
        const val roomCompiler = "androidx.room:room-compiler:${archComponentRoomVersion}"
        const val roomCoroutinesAdapter = "androidx.room:room-ktx:${archComponentRoomVersion}"
        const val paging = "androidx.paging:paging-runtime:${archComponentPagingVersion}"

        const val chuckerDebug = "com.github.ChuckerTeam.Chucker:library:${chuckerVersion}"
        const val chuckerRelease = "com.github.ChuckerTeam.Chucker:library-no-op:${chuckerVersion}"

        /**
         * Testing depedencies.
         */
        const val junit = "junit:junit:${junitVersion}"
        const val junitExtension = "androidx.test.ext:junit:${junitExtensionVersion}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${androidXCoreTestVersion}"
        const val roomTesting = "androidx.room:room-testing:${archComponentRoomVersion}"
        const val archCoreTesting = "androidx.arch.core:core-testing:${archCoreTestingVersion}"
        const val mockitoKotlin = "org.mockito.kotlin:mockito-kotlin:${mockitoVersion}"
    }

    object ComposeConfig{
        const val composeUi = "androidx.compose.ui:ui:${composeVersion}"
        const val composeFoundation = "androidx.compose.foundation:foundation:${composeVersion}"
        const val composeMaterial = "androidx.compose.material:material:${composeVersion}"
        const val composeMaterialIcons = "androidx.compose.material:material-icons-core:${composeVersion}"
        const val composeMaterialIconsExtended = "androidx.compose.material:material-icons-extended:${composeVersion}"
        const val composeLiveDataSupport = "androidx.compose.runtime:runtime-livedata:${composeVersion}"
        const val composeActivity = "androidx.activity:activity-compose:${composeActivityVersion}"
        const val composeUiTooling = "androidx.compose.ui:ui-tooling:${composeVersion}"
    }
}
