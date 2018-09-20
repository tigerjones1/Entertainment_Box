package com.example.ahmadhasssan.entertainmentbox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Ahmad Hasssan on 12/11/2015.
 */
public class MovieDatabase extends SQLiteOpenHelper {

    public static final String DB_NAME = "movie.db";
    public static final int DB_VERSION = 1;
    final String TABLE_NAME = "moviedatabase";
    public static final String COL_ID = "keyword";
    public static final String COL_JSON = "json";


    public MovieDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME +
                " (" + COL_ID + " TEXT, " + COL_JSON + " TEXT) ";
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addRecord(String keyword, String json) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_ID, keyword);
        cv.put(COL_JSON, json);
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public String search (String keyword){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+COL_JSON+" FROM " + TABLE_NAME + " WHERE "+COL_ID+"='" + keyword + "'";
        Cursor cursor = db.rawQuery(query, null);
        String result = null;
        while (cursor.moveToNext()) {
            result = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return result;
    }
}





