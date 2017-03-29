package com.example.kevin.androidapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 25-3-2016.
 */
public class DataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] showAllColumns = { MySQLiteHelper.COLUMN_SHOW_ID, MySQLiteHelper.COLUMN_SHOW };


    public DataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        database = dbHelper.getWritableDatabase();
        dbHelper.close();
    }

    // Opens the database to use it
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Closes the database when you no longer need it
    public void close() {
        dbHelper.close();
    }

    public long createShows(String show) {
        // If the database is not open yet, open it
        if (!database.isOpen())
            open();

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_SHOW, show);
        long insertId = database.insert(MySQLiteHelper.TABLE_SHOWS, null, values);

        // If the database is open, close it
        if (database.isOpen())
            close();

        return insertId;
    }

    public void deleteShows(Show show) {
        if (!database.isOpen())
            open();

        database.delete(MySQLiteHelper.TABLE_SHOWS, MySQLiteHelper.COLUMN_SHOW_ID + " =?", new String[] { Long.toString(show.getId())});

        if (database.isOpen())
            close();

    }

    public void updateShow(Show show) {
        if (!database.isOpen())
            open();

        ContentValues args = new ContentValues();
        args.put(MySQLiteHelper.COLUMN_SHOW, show.getShow());
        database.update(MySQLiteHelper.TABLE_SHOWS, args, MySQLiteHelper.COLUMN_SHOW_ID + "=?", new String[]{Long.toString(show.getId())});
        if (database.isOpen())
            close();
    }

    public List<Show> getAllShows() {
        if (!database.isOpen())
            open();

        List<Show> shows = new ArrayList<Show>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_SHOWS, showAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Show show = cursorToShow(cursor);
            shows.add(show);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        if (database.isOpen())
            close();

        return shows;
    }

    private Show cursorToShow(Cursor cursor) {
        try {
            Show show = new Show();
            show.setId(cursor.getLong(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_SHOW_ID)));
            show.setShows(cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_SHOW)));
            return show;
        } catch(CursorIndexOutOfBoundsException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Show getShow(long columnId) {
        if (!database.isOpen())
            open();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_SHOWS, showAllColumns, MySQLiteHelper.COLUMN_SHOW_ID + "=?", new String[] { Long.toString(columnId)}, null, null, null);

        cursor.moveToFirst();
        Show show = cursorToShow(cursor);
        cursor.close();

        if (database.isOpen())
            close();

        return show;
    }
}
