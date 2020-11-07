package com.karthik.splash.storage.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.karthik.splash.models.photoslists.Photos

@Entity(tableName = "Photos")
data class PhotosStorage(
        @ColumnInfo(name = "pageno") var pagenumber: Int = 0,
        @ColumnInfo(name = "type") var type: String = "",
        @ColumnInfo(name = "photos") var photos: ArrayList<Photos> = ArrayList(),
        @PrimaryKey @ColumnInfo(name = "pgtype") var pgtype: String
)