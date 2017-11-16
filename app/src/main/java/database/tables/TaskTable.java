package database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import database.DatabaseHelper;
import database.DatabaseObject;
import database.schema.CommitmentContract;
import database.schema.TaskContract;
import entities.Task;

/**
 * Created by mahmudfasihulazam on 2017-11-06.
 */

public class TaskTable extends Table {
    /**
     * Singleton instance
     */
    private static TaskTable instance;

    /**
     * Generic unidimensional query strings
     */
    private static String uniDimQuery =
            String.format(
                    "SELECT * FROM %s\nJOIN %s\nON %s.%s = %s.%s\nWHERE %s.%s = '%s';",
                    TaskContract.TABLE_NAME, CommitmentContract.TABLE_NAME,
                    TaskContract.TABLE_NAME, TaskContract.TaskEntry.COLUMN_NAME_TITLE,
                    CommitmentContract.TABLE_NAME,
                        CommitmentContract.CommitmentEntry.COLUMN_NAME_TITLE,
                    TaskContract.TABLE_NAME, "%s", "%s"
            );
    /*
    Example :
        SELECT * FROM task
        JOIN commitment
        ON task.title = commitment.title
        WHERE task.title = 'Task 1';
    */

    private TaskTable(Context c) {
        super(c);
        this.tableName = TaskContract.TABLE_NAME;
    }

    public static TaskTable getInstance(Context c) {
        if(instance == null) {
            instance = new TaskTable(c);
        }
        return instance;
    }

    @Override
    public void insert(DatabaseObject record) {
        Task task = (Task)record;
        ContentValues taskValues = new ContentValues();

        taskValues.put(TaskContract.TaskEntry.COLUMN_NAME_TITLE, task.getTitle());
        taskValues.put(TaskContract.TaskEntry.COLUMN_NAME_DATE,
                new SimpleDateFormat("yyyy-MM-dd").format(task.getDate()));
        taskValues.put(TaskContract.TaskEntry.COLUMN_NAME_START,
                new SimpleDateFormat("HH:mm:ss").format(task.getStart()));
        taskValues.put(TaskContract.TaskEntry.COLUMN_NAME_END,
                new SimpleDateFormat("HH:mm:ss").format(task.getEnd()));

        super.rawInsert(taskValues);
    }

    @Override
    protected String removeQuery(DatabaseObject record) {
        Task task = (Task)record;
        return "DELETE FROM " + TaskContract.TABLE_NAME + " WHERE "
                + TaskContract.TaskEntry.COLUMN_NAME_TITLE + " = '"
                + task.getTitle()
                + "' AND "
                + TaskContract.TaskEntry.COLUMN_NAME_DATE + " = '"
                + new SimpleDateFormat("yyyy-MM-dd").format(task.getDate())
                + "' AND "
                + TaskContract.TaskEntry.COLUMN_NAME_START + " = '"
                + new SimpleDateFormat("HH:mm:ss").format(task.getStart())
                + "';";
    }

    private Task getTaskFromCursor(Cursor cur) {
        Task task = new Task(
                cur.getString(cur.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_TITLE)),
                cur.getString(cur.getColumnIndex(
                        CommitmentContract.CommitmentEntry.COLUMN_NAME_DESC)),
                cur.getInt(cur.getColumnIndex(CommitmentContract.CommitmentEntry.COLUMN_NAME_PRIO))
        );
        try {
            task.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(
                    cur.getString(cur.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_DATE))
            ));
            task.setStart(new SimpleDateFormat("HH:mm:ss").parse(
                    cur.getString(cur.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_START))
            ));
            task.setEnd(new SimpleDateFormat("HH:mm:ss").parse(
                    cur.getString(cur.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_END))
            ));
            return task;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private LinkedList<Task> getTasksFromQuery(String query) {
        DatabaseHelper db = new DatabaseHelper(this.context);
        Cursor cur = db.query(query);
        LinkedList<Task> list = null;
        if(cur.moveToFirst()) {
            list = new LinkedList<Task>();
            do {
                list.add(getTaskFromCursor(cur));
            } while (cur.moveToNext());
        }
        cur.close();
        db.close();
        return list;
    }

    public LinkedList<Task> selectByTitle(String title) {
        String titleQuery = String.format(uniDimQuery,
                TaskContract.TaskEntry.COLUMN_NAME_TITLE, title
        );
        // Log.v(this.getTableName(), titleQuery);

        return getTasksFromQuery(titleQuery);
    }

    public LinkedList<Task> selectByDate(String date) {
        String dateQuery = String.format(uniDimQuery,
                TaskContract.TaskEntry.COLUMN_NAME_DATE, date
        );
        // Log.v(this.getTableName(), titleQuery);

        return getTasksFromQuery(dateQuery);
    }

}
