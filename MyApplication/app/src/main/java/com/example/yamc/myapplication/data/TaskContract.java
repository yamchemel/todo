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
        public static final String COLUMN_IS_SCHEDULED = "is_scheduled";
        public static final String COLUMN_DATE_SCHEDULED = "date_scheduled";
    }
}
