package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import database.schema.TaskContract;

/**
 * Created by mahmudfasihulazam on 2017-10-12.
 * Initialize the database with placeholder data
 */

public final class InitDb {
    public static String TAG = "InitDb";
    private InitDb(){}

    public static void initDb(DatabaseHelper dbOpener) {
        SQLiteDatabase db_w = dbOpener.getWritableDatabase();
        SQLiteDatabase db_r = dbOpener.getReadableDatabase();

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
        Log.v(TAG, "Database command executed; row id: " + newRowId);

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
        Log.v(TAG, "Database command executed; row id: " + newRowId);

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
        Log.v(TAG, "Database command executed; row id: " + newRowId);

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
        Log.v(TAG, "Database command executed; row id: " + newRowId);

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
        Log.v(TAG, "Database command executed; row id: " + newRowId);

        Cursor cur = db_r.rawQuery("SELECT * FROM task", null);
        if(cur.moveToFirst()) {
            do {
                int title_ind = cur.getColumnIndex("title");
                int date_ind = cur.getColumnIndex("date");
                int desc_ind = cur.getColumnIndex("desc");
                int start_ind = cur.getColumnIndex("start");
                int end_ind = cur.getColumnIndex("end");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                String queryResult = null;
                try {
                    date = dateFormat.parse(cur.getString(date_ind) + " " + cur.getString(start_ind));
                    queryResult =
                            "Query: " + cur.getString(title_ind) + ": Date and time: "
                                    + date.toString();
                } catch (ParseException e) {
                    queryResult = "Date parse error";
                }
                Log.v(TAG, queryResult);
            } while(cur.moveToNext());
        }

    }
}
