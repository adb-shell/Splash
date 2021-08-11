package com.karthik

import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.addComposeDependencies() {
    val config = "implementation"
    add(config,Configs.ComposeConfig.composeUi)
    add(config,Configs.ComposeConfig.composeUiTooling)
    add(config,Configs.ComposeConfig.composeFoundation)
    add(config,Configs.ComposeConfig.composeMaterial)
    add(config,Configs.ComposeConfig.composeMaterialIcons)
    add(config,Configs.ComposeConfig.composeMaterialIconsExtended)
    add(config,Configs.ComposeConfig.composeLiveDataSupport)
    add(config,Configs.ComposeConfig.composeActivity)
    add(config,Configs.ComposeConfig.composePagination)
}