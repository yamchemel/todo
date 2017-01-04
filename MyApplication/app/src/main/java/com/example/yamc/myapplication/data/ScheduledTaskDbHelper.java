package com.example.yamc.myapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.yamc.myapplication.data.ScheduledTaskContract.ScheduledTaskEntry;

/**
 * Created by yamc on 11/22/16.
 */

public class ScheduledTaskDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "scheduled_task.db";

    public ScheduledTaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_WEATHER_TABLE = "CREATE TABLE " + ScheduledTaskEntry.TABLE_NAME + " (" +
                ScheduledTaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                ScheduledTaskEntry.COLUMN_TASK_ID + " INTEGER NOT NULL, " +
                ScheduledTaskEntry.COLUMN_DATE_SCHEDULED + " STRING NOT NULL " +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ScheduledTaskEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
