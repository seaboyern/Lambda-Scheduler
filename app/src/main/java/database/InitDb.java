package database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.TreeMap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import database.schema.AssignmentContract;
import database.schema.CommitmentContract;
import database.schema.CourseCommitmentContract;
import database.schema.CourseContract;
import database.schema.ExamContract;
import database.schema.RecurringCommitmentContract;
import database.schema.SessionAttendeesContract;
import database.schema.SessionContract;
import database.schema.TaskContract;
import database.tables.AssignmentTable;
import database.tables.CommitmentTable;
import database.tables.CourseCommitmentTable;
import database.tables.RecurringCommitmentTable;
import database.tables.SessionAttendeesTable;
import database.tables.SessionTable;
import database.tables.TaskTable;
import database.tables.TestSessionTable;
import entities.Assignment;
import entities.Task;

/**
 * Created by mahmudfasihulazam on 2017-10-12.
 * Initialize the database with placeholder data
 */
public final class InitDb {
    private static final int NUM_TASKS = 5;
    public static String TAG = "InitDb";
    private InitDb(){}

    public static void initDb(Context c) {
        // Clear and recreate Tables:
//        DatabaseHelper db_helper = new DatabaseHelper(c);
//        SQLiteDatabase db = db_helper.getWritableDatabase();
//        db.execSQL(CourseContract.DELETE_TABLE);
//        db.execSQL(TaskContract.DELETE_TABLE);
//        db.execSQL(CommitmentContract.DELETE_TABLE);
//        db.execSQL(CourseCommitmentContract.DELETE_TABLE);
//        db.execSQL(AssignmentContract.DELETE_TABLE);
//        db.execSQL(ExamContract.DELETE_TABLE);
//        db.execSQL(RecurringCommitmentContract.DELETE_TABLE);
//        db.execSQL(SessionContract.DELETE_TABLE);
//        db.execSQL(SessionAttendeesContract.DELETE_TABLE);
//
//        db.execSQL(CourseContract.CREATE_TABLE);
//        db.execSQL(TaskContract.CREATE_TABLE);
//        db.execSQL(CommitmentContract.CREATE_TABLE);
//        db.execSQL(CourseCommitmentContract.CREATE_TABLE);
//        db.execSQL(AssignmentContract.CREATE_TABLE);
//        db.execSQL(ExamContract.CREATE_TABLE);
//        db.execSQL(RecurringCommitmentContract.CREATE_TABLE);
//        db.execSQL(SessionContract.CREATE_TABLE);
//        db.execSQL(SessionAttendeesContract.CREATE_TABLE);
//cl
//        db_helper.close();

        // Insert an assignment:
        // Insert commitment entry:
        Assignment a1 = new Assignment("A1", "First assignment", 1);
        a1.setCourseId("CMPT 370");
        a1.setWeight((float) 5.5);
        a1.setRequired((float) 5.5);
        a1.setAchieved((float) 5.5);
        SimpleDateFormat fmt = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        try {
            a1.setDeadline(fmt.parse("2017-11-11 23:59:00"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            CommitmentTable.getInstance(c).remove(a1);
            CourseCommitmentTable.getInstance(c).remove(a1);
            AssignmentTable.getInstance(c).remove(a1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            CommitmentTable.getInstance(c).insert(a1);
            CourseCommitmentTable.getInstance(c).insert(a1);
            AssignmentTable.getInstance(c).insert(a1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert a Task:
        // Insert a commitment:
        for(int i = 1; i <= NUM_TASKS; i++) {
            Task task1 = new Task("Task " + i, "A task",
                    (int)((Math.random() * 3) + 1));
            SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm:ss");
            try {
                task1.setDate(dateFmt.parse("2017-11-" + i));
                task1.setStart(timeFmt.parse("04:00:00"));
                task1.setEnd(timeFmt.parse("04:30:00"));
            } catch(Exception e) {
                e.printStackTrace();
            }

            try {
                CommitmentTable.getInstance(c).remove(task1);
                TaskTable.getInstance(c).remove(task1);
            } catch(Exception e) {
                e.printStackTrace();
            }
            try {
                CommitmentTable.getInstance(c).insert(task1);
                TaskTable.getInstance(c).insert(task1);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        // Task table queries:
        Log.d(TAG, "\n#####\n# Select task by title:");
        LinkedList<Task> list = TaskTable.getInstance(c).selectByTitle("Task 1");
        for(Task task : list) {
            Log.d(TAG, task.toString());
        }

        Log.d(TAG, "\n#####\n# Select task by date:");
        list = TaskTable.getInstance(c).selectByDate("2017-11-03");
        for(Task task : list) {
            Log.d(TAG, task.toString());
        }

        Log.d(TAG, "\n#####\n#Select all tasks sorted by priority:");
        TreeMap<Integer, LinkedList<Task>> prioMap = TaskTable.getInstance(c).priorityMap();
        for(Integer i : prioMap.keySet()) {
            LinkedList<Task> l = prioMap.get(i);
            for(Task j : l) {
                Log.d(TAG, j.toString());
            }
        }

        try {
            TestSessionTable.testSessionTable(c);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Print tables changed:
        Log.d(TAG, CommitmentTable.getInstance(c).toString());
        Log.d(TAG, RecurringCommitmentTable.getInstance(c).toString());
        Log.d(TAG, CourseCommitmentTable.getInstance(c).toString());
        Log.d(TAG, AssignmentTable.getInstance(c).toString());
        Log.d(TAG, TaskTable.getInstance(c).toString());
        Log.d(TAG, SessionTable.getInstance(c).toString());
        Log.d(TAG, SessionAttendeesTable.getInstance(c).toString());

    }

}
