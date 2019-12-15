package com.karthik.splash.models.photodetail


import com.google.gson.annotations.SerializedName


data class User(@SerializedName("total_photos")
                var totalPhotos: Int = 0,
                @SerializedName("twitter_username")
                var twitterUsername: String? = null,
                @SerializedName("last_name")
                var lastName: String? = null,
                @SerializedName("bio")
                var bio: String? = null,
                @SerializedName("total_likes")
                var totalLikes: Int = 0,
                @SerializedName("portfolio_url")
                var portfolioUrl: String? = null,
                @SerializedName("profile_image")
                var profileImage: ProfileImage? = null,
                @SerializedName("name")
                var name: String? = null,
                @SerializedName("location")
                var location: String? = null,
                @SerializedName("total_collections")
                var totalCollections: Int = 0,
                @SerializedName("links")
                var links: Links? = null,
                @SerializedName("id")
                var id: String? = null,
                @SerializedName("first_name")
                var firstName: String? = null,
                @SerializedName("username")
                var username: String? = null)