// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Configs.AppConfig.androidToolsVersion}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Configs.AppConfig.kotlinVersion}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://jitpack.io")
    }
}
