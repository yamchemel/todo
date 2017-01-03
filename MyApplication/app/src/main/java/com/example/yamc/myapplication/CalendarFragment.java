package com.example.yamc.myapplication;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yamc.myapplication.data.TaskContract;
import com.example.yamc.myapplication.data.TaskDbHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        ArrayList<String> pickedTasks = pickTasks(3);
        setDayTask(pickedTasks.get(0), rootView.findViewById(R.id.day_1));
        setDayTask(pickedTasks.get(1), rootView.findViewById(R.id.day_3));
        setDayTask(pickedTasks.get(2), rootView.findViewById(R.id.day_4));
        return rootView;
    }

    void setDayTitle(View day, String title) {
        ((TextView) day.findViewById(R.id.day_title)).setText(title);
    }
    void setDayTask(String task, View day) {
        ((TextView) day.findViewById(R.id.day_content)).setText(task);
    }

    ArrayList<String> pickTasks(int num_tasks) {
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

        ArrayList<String> pickedTasks = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION);
                pickedTasks.add(cursor.getString(idx));
            } while (cursor.moveToNext());
        }
        return pickedTasks;
    }

}
