package com.karthik.splash.Storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.karthik.splash.Models.Photos;
import com.karthik.splash.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by karthikrk on 19/11/17.
 */

public class SqlLiteDbHandler extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "Cache";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "response";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "response";
    private enum RESPONSE_TYPES{ NEW,TRENDING,FEATURED,SEARCHED,LIKES }



    public SqlLiteDbHandler(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CACHE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT" + " ) ";
        db.execSQL(CREATE_CACHE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void updateHomeNewResponseCache(String response){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID,getKeyIdOfCoresspondingResponseType(RESPONSE_TYPES.NEW));
        contentValues.put(KEY_NAME,response);
        db.insertWithOnConflict(TABLE_NAME,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
    }


    public ArrayList<Photos> getCachedHomeNewResponse(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
                        KEY_NAME}, KEY_ID + "=?",
                new String[] { String.valueOf(RESPONSE_TYPES.NEW) },
                null, null, null, null);
        if(cursor==null)
            return (ArrayList<Photos>) Collections.<Photos>emptyList();

        cursor.moveToFirst();
        return (ArrayList<Photos>)
                Utils.convertStringToArrayList(
                        cursor.getColumnName(1),Utils.ResponseType.NEW);
    }


    public void updateHomeTrendingResponseCache(String response){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID,getKeyIdOfCoresspondingResponseType(RESPONSE_TYPES.TRENDING));
        contentValues.put(KEY_NAME,response);
        db.insertWithOnConflict(TABLE_NAME,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public ArrayList<Photos> getTrendingHomeNewResponse(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
                        KEY_NAME}, KEY_ID + "=?",
                new String[] { String.valueOf(RESPONSE_TYPES.TRENDING) },
                null, null, null, null);
        if(cursor==null)
            return (ArrayList<Photos>) Collections.<Photos>emptyList();

        cursor.moveToFirst();
        return (ArrayList<Photos>)
                Utils.convertStringToArrayList(
                        cursor.getColumnName(1),Utils.ResponseType.TRENDING);
    }


    public void updateHomeFeaturedResponseCache(String response){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID,getKeyIdOfCoresspondingResponseType(RESPONSE_TYPES.FEATURED));
        contentValues.put(KEY_NAME,response);
        db.insertWithOnConflict(TABLE_NAME,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public ArrayList<Photos> getFeaturedHomeNewResponse(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
                        KEY_NAME}, KEY_ID + "=?",
                new String[] { String.valueOf(RESPONSE_TYPES.FEATURED) },
                null, null, null, null);
        if(cursor==null)
            return (ArrayList<Photos>) Collections.<Photos>emptyList();

        cursor.moveToFirst();
        return (ArrayList<Photos>)
                Utils.convertStringToArrayList(
                        cursor.getColumnName(1),Utils.ResponseType.FEATURED);
    }

    public void updateSearchedResponseCache(String response){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID,getKeyIdOfCoresspondingResponseType(RESPONSE_TYPES.SEARCHED));
        contentValues.put(KEY_NAME,response);
        db.insertWithOnConflict(TABLE_NAME,null,contentValues,
                SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void updateLikesResponseCache(String response){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID,getKeyIdOfCoresspondingResponseType(RESPONSE_TYPES.LIKES));
        contentValues.put(KEY_NAME,response);
        db.insertWithOnConflict(TABLE_NAME,null,contentValues,
                SQLiteDatabase.CONFLICT_REPLACE);
    }


    private int getKeyIdOfCoresspondingResponseType(RESPONSE_TYPES type){
        try {
          return Integer.parseInt(String.valueOf(type));
        }catch (NumberFormatException e){
            return 0;
        }
    }
}
