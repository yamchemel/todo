package com.example.yamc.myapplication.data;

import android.provider.BaseColumns;

/**
 * Created by yamc on 11/22/16.
 */

public class ScheduledTaskContract {
    public static final class ScheduledTaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "scheduled_task";
        public static final String COLUMN_TASK_ID = "task_id";
        public static final String COLUMN_DATE_SCHEDULED = "date_scheduled";
    }
}
