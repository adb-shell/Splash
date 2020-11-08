package com.karthik.splash.storage.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserInfo(
        @ColumnInfo(name = "userid") var id: String?,
        @ColumnInfo(name = "username") var username: String?,
        @ColumnInfo(name = "email") var email: String?,
        @ColumnInfo(name = "bio") var bio: String?,
        @ColumnInfo(name = "authcode") var authcode: String?,
        @PrimaryKey @ColumnInfo(name = "user") var user: String
)