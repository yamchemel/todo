package com.example.yamc.myapplication.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import java.util.HashSet;

import static com.example.yamc.myapplication.data.TestUtilities.validateCurrentRecord;

/**
 * Created by yamc on 11/22/16.
 */

public class TestDb extends AndroidTestCase {
    private static final String TEST_FILE_PREFIX = "test_";

    RenamingDelegatingContext context;

    void deleteTheDatabase() { mContext.deleteDatabase(TaskDbHelper.DATABASE_NAME); }

    public void setUp() {
        deleteTheDatabase();
        context = new RenamingDelegatingContext(getContext(), TEST_FILE_PREFIX);
    }

    public void testCreateDb() throws Throwable {

        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(TaskContract.TaskEntry.TABLE_NAME);

        mContext.deleteDatabase(TaskDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new TaskDbHelper(context).getWritableDatabase();
        assertEquals(true, db.isOpen());

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Database wasn't created correctly", c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );
        assertTrue("database was created without task entry", tableNameHashSet.isEmpty());

        c = db.rawQuery("PRAGMA table_info(" + TaskContract.TaskEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        final HashSet<String> taskColumnHashSet = new HashSet<String>();
        taskColumnHashSet.add(TaskContract.TaskEntry._ID);
        taskColumnHashSet.add(TaskContract.TaskEntry.COLUMN_DATE_CREATED);
        taskColumnHashSet.add(TaskContract.TaskEntry.COLUMN_DESCRIPTION);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            taskColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                taskColumnHashSet.isEmpty());
        db.close();
        Log.d("FINISH", "finishing test");
    }

    public void testWeatherTable() {
        SQLiteDatabase db = new TaskDbHelper(context).getWritableDatabase();
        assertTrue(db.isOpen());

        ContentValues taskValues = new ContentValues();
        taskValues.put(TaskContract.TaskEntry.COLUMN_DATE_CREATED, 123);
        taskValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, "Some test task");

        long taskRowId;
        taskRowId = db.insert(TaskContract.TaskEntry.TABLE_NAME, null, taskValues);

        Cursor cursor = db.query(
                TaskContract.TaskEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        assertTrue("No records returned from weather table", cursor.moveToFirst());

        validateCurrentRecord("weather table validation failed", cursor, taskValues);

        cursor.close();
        db.close();
    }
}
