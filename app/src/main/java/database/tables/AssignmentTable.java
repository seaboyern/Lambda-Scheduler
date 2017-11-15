package database.tables;

import android.content.ContentValues;
import android.content.Context;

import java.text.SimpleDateFormat;

import database.DatabaseObject;
import database.schema.AssignmentContract;
import entities.Assignment;

/**
 * Created by mahmudfasihulazam on 2017-11-05.
 */

public class AssignmentTable extends Table {
    private static AssignmentTable instance;

    private AssignmentTable(Context c) {
        super(c);
        this.tableName = AssignmentContract.TABLE_NAME;
    }

    @Override
    public void insert(DatabaseObject record) {
        Assignment a = (Assignment)record;
        ContentValues aValues = new ContentValues();

        aValues.put(AssignmentContract.AssignmentEntry.COLUMN_NAME_TITLE, a.getTitle());
        aValues.put(AssignmentContract.AssignmentEntry.COLUMN_NAME_CRID, a.getCourseId());
        aValues.put(AssignmentContract.AssignmentEntry.COLUMN_NAME_DEADLINE,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(a.getDeadline()));

        super.rawInsert(aValues);
    }

    public static Table getInstance(Context c) {
        if(instance == null) {
            instance = new AssignmentTable(c);
        }
        return (Table)instance;
    }

    @Override
    protected String removeQuery(DatabaseObject record) {
        Assignment a = (Assignment)record;
        return "DELETE FROM " + AssignmentContract.TABLE_NAME + " WHERE "
                + AssignmentContract.AssignmentEntry.COLUMN_NAME_TITLE + " = '"
                + a.getTitle()
                + "' AND "
                + AssignmentContract.AssignmentEntry.COLUMN_NAME_CRID + " = '"
                + a.getCourseId()
                + "';";
    }

}
