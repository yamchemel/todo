package com.example.yamc.myapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myapplication.MESSAGE";
//    private static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        // Disable add button.
        Button button = (Button) findViewById(R.id.add_task_button);
        button.setEnabled(false);

        // Don't display keyboard in startup.
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        EditText editText = (EditText) findViewById(R.id.edit_task);
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                Log.d("KeyCode", Integer.toString(keyCode));
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    addTask(view);
                    return true;
                }
                return false;
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence tast_text, int start, int before, int count) {
                disableBottonIfNoText(tast_text);
            }

            @Override
            public void beforeTextChanged(CharSequence tast_text, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable tast_text) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void disableBottonIfNoText(CharSequence task_text) {
        Button button = (Button) findViewById(R.id.add_task_button);
        if (task_text.toString().trim().length() == 0) {
            button.setEnabled(false);
        } else {
            button.setEnabled(true);
        }
    }

    public void addTask(View view) {

        EditText editText = (EditText) findViewById(R.id.edit_task);
        String new_user_task = editText.getText().toString();
        TasksListFragment tasksListFragment = (TasksListFragment) getSupportFragmentManager().findFragmentById(R.id.tasks_list_fragment_id);
        tasksListFragment.addNewTask(new_user_task);

        // Remove keyboard after task was entered.
        InputMethodManager inputManager =
                (InputMethodManager) getApplicationContext().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        editText.setText("");
    }

    public void clearAllTasks(View view) {
        TasksListFragment tasksListFragment = (TasksListFragment) getSupportFragmentManager().findFragmentById(R.id.tasks_list_fragment_id);
        tasksListFragment.clearAllTasks();
    }

    public void deleteTask(View view) {
        TasksListFragment tasksListFragment = (TasksListFragment) getSupportFragmentManager().findFragmentById(R.id.tasks_list_fragment_id);
        tasksListFragment.deleteTask(view);
    }

    public void showCalendar(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
}
