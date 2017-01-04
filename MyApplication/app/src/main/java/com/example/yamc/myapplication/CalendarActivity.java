package com.example.yamc.myapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.yamc.myapplication.data.TaskContract;
import com.example.yamc.myapplication.data.TaskDbHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);
    }


    public void clearScheduledTasks(View view) {
        CalendarFragment calendarFragment = (CalendarFragment) getSupportFragmentManager().findFragmentById(R.id.calendar_fragment_id);
        calendarFragment.clearScheduledTasks();
    }

    public void scheduleWeeklyTasks(View view) {

        CalendarFragment calendarFragment = (CalendarFragment) getSupportFragmentManager().findFragmentById(R.id.calendar_fragment_id);
        calendarFragment.scheduleWeeklyTasks();
    }
}