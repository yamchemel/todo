package com.example.yamc.myapplication.data;

import android.provider.BaseColumns;

/**
 * Created by yamc on 11/22/16.
 */

public class TaskContract {
    public static final class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DATE_CREATED = "date_created";
    }
}
