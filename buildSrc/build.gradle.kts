plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://jitpack.io")
    }
}