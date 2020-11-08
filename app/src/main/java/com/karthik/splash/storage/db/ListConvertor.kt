package com.karthik.splash.storage.db

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import com.karthik.splash.models.photoslists.Photos
import java.lang.reflect.Type


class ListConvertor {
    @TypeConverter
    fun fromString(value: String?): ArrayList<Photos>? {
        val listType: Type = object : TypeToken<ArrayList<Photos>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Photos>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}