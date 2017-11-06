package database;

import android.content.ContentValues;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.content.Context;
import android.util.Log;

import database.schema.AssignmentContract;
import database.schema.CommitmentContract;
import database.schema.CourseCommitmentContract;
import database.schema.TaskContract;
import database.tables.AssignmentTable;
import database.tables.CommitmentTable;
import database.tables.CourseCommitmentTable;
import database.tables.TaskTable;

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
        DatabaseObject obj = new DatabaseObject() {
            @Override
            public ContentValues databaseObject() {
                ContentValues values = new ContentValues();
                values.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_TITLE, "A1");
                values.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_DESC, "First assignment");
                values.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_PRIO, 10);
                values.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_TYPE,
                        CourseCommitmentContract.TABLE_NAME);
                return values;
            }
        };
        try {
            CommitmentTable.getInstance(c).remove(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            CommitmentTable.getInstance(c).insert(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert course commitment entry:
        obj = new DatabaseObject() {
            @Override
            public ContentValues databaseObject() {
                ContentValues values = new ContentValues();
                values.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_TITLE, "A1");
                values.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_CRID, "CMPT 370");
                values.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_TYPE,
                        AssignmentContract.TABLE_NAME);
                values.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_WT, 5.0);
                values.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_REQ, 5.0);
                values.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_ACH, 5.0);
                return values;
            }
        };
        try {
            CourseCommitmentTable.getInstance(c).remove(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            CourseCommitmentTable.getInstance(c).insert(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert assignment entry:
        obj = new DatabaseObject() {
            @Override
            public ContentValues databaseObject() {
                SimpleDateFormat fmt = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                ContentValues values = new ContentValues();
                values.put(AssignmentContract.AssignmentEntry.COLUMN_NAME_TITLE, "A1");
                values.put(AssignmentContract.AssignmentEntry.COLUMN_NAME_CRID, "CMPT 370");
                try {
                    values.put(AssignmentContract.AssignmentEntry.COLUMN_NAME_DEADLINE,
                            fmt.format(fmt.parse("2017-11-21 23:59:00")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return values;
            }
        };
        try {
            AssignmentTable.getInstance(c).remove(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            AssignmentTable.getInstance(c).insert(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert a ToDo/Task:
        // Insert a commitment:
        obj = new DatabaseObject() {
            @Override
            public ContentValues databaseObject() {
                ContentValues values = new ContentValues();
                values.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_TITLE, "Task 1");
                values.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_DESC, "First task");
                values.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_PRIO, 5 );
                values.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_TYPE,
                        TaskContract.TABLE_NAME);
                return values;
            }
        };
        try {
            CommitmentTable.getInstance(c).remove(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            CommitmentTable.getInstance(c).insert(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert a task:
        obj = new DatabaseObject() {
            @Override
            public ContentValues databaseObject() {
                ContentValues values = new ContentValues();
                SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm:ss");
                values.put(TaskContract.TaskEntry.COLUMN_NAME_TITLE, "Task 1");
                try {
                    values.put(TaskContract.TaskEntry.COLUMN_NAME_DATE,
                            dateFmt.format(dateFmt.parse("2017-11-10")));
                    values.put(TaskContract.TaskEntry.COLUMN_NAME_START,
                            timeFmt.format(timeFmt.parse("04:00:00")));
                    values.put(TaskContract.TaskEntry.COLUMN_NAME_END,
                            timeFmt.format(timeFmt.parse("04:30:00")));
                } catch(Exception e) {
                    e.printStackTrace();
                }
                return values;
            }
        };
        try {
            TaskTable.getInstance(c).remove(obj);
        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            TaskTable.getInstance(c).insert(obj);
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Print tables changed:
        Log.v(TAG, CommitmentTable.getInstance(c).toString());
        Log.v(TAG, CourseCommitmentTable.getInstance(c).toString());
        Log.v(TAG, AssignmentTable.getInstance(c).toString());
        Log.v(TAG, TaskTable.getInstance(c).toString());

    }
}
