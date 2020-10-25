plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Configs.AppConfig.compileSDKVersion.toInt())
    defaultConfig {
        minSdkVersion(Configs.AppConfig.minSDKVersion)
        targetSdkVersion(Configs.AppConfig.compileSDKVersion)
        applicationId = Configs.AppConfig.applicationId
        versionCode = Configs.VersionsConfig.versionCode
        versionName = Configs.VersionsConfig.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        //default configs
        buildConfigField("String", Configs.SplashUrlConfig.splashBaseUrl,
                Configs.SplashUrlConfig.splashBaseUrlValue)
        buildConfigField("String", Configs.SplashUrlConfig.splashKey,
                Configs.SplashUrlConfig.splashKeyValue)
        buildConfigField("String", Configs.SplashUrlConfig.splashKeySecret,
                Configs.SplashUrlConfig.splashKeySecretValue)
        buildConfigField("String", Configs.SplashUrlConfig.splashCallback,
                Configs.SplashUrlConfig.splashCallbackValue)
        buildConfigField("String", Configs.SplashUrlConfig.splashLoginUrl,
                Configs.SplashUrlConfig.splashLoginUrlValue)
        buildConfigField("String", Configs.SplashUrlConfig.splashTokenUrl,
                Configs.SplashUrlConfig.splashTokenUrlValue)
        buildConfigField("String", Configs.SplashUrlConfig.splashLoginCallback,
                Configs.SplashUrlConfig.splashLoginCallbackValue)
    }

    //TODO: fix lint issues.
    lintOptions {
        isAbortOnError = false
    }

    buildTypes {
        getByName("debug") {
            defaultConfig.versionName?.let { resValue("string", "version", it) }
        }

        getByName("release") {
            defaultConfig.versionName?.let { resValue("string", "version", it) }
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions("version")

    productFlavors {
        create("demo") {
            dimension("version")
            applicationIdSuffix = Configs.AppConfig.demoApllicationSuffix
            versionNameSuffix = Configs.AppConfig.demoApllicationVersionNameSuffix
        }

        create("appstore") {
            dimension("version")
            applicationIdSuffix = Configs.AppConfig.appstoreApplicationSuffix
            versionNameSuffix = Configs.AppConfig.appstoreApllicationVersionNameSuffix
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    androidExtensions {
        isExperimental = true
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Configs.DependenciesConfig.androidXAppcompat)
    implementation(Configs.DependenciesConfig.googleMaterial)
    implementation(Configs.DependenciesConfig.androidXBrowser)
    implementation(Configs.DependenciesConfig.recyclerView)
    implementation(Configs.DependenciesConfig.retrofit)
    implementation(Configs.DependenciesConfig.retrofitConvertor)
    implementation(Configs.DependenciesConfig.retrofitAdapter)
    implementation(Configs.DependenciesConfig.loggingInterceptor)
    implementation(Configs.DependenciesConfig.dagger)
    kapt(Configs.DependenciesConfig.daggerCompiler)

    /**
     * Rx java extensions
     */
    implementation(Configs.DependenciesConfig.rxJava2)
    implementation(Configs.DependenciesConfig.rxAndroid)
    implementation(Configs.DependenciesConfig.rxDownloader)
    implementation(Configs.DependenciesConfig.roomRxjava2Adapter)

    /**
     * image loading libraries
     */
    implementation(Configs.DependenciesConfig.picasso)
    implementation(Configs.DependenciesConfig.materialLoading)

    /**
     * Architectre components
     */
    implementation(Configs.DependenciesConfig.lifecycleExtensions)
    implementation(Configs.DependenciesConfig.lifecycleJava8)
    implementation(Configs.DependenciesConfig.roomRuntime)
    kapt(Configs.DependenciesConfig.roomCompiler)
    implementation(Configs.DependenciesConfig.paging)

    debugImplementation(Configs.DependenciesConfig.chuckerDebug)
    releaseImplementation(Configs.DependenciesConfig.chuckerRelease)

    /**
     * Testing depndencies
     */
    testImplementation(Configs.DependenciesConfig.junit)
    testImplementation(Configs.DependenciesConfig.nharmanMockito)
    testImplementation(Configs.DependenciesConfig.junitExtension)
    testImplementation(Configs.DependenciesConfig.archCoreTesting)

    androidTestImplementation(Configs.DependenciesConfig.junitExtension)
    androidTestImplementation(Configs.DependenciesConfig.espressoCore)
    androidTestImplementation(Configs.DependenciesConfig.roomTesting)
    androidTestImplementation(Configs.DependenciesConfig.archCoreTesting)
}

