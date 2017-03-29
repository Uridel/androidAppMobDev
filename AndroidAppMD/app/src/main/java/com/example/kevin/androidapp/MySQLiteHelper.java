package com.example.kevin.androidapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Kevin on 25-3-2017.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database info
    private static final String DATABASE_NAME = "myShowsDataTable.db";
    private static final int DATABASE_VERSION = 1;

    // Shows
    public static final String TABLE_SHOWS = "shows";
    public static final String COLUMN_SHOW_ID = "show_id";
    public static final String COLUMN_SHOW = "show";

    // Creating the table
    private static final String DATABASE_CREATE_SHOWS =
            "CREATE TABLE " + TABLE_SHOWS +
                    "(" +
                    COLUMN_SHOW_ID + " integer primary key autoincrement, " +
                    COLUMN_SHOW + " text not null" +
                    ");";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // Execute the sql to create the table show
        database.execSQL(DATABASE_CREATE_SHOWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	/*
     	* When the database gets upgraded you should handle the update to make sure there is no data loss.
     	* This is the default code you put in the upgrade method, to delete the table and call the oncreate again.
     	*/
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOWS);
        onCreate(db);
    }
}
