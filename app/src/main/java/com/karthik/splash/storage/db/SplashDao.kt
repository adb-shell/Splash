package com.karthik.splash.storage.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.karthik.splash.storage.db.entity.PhotosStorage
import com.karthik.splash.storage.db.entity.UserInfo


@Dao
interface SplashDao {
    @Query("select * from Photos where pageno=:page AND type=:type")
    suspend fun getPhotos(page: Int, type: String): PhotosStorage

    @Query("select * from User")
    suspend fun getUserInfo(): UserInfo

    @Insert(onConflict = REPLACE)
    suspend fun setPhotos(photosStorage: PhotosStorage): Long

    @Insert(onConflict = REPLACE)
    suspend fun setUserInfo(userInfo: UserInfo): Long
}
