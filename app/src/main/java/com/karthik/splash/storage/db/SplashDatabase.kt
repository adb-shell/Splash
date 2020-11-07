package com.karthik.splash.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.karthik.splash.storage.db.entity.PhotosStorage
import com.karthik.splash.storage.db.entity.UserInfo

@Database(entities = [PhotosStorage::class, UserInfo::class], version = 1)
@TypeConverters(ListConvertor::class)
abstract class SplashDatabase : RoomDatabase() {
    abstract fun getDatabaseDao(): SplashDao
}