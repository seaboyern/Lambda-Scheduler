package database.tables;

import android.content.ContentValues;
import android.content.Context;

import database.DatabaseHelper;
import database.schema.CourseCommitmentContract;

/**
 * Created by mahmudfasihulazam on 2017-11-05.
 */

public class CourseCommitmentTable extends Table {
    private static CourseCommitmentTable instance;

    private CourseCommitmentTable(Context c) {
        super(c);
        this.tableName = CourseCommitmentContract.TABLE_NAME;
        this.primaryKeyName = CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_TITLE;
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


}
