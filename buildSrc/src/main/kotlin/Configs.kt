import Configs.VersionsConfig.androidXBrowserVersion
import Configs.VersionsConfig.androidXCoreTestVersion
import Configs.VersionsConfig.androidXVersion
import Configs.VersionsConfig.archComponentPagingVersion
import Configs.VersionsConfig.archComponentRoomVersion
import Configs.VersionsConfig.archCoreTestingVersion
import Configs.VersionsConfig.archLifecycleComponentVersion
import Configs.VersionsConfig.chuckerVersion
import Configs.VersionsConfig.daggerVersion
import Configs.VersionsConfig.junitExtensionVersion
import Configs.VersionsConfig.junitVersion
import Configs.VersionsConfig.kotlinCoroutinesVersion
import Configs.VersionsConfig.materialVersion
import Configs.VersionsConfig.nhaarmanMockitoVersion
import Configs.VersionsConfig.picassoMaterialVersion
import Configs.VersionsConfig.picassoVersion
import Configs.VersionsConfig.retrofitConvVersion
import Configs.VersionsConfig.retrofitLoggerVersion
import Configs.VersionsConfig.retrofitVersion
import Configs.VersionsConfig.rxAndroidVersion
import Configs.VersionsConfig.rxDownloaderVersion
import Configs.VersionsConfig.rxJavaVersion
import Configs.VersionsConfig.viewModelKtxVersion

class Configs {
    object VersionsConfig {
        const val junitVersion = "4.12"
        const val junitExtensionVersion = "1.1.1"
        const val daggerVersion = "2.10"
        const val retrofitVersion = "2.6.0"
        const val retrofitConvVersion = "2.2.0"
        const val retrofitLoggerVersion = "3.4.1"
        const val picassoVersion = "2.5.2"
        const val rxJavaVersion = "2.1.6"
        const val rxAndroidVersion = "2.0.1"
        const val chuckerVersion = "3.1.2"
        const val picassoMaterialVersion = "1.0.2"
        const val rxDownloaderVersion = "1.0.1"
        const val archLifecycleComponentVersion = "2.2.0-rc03"
        const val archComponentRoomVersion = "2.2.5"
        const val archComponentPagingVersion = "2.1.1"
        const val viewModelKtxVersion = "2.2.0"
        const val androidXVersion = "1.1.0"
        const val androidXBrowserVersion = "1.2.0"
        const val androidXCoreTestVersion = "3.1.0"
        const val materialVersion = "1.1.0"


        const val versionCode = 1
        const val versionName = "0.4"
        const val archCoreTestingVersion = "2.1.0"
        const val nhaarmanMockitoVersion = "2.2.0"
        const val kotlinCoroutinesVersion = "1.4.2"
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
        const val kotlinVersion = "1.4.0"
        const val androidToolsVersion = "4.1.0"

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
        const val coroutines  = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${kotlinCoroutinesVersion}"
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
        const val nharmanMockito = "com.nhaarman.mockitokotlin2:mockito-kotlin:${nhaarmanMockitoVersion}"
    }
}
