package com.example.yamc.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.yamc.myapplication.data.TaskContract;
import com.example.yamc.myapplication.data.TaskDbHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TasksListFragment extends Fragment {

    ArrayAdapter<String> mTaskAdapter;

    public TasksListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tasks_list_fragment, container, false);
        renderDbUserTasks(rootView);

        return rootView;
    }

    public void deleteTask(View view) {
        View parentRow = (View) view.getParent();
        ListView listView = (ListView) parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);
        SQLiteDatabase db = new TaskDbHelper(getContext()).getReadableDatabase();
        // Query should match readDb().
        Cursor cursor = db.query(
                TaskContract.TaskEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                TaskContract.TaskEntry._ID +" DESC",
                String.valueOf(position) + "," + String.valueOf(position+1));
        if (cursor.moveToFirst()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry._ID);
            int row_id = cursor.getInt(idx);
            db.delete(TaskContract.TaskEntry.TABLE_NAME, TaskContract.TaskEntry._ID +  "=" + row_id, null);
        }

        renderDbUserTasks(getView());

    }
//
//    }

    private void renderDbUserTasks(View rootView) {
        List<String> dbUserTasks = readDb();
        renderTasks(rootView, dbUserTasks);
    }

    private List<String> readDb() {
        ArrayList<String> dbUserTasks = new ArrayList<String>();
        SQLiteDatabase db = new TaskDbHelper(getContext()).getReadableDatabase();
        Cursor cursor = db.query(
                TaskContract.TaskEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                TaskContract.TaskEntry._ID +" DESC");

        if (cursor.moveToFirst()) {
            do {
                int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION);
                dbUserTasks.add(cursor.getString(idx));
            } while (cursor.moveToNext());
        }


        return dbUserTasks;
    }

    private void renderTasks(View rootView, List<String> userTasks) {
        mTaskAdapter =
                new ArrayAdapter<String>(
                        getActivity(),
                        R.layout.list_item_task,
                        R.id.list_item_task_textview,
                        userTasks);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_task);
        listView.setAdapter(mTaskAdapter);
    }

    public void addNewTask(String new_task) {
        if (new_task.isEmpty())
            return;
        addNewTaskToDb(new_task);
        renderDbUserTasks(getView());
    }

    private void addNewTaskToDb(String new_task) {
        SQLiteDatabase db = new TaskDbHelper(getContext()).getWritableDatabase();

        ContentValues taskValues = new ContentValues();
        taskValues.put(TaskContract.TaskEntry.COLUMN_DATE_CREATED, (int) (new Date().getTime()/1000));
        taskValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, new_task);

        long taskRowId;
        taskRowId = db.insert(TaskContract.TaskEntry.TABLE_NAME, null, taskValues);
    }

    public void clearAllTasks() {
        clearAllTasksFromDb();
        renderDbUserTasks(getView());
    }

    private void clearAllTasksFromDb() {
        SQLiteDatabase db = new TaskDbHelper(getContext()).getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE_NAME, null, null);
    }

}