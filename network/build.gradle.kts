plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
    id("io.gitlab.arturbosch.detekt").version("1.14.2")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}


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
    implementation(Configs.DependenciesConfig.retrofit)
    implementation(Configs.DependenciesConfig.retrofitConvertor)
    implementation(Configs.DependenciesConfig.loggingInterceptor)
    implementation(Configs.DependenciesConfig.coroutinesCore)
    implementation(Configs.DependenciesConfig.dagger)
    kapt(Configs.DependenciesConfig.daggerCompiler)
}