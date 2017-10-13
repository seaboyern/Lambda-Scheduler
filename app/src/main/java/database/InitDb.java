package database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import database.schema.TaskContract;

/**
 * Created by mahmudfasihulazam on 2017-10-12.
 * Initialize the database with placeholder data
 */

public final class InitDb {
    private InitDb(){}

    public static void initDb(DatabaseHelper dbOpener) {
        SQLiteDatabase db_w = dbOpener.getWritableDatabase();

        // Populate task table:
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_NAME_TITLE,
                "Task1");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DESC,
                "First task");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DATE,
                "2017-10-28");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_START,
                "00:00:00");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_END,
                "00:00:30");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_PRIO,
                10);
        // INSERT INTO task:
        long newRowId =
                db_w.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);
        // Log table status:
        Log.v("MainActivity", "Database command executed; row id: " + newRowId);

        values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_NAME_TITLE,
                "Task2");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DESC,
                "Second task");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DATE,
                "2017-10-28");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_START,
                "00:00:31");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_END,
                "00:01:00");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_PRIO,
                10);
        // INSERT INTO task:
        newRowId =
                db_w.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);
        // Log table status:
        Log.v("MainActivity", "Database command executed; row id: " + newRowId);

        values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_NAME_TITLE,
                "Task3");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DESC,
                "Third task");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DATE,
                "2017-10-28");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_START,
                "00:01:00");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_END,
                "00:02:00");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_PRIO,
                10);
        // INSERT INTO task:
        newRowId =
                db_w.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);
        // Log table status:
        Log.v("MainActivity", "Database command executed; row id: " + newRowId);

        values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_NAME_TITLE,
                "Task4");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DESC,
                "Fourth task");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DATE,
                "2017-10-28");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_START,
                "00:02:00");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_END,
                "00:03:00");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_PRIO,
                10);
        // INSERT INTO task:
        newRowId =
                db_w.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);
        // Log table status:
        Log.v("MainActivity", "Database command executed; row id: " + newRowId);

        values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_NAME_TITLE,
                "Task5");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DESC,
                "Fifth task");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DATE,
                "2017-10-28");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_START,
                "00:03:00");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_END,
                "00:04:00");
        values.put(TaskContract.TaskEntry.COLUMN_NAME_PRIO,
                10);
        // INSERT INTO task:
        newRowId =
                db_w.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);
        // Log table status:
        Log.v("MainActivity", "Database command executed; row id: " + newRowId);

        // Log entire table:
        Log.v("MainActivity", dbOpener.getTableAsString("task"));
    }
}
