package com.karthik

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.withType

class SplashPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.apply("kotlin-kapt")
        project.plugins.apply("io.gitlab.arturbosch.detekt")

        project.tasks.withType<JavaCompile>{
            targetCompatibility = JavaVersion.VERSION_1_8.toString()
            sourceCompatibility = JavaVersion.VERSION_1_8.toString()
        }
    }
}