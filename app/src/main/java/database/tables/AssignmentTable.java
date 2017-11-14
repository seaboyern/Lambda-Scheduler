package database.tables;

import android.content.ContentValues;
import android.content.Context;

import java.util.Map;
import java.util.Set;

import database.DatabaseHelper;
import database.schema.AssignmentContract;

/**
 * Created by mahmudfasihulazam on 2017-11-05.
 */

public class AssignmentTable extends Table {
    private static AssignmentTable instance;

    private AssignmentTable(Context c) {
        super(c);
        this.tableName = AssignmentContract.TABLE_NAME;
    }

    public static Table getInstance(Context c) {
        if(instance == null) {
            instance = new AssignmentTable(c);
        }
        return (Table)instance;
    }

    @Override
    public ContentValues query(String q) {
        return null;
    }

    @Override
    protected String removeQuery(ContentValues values) {
        return "DELETE FROM " + AssignmentContract.TABLE_NAME + " WHERE "
                + AssignmentContract.AssignmentEntry.COLUMN_NAME_TITLE + " = '"
                + values.get(AssignmentContract.AssignmentEntry.COLUMN_NAME_TITLE)
                + "' AND "
                + AssignmentContract.AssignmentEntry.COLUMN_NAME_CRID + " = '"
                + values.get(AssignmentContract.AssignmentEntry.COLUMN_NAME_CRID)
                + "';";
    }

}
