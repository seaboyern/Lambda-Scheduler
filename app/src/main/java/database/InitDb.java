package database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import database.schema.TaskContract;
import entities.Task;

/**
 * Created by mahmudfasihulazam on 2017-10-12.
 * Initialize the database with placeholder data
 */

public final class InitDb {
    public static String TAG = "InitDb";
    private InitDb(){}

    public static void initDb(SQLiteDatabase db_w) {

        db_w.execSQL(TaskContract.DELETE_TABLE);
        db_w.execSQL(TaskContract.CREATE_TABLE);
        // Populate task table:
        Task[] tasks = new Task[10];
        try {
            tasks[0] = new Task("Task1", "2017-10-28", "00:00:00", "00:00:30", "First task", 100);
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
        try {
            tasks[1] = new Task("Task2", "2017-10-28", "00:00:31", "00:00:59", "Second task", 90);
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
        try {
            tasks[2] = new Task("Task3", "2017-10-28", "00:01:00", "00:01:59", "Third task", 80);
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
        try {
            tasks[3] = new Task("Task4", "2017-10-28", "00:02:00", "00:02:59", "Fourth task", 70);
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
        try {
            tasks[4] = new Task("Task5", "2017-10-28", "00:03:00", "00:03:59", "Fifth tasks", 110);
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
        try {
            tasks[5] = new Task("Task6", "2017-10-29", "00:03:00", "00:03:59", "Sixth task", 60);
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
        try {
            tasks[6] = new Task("Task7", "2017-10-29", "00:04:00", "00:04:59", "Seventh task", 70);
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
        try {
            tasks[7] = new Task("Task8", "2017-10-30", "00:01:00", "00:01:59", "Eighth task", 80);
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
        try {
            tasks[8] = new Task("Task9", "2017-11-01", "00:01:00", "00:01:59", "Ninth task", 80);
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
        try {
            tasks[9] = new Task("Task10", "2017-11-01", "00:02:00", "00:02:59", "Tenth task", 100);
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }

        for(Task i : tasks) {
            try {
                Log.v(TAG, i.toString());
                long newRowId =
                        db_w.insert(TaskContract.TaskEntry.TABLE_NAME, null, i.databaseObject());
                // Log table status:
                if(0 <= newRowId) {
                    Log.v(TAG, "Database command executed; row id: " + newRowId);
                } else {
                    Log.e(TAG, "Entry exists: " + i.getTitle());
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            // INSERT INTO task:
        }

        Cursor cur = db_w.rawQuery("SELECT * FROM task", null);
        int i = 0;
        if(cur.moveToFirst()) {
            do {
                Task task = new Task(cur);
                Log.v(TAG, task.toString());
            } while(cur.moveToNext());
        }

        cur.close();

    }
}
