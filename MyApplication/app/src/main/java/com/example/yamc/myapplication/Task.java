package com.example.yamc.myapplication;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.yamc.myapplication.data.TaskContract;

import static android.content.ContentValues.TAG;

public class Task {
    public int id;
    public String description;
    public String date_created; // TODO: change to date format.
    public boolean is_scheduled;
    public String date_scheduled;   // TODO: change to date format.

    public Task(Cursor dbRow) {
        int id_idx = dbRow.getColumnIndex(TaskContract.TaskEntry._ID);
        this.id = dbRow.getInt(id_idx);

        int description_idx = dbRow.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION);
        this.description = dbRow.getString(description_idx);

        int date_created_idx = dbRow.getColumnIndex(TaskContract.TaskEntry.COLUMN_DATE_CREATED);
        this.date_created = dbRow.getString(date_created_idx);

        int is_scheduled_idx = dbRow.getColumnIndex(TaskContract.TaskEntry.COLUMN_IS_SCHEDULED);
        this.is_scheduled = dbRow.getInt(is_scheduled_idx) > 0;

        if (this.is_scheduled) {
            int date_scheduled_idx = dbRow.getColumnIndex(TaskContract.TaskEntry.COLUMN_DATE_SCHEDULED);
            this.date_scheduled = dbRow.getString(date_scheduled_idx);
        }

    }
}
