package com.karthik.network.home.bottomliketab.models

import java.io.Serializable

/**
 * Created by karthikrk on 20/12/15.
 */
data class Urls(
        var full: String? = null,
        var regular: String? = null,
        var small: String? = null,
        var thumb: String? = null
): Serializable
