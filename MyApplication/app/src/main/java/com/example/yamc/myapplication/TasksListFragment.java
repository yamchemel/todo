package com.example.yamc.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yamc.myapplication.data.TaskContract;
import com.example.yamc.myapplication.data.TaskDbHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

class TaskListAdapter extends ArrayAdapter<Task> {
    private List<Task> tasks;

    public TaskListAdapter(Context context, int textViewResourceId, List<Task> tasks) {
        super(context, textViewResourceId, tasks);
        this.tasks = tasks;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_task, null);
        } else {
            row = convertView;
        }

        Task task = (Task) getItem(position);

        TextView tv = (TextView) row.findViewById(R.id.list_item_task_textview);
        tv.setText(task.description);

        if (task.is_scheduled) {
            row.setBackgroundColor(Color.parseColor("#FFE070")); // yellow
        } else {
            row.setBackgroundColor(Color.WHITE);
        }


        return row;
    }

}

public class TasksListFragment extends Fragment {

    TaskListAdapter mTaskAdapter;

    public TasksListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tasks_list_fragment, container, false);
        renderDbUserTasks(rootView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        renderDbUserTasks(getView());
    }

    public void deleteTask(View deleteButton) {
        // Get clicked task.
        Task task = mTaskAdapter.getItem(getPositionOfTaskToDelete(deleteButton));

        // Delete task from db.
        SQLiteDatabase db = new TaskDbHelper(getContext()).getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE_NAME, TaskContract.TaskEntry._ID +  "=" + task.id, null);

        renderDbUserTasks(getView());
    }

    private int getPositionOfTaskToDelete(View deleteButton) {
        View taskRow = (View) deleteButton.getParent();
        ListView listView = (ListView) taskRow.getParent();
        return listView.getPositionForView(taskRow);
    }

    private void renderDbUserTasks(View rootView) {
        List<Task> dbUserTasks = readDb();
        renderTasks(rootView, dbUserTasks);
    }

    private List<Task> readDb() {
        ArrayList<Task> dbUserTasks = new ArrayList<Task>();
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
                dbUserTasks.add(new Task(cursor));
            } while (cursor.moveToNext());
        }


        return dbUserTasks;
    }



    private void renderTasks(View rootView, List<Task> userTasks) {
        mTaskAdapter = new TaskListAdapter(
                getActivity(),
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