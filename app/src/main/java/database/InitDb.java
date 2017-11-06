package database;

import android.content.ContentValues;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.content.Context;
import android.util.Log;

import database.schema.AssignmentContract;
import database.schema.CommitmentContract;
import database.schema.CourseCommitmentContract;
import database.schema.CourseContract;
import database.schema.ExamContract;
import database.schema.TaskContract;
import database.tables.AssignmentTable;
import database.tables.CommitmentTable;
import database.tables.CourseCommitmentTable;

/**
 * Created by mahmudfasihulazam on 2017-10-12.
 * Initialize the database with placeholder data
 */

public final class InitDb {
    public static String TAG = "InitDb";
    private InitDb(){}

    public static void initDb(Context c) {
        // Insert an assignment:
        // Insert commitment entry:
        ContentValues values = new ContentValues();
        values.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_TITLE, "A1");
        values.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_DESC, "First assignment");
        values.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_PRIO, 10);
        values.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_TYPE,
                CourseCommitmentContract.TABLE_NAME);
        try {
            CommitmentTable.getInstance(c).remove(values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            CommitmentTable.getInstance(c).insert(values);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert course commitment entry:
        values = new ContentValues();
        values.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_TITLE, "A1");
        values.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_CRID, "CMPT 370");
        values.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_TYPE,
                AssignmentContract.TABLE_NAME);
        values.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_WT, 5.0);
        values.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_REQ, 5.0);
        values.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_ACH, 5.0);
        try {
            CourseCommitmentTable.getInstance(c).remove(values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            CourseCommitmentTable.getInstance(c).insert(values);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert assignment entry:
        values = new ContentValues();
        values.put(AssignmentContract.AssignmentEntry.COLUMN_NAME_TITLE, "A1");
        values.put(AssignmentContract.AssignmentEntry.COLUMN_NAME_CRID, "CMPT 370");
        try {
            AssignmentTable.getInstance(c).remove(values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            AssignmentTable.getInstance(c).insert(values);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Print all tables:
        Log.v(TAG, CommitmentTable.getInstance(c).toString());
        Log.v(TAG, CourseCommitmentTable.getInstance(c).toString());
        Log.v(TAG, AssignmentTable.getInstance(c).toString());

    }
}
