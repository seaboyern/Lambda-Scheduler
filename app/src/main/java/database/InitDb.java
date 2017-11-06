package database;

import android.content.ContentValues;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import android.util.Log;

import database.schema.AssignmentContract;
import database.schema.CommitmentContract;
import database.schema.CourseCommitmentContract;
import database.schema.CourseContract;
import database.schema.ExamContract;
import database.schema.TaskContract;

/**
 * Created by mahmudfasihulazam on 2017-10-12.
 * Initialize the database with placeholder data
 */

public final class InitDb {
    public static String TAG = "InitDb";
    private InitDb(){}

    public static void initDb(DatabaseHelper db) {
        // Print all tables:
        Log.v(TAG, db.getTableAsString(CommitmentContract.TABLE_NAME));
        Log.v(TAG, db.getTableAsString(CourseCommitmentContract.TABLE_NAME));
        Log.v(TAG, db.getTableAsString(AssignmentContract.TABLE_NAME));
        Log.v(TAG, db.getTableAsString(ExamContract.TABLE_NAME));
        Log.v(TAG, db.getTableAsString(CourseContract.TABLE_NAME));
        Log.v(TAG, db.getTableAsString(TaskContract.TABLE_NAME));

        // Insert an assignment:
        // Insert commitment entry:
        ContentValues values = new ContentValues();
        values.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_TITLE, "A1");
        values.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_DESC, "First assignment");
        values.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_PRIO, 10);
        values.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_TYPE,
                CourseCommitmentContract.TABLE_NAME);
        db.getWritableDatabase().insert(CommitmentContract.TABLE_NAME, null, values);

        // Insert course commitment entry:
        values = new ContentValues();
        values.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_TITLE, "A1");
        values.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_CRID, "CMPT 370");
        values.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_TYPE,
                AssignmentContract.TABLE_NAME);
        values.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_WT, 5.0);
        values.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_REQ, 5.0);
        values.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_ACH, 5.0);
        db.getWritableDatabase().insert(CourseCommitmentContract.TABLE_NAME, null, values);

        // Insert assignment entry:
        values = new ContentValues();
        values.put(AssignmentContract.AssignmentEntry.COLUMN_NAME_TITLE, "A1");
        values.put(AssignmentContract.AssignmentEntry.COLUMN_NAME_CRID, "CMPT 370");
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            values.put(AssignmentContract.AssignmentEntry.COLUMN_NAME_DEADLINE,
                    fmt.format(fmt.parse("2017-11-21 23:59:00")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        db.getWritableDatabase().insert(AssignmentContract.TABLE_NAME, null, values);

        // Print all tables:
        Log.v(TAG, db.getTableAsString(CommitmentContract.TABLE_NAME));
        Log.v(TAG, db.getTableAsString(CourseCommitmentContract.TABLE_NAME));
        Log.v(TAG, db.getTableAsString(AssignmentContract.TABLE_NAME));
        Log.v(TAG, db.getTableAsString(ExamContract.TABLE_NAME));
        Log.v(TAG, db.getTableAsString(CourseContract.TABLE_NAME));
        Log.v(TAG, db.getTableAsString(TaskContract.TABLE_NAME));


    }
}
