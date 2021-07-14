plugins {
    `kotlin-dsl`
}

gradlePlugin{
    plugins{
        create("Splash"){
          id = "splash-plugin"
          implementationClass = "com.karthik.SplashPlugin"
        }
    }
}

repositories {
    google()
    jcenter()
    maven(url = uri("https://plugins.gradle.org/m2/"))
}
dependencies {
    implementation("com.android.tools.build:gradle:7.0.0-beta05")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.17.1")
}