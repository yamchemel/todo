package com.example.yamc.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yamc.myapplication.data.TaskContract;
import com.example.yamc.myapplication.data.TaskDbHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CalendarFragment extends Fragment {

//    ArrayAdapter<String> mDayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calendar_fragment, container, false);

        setDayTitle(rootView.findViewById(R.id.day_1), "Sunday");
        setDayTitle(rootView.findViewById(R.id.day_2), "Monday");
        setDayTitle(rootView.findViewById(R.id.day_3), "Tuesday");
        setDayTitle(rootView.findViewById(R.id.day_4), "Wednesday");
        setDayTitle(rootView.findViewById(R.id.day_5), "Thursday");
        setDayTitle(rootView.findViewById(R.id.day_6), "Friday");
        setDayTitle(rootView.findViewById(R.id.day_7), "Saturday");

        renderCalendar(rootView);

        return rootView;
    }



    private void renderCalendar(View view) {

        LinearLayout days = (LinearLayout) view.findViewById(R.id.calendar_days);
        int count = days.getChildCount();
        for (int i = 0; i < count; i++) {
            View day = days.getChildAt(i);
            if (day instanceof LinearLayout) {
                ((TextView) day.findViewById(R.id.day_content)).setText("");
            }
        }

        SQLiteDatabase db = new TaskDbHelper(getContext()).getReadableDatabase();
        Cursor cursor = db.query(
                TaskContract.TaskEntry.TABLE_NAME,
                null,
                TaskContract.TaskEntry.COLUMN_IS_SCHEDULED +">0",
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                Task pickedTask = new Task(cursor);
                Log.d(TAG, "renderCalendar: pickedTask.date_scheduled = " + pickedTask.date_scheduled);
                TextView day_content = ((TextView) view.findViewWithTag(pickedTask.date_scheduled).findViewById(R.id.day_content));
                day_content.setText(pickedTask.description);
            } while (cursor.moveToNext());
        }
    }

    void setDayTitle(View day, String title) {
        ((TextView) day.findViewById(R.id.day_title)).setText(title);
    }

    private void markTaskIsScheduled(Task task, String day) {
        SQLiteDatabase db = new TaskDbHelper(getContext()).getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TaskContract.TaskEntry.COLUMN_IS_SCHEDULED, 1);
        cv.put(TaskContract.TaskEntry.COLUMN_DATE_SCHEDULED, day);
        Log.d(TAG, "markTaskIsScheduled: day_1");
        // TODO: Add task date (when we have calendar dates in addition to days.)

        db.update(TaskContract.TaskEntry.TABLE_NAME, cv, TaskContract.TaskEntry._ID + "=" + task.id, null);
    }

    private ArrayList<Task> pickTasks(int num_tasks) {
        SQLiteDatabase db = new TaskDbHelper(getContext()).getReadableDatabase();
        Cursor cursor = db.query(
                TaskContract.TaskEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                "RANDOM()",
                Integer.toString(num_tasks));

        ArrayList<Task> pickedTasks = new ArrayList<Task>();
        if (cursor.moveToFirst()) {
            do {
                pickedTasks.add(new Task(cursor));
            } while (cursor.moveToNext());
        }
        return pickedTasks;
    }

    public void scheduleWeeklyTasks() {
        ArrayList<Task> pickedTasks = pickTasks(3);
        markTaskIsScheduled(pickedTasks.get(0), "day_1");
        markTaskIsScheduled(pickedTasks.get(1), "day_3");
        markTaskIsScheduled(pickedTasks.get(2), "day_4");
        // TODO: pick days
        renderCalendar(getView());
    }
    public void clearScheduledTasks() {
        SQLiteDatabase db = new TaskDbHelper(getContext()).getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TaskContract.TaskEntry.COLUMN_IS_SCHEDULED, 0);
        db.update(TaskContract.TaskEntry.TABLE_NAME, cv, TaskContract.TaskEntry.COLUMN_IS_SCHEDULED + " > 0", null);

        renderCalendar(getView());
    }

}
