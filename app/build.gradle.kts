import com.karthik.Configs
import com.karthik.addComposeDependencies

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("splash-plugin")
}

android {
    compileSdk = Configs.AppConfig.compileSDKVersion.toInt()
    defaultConfig {
        minSdk = Configs.AppConfig.minSDKVersion
        targetSdkPreview = Configs.AppConfig.compileSDKVersion
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

    lintOptions {
        isAbortOnError = false
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    composeOptions {
        kotlinCompilerVersion = Configs.AppConfig.kotlinVersion
        kotlinCompilerExtensionVersion = "1.0.0-beta09"
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

    flavorDimensions.add("version")

    productFlavors {
        create("demo") {
            dimension = "version"
            applicationIdSuffix = Configs.AppConfig.demoApllicationSuffix
            versionNameSuffix = Configs.AppConfig.demoApllicationVersionNameSuffix
        }

        create("appstore") {
            dimension = "version"
            applicationIdSuffix = Configs.AppConfig.appstoreApplicationSuffix
            versionNameSuffix = Configs.AppConfig.appstoreApllicationVersionNameSuffix
        }
    }

    androidExtensions {
        isExperimental = true
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

//TODO: move to a common plugin
detekt{
    toolVersion = "1.14.2"
    buildUponDefaultConfig = true
    reports{
        html {
            enabled = true
            destination = file("../config/detekt-reports.html")
        }
    }
    autoCorrect = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":network"))
    implementation(Configs.DependenciesConfig.androidXAppcompat)
    implementation(Configs.DependenciesConfig.googleMaterial)
    implementation(Configs.DependenciesConfig.androidXBrowser)
    implementation(Configs.DependenciesConfig.retrofit)
    implementation(Configs.DependenciesConfig.retrofitConvertor)
    implementation(Configs.DependenciesConfig.loggingInterceptor)
    implementation(Configs.DependenciesConfig.dagger)
    kapt(Configs.DependenciesConfig.daggerCompiler)

    /**
     * Rx java extensions
     */
    implementation(Configs.DependenciesConfig.rxDownloader)


    implementation(Configs.DependenciesConfig.coilAccompanist)

    /**
     * Architectre components
     */
    implementation(Configs.DependenciesConfig.lifecycleExtensions)
    implementation(Configs.DependenciesConfig.lifecycleJava8)
    implementation(Configs.DependenciesConfig.roomRuntime)
    kapt(Configs.DependenciesConfig.roomCompiler)
    implementation(Configs.DependenciesConfig.roomCoroutinesAdapter)
    implementation(Configs.DependenciesConfig.paging)

    /**
     * coroutines support.
     */
    implementation(Configs.DependenciesConfig.coroutinesAndroid)
    implementation(Configs.DependenciesConfig.viewmodelKTX)

    debugImplementation(Configs.DependenciesConfig.chuckerDebug)
    releaseImplementation(Configs.DependenciesConfig.chuckerRelease)

    /**
     * compose dependencies
     */
    addComposeDependencies()

    /**
     * Testing depndencies
     */
    testImplementation(Configs.DependenciesConfig.junit)
    testImplementation(Configs.DependenciesConfig.mockitoKotlin)
    testImplementation(Configs.DependenciesConfig.junitExtension)
    testImplementation(Configs.DependenciesConfig.archCoreTesting)

    androidTestImplementation(Configs.DependenciesConfig.junitExtension)
    androidTestImplementation(Configs.DependenciesConfig.espressoCore)
    androidTestImplementation(Configs.DependenciesConfig.roomTesting)
    androidTestImplementation(Configs.DependenciesConfig.archCoreTesting)
}

