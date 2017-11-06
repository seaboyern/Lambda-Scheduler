package database.tables;

import android.content.ContentValues;
import android.content.Context;

import database.schema.CourseCommitmentContract;

/**
 * Created by mahmudfasihulazam on 2017-11-05.
 */

public class CourseCommitmentTable extends Table {
    private static CourseCommitmentTable instance;

    private CourseCommitmentTable(Context c) {
        super(c);
        this.tableName = CourseCommitmentContract.TABLE_NAME;
    }

    public static Table getInstance(Context c) {
        if(instance == null) {
            instance = new CourseCommitmentTable(c);
        }
        return (Table)instance;
    }

    @Override
    public ContentValues query(String q) {
        return null;
    }

    @Override
    protected String removeQuery(ContentValues values) {
        return "DELETE FROM " + CourseCommitmentContract.TABLE_NAME + " WHERE "
                + CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_TITLE + " = '"
                + values.get(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_TITLE)
                + "' AND "
                + CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_CRID + " = '"
                + values.get(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_CRID)
                + "';";
    }

}
