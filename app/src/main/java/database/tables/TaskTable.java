package database.tables;

import android.content.ContentValues;
import android.content.Context;

import java.text.SimpleDateFormat;

import database.DatabaseObject;
import database.schema.TaskContract;
import entities.Task;

/**
 * Created by mahmudfasihulazam on 2017-11-06.
 */

public class TaskTable extends Table {
    private static TaskTable instance;

    private TaskTable(Context c) {
        super(c);
        this.tableName = TaskContract.TABLE_NAME;
    }

    public static Table getInstance(Context c) {
        if(instance == null) {
            instance = new TaskTable(c);
        }
        return (Table)instance;
    }

    @Override
    public ContentValues query(String q) {
        return null;
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
}
