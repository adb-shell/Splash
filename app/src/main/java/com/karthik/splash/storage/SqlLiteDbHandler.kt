package com.karthik.splash.storage

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.karthik.splash.misc.Utils
import com.karthik.splash.models.PhotosLists.Photos
import java.util.ArrayList

class SqlLiteDbHandler: SQLiteOpenHelper {

    private val TABLE_NAME = "response"
    private val KEY_ID = "id"
    private val KEY_NAME = "response"

    private enum class RESPONSE_TYPES {
        NEW, TRENDING, FEATURED, SEARCHED, LIKES
    }

    constructor(context:Context):super(context,"Cache", null,1)

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_CACHE_TABLE = ("CREATE TABLE " + TABLE_NAME + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT" + " ) ")
        db.execSQL(CREATE_CACHE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun updateHomeNewResponseCache(response: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, getKeyIdOfCoresspondingResponseType(RESPONSE_TYPES.NEW))
        contentValues.put(KEY_NAME, response)
        db.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun getCachedHomeNewResponse(): ArrayList<Photos> {
        val db = this.writableDatabase
        val cursor = db.query(TABLE_NAME, arrayOf(KEY_ID, KEY_NAME), "$KEY_ID=?",
                arrayOf(RESPONSE_TYPES.NEW.toString()), null, null, null, null)
                ?: return emptyList<Photos>() as ArrayList<Photos>

        cursor.moveToFirst()
        return Utils.convertStringToArrayList(cursor.getColumnName(1),
                Utils.ResponseType.NEW) as ArrayList<Photos>
    }

    fun updateHomeTrendingResponseCache(response: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, getKeyIdOfCoresspondingResponseType(RESPONSE_TYPES.TRENDING))
        contentValues.put(KEY_NAME, response)
        db.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun getTrendingHomeNewResponse(): ArrayList<Photos> {
        val db = this.writableDatabase
        val cursor = db.query(TABLE_NAME, arrayOf(KEY_ID, KEY_NAME), "$KEY_ID=?",
                arrayOf(RESPONSE_TYPES.TRENDING.toString()), null, null, null, null)
                ?: return emptyList<Photos>() as ArrayList<Photos>

        cursor.moveToFirst()
        return Utils.convertStringToArrayList(
                cursor.getColumnName(1), Utils.ResponseType.TRENDING) as ArrayList<Photos>
    }

    fun updateHomeFeaturedResponseCache(response: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, getKeyIdOfCoresspondingResponseType(RESPONSE_TYPES.FEATURED))
        contentValues.put(KEY_NAME, response)
        db.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun getFeaturedHomeNewResponse(): ArrayList<Photos> {
        val db = this.writableDatabase
        val cursor = db.query(TABLE_NAME, arrayOf(KEY_ID, KEY_NAME), "$KEY_ID=?",
                arrayOf(RESPONSE_TYPES.FEATURED.toString()), null, null, null, null)
                ?: return emptyList<Photos>() as ArrayList<Photos>

        cursor.moveToFirst()
        return Utils.convertStringToArrayList(
                cursor.getColumnName(1), Utils.ResponseType.FEATURED) as ArrayList<Photos>
    }

    fun updateSearchedResponseCache(response: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, getKeyIdOfCoresspondingResponseType(RESPONSE_TYPES.SEARCHED))
        contentValues.put(KEY_NAME, response)
        db.insertWithOnConflict(TABLE_NAME, null, contentValues,
                SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun updateLikesResponseCache(response: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, getKeyIdOfCoresspondingResponseType(RESPONSE_TYPES.LIKES))
        contentValues.put(KEY_NAME, response)
        db.insertWithOnConflict(TABLE_NAME, null, contentValues,
                SQLiteDatabase.CONFLICT_REPLACE)
    }

    private fun getKeyIdOfCoresspondingResponseType(type: RESPONSE_TYPES): Int {
        return try {
            Integer.parseInt(type.toString())
        } catch (e: NumberFormatException) {
            0
        }
    }
}