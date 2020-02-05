package com.karthik.splash.storage.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.karthik.splash.storage.db.entity.PhotosStorage
import com.karthik.splash.storage.db.entity.UserInfo
import io.reactivex.Maybe
import io.reactivex.Single


@Dao
interface SplashDao {
    @Query("select * from Photos where pageno=:page AND type=:type")
    fun getPhotos(page:Int,type:String):Maybe<PhotosStorage>

    @Query("select * from User")
    fun getUserInfo(): Maybe<UserInfo>

    @Insert(onConflict = REPLACE)
    fun setPhotos(photosStorage: PhotosStorage): Single<Long>

    @Insert(onConflict = REPLACE)
    fun setUserInfo(userInfo: UserInfo):Single<Long>
}