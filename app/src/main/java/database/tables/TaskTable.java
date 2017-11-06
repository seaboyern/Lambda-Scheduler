package database.tables;

import android.content.ContentValues;
import android.content.Context;

import database.schema.TaskContract;

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
    protected String removeQuery(ContentValues values) {
        return "DELETE FROM " + TaskContract.TABLE_NAME + " WHERE "
                + TaskContract.TaskEntry.COLUMN_NAME_TITLE + " = '"
                + values.get(TaskContract.TaskEntry.COLUMN_NAME_TITLE)
                + "' AND "
                + TaskContract.TaskEntry.COLUMN_NAME_DATE + " = '"
                + values.get(TaskContract.TaskEntry.COLUMN_NAME_DATE)
                + "' AND "
                + TaskContract.TaskEntry.COLUMN_NAME_START + " = '"
                + values.get(TaskContract.TaskEntry.COLUMN_NAME_START)
                + "';";
    }
}
