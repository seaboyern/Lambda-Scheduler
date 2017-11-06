package database.tables;

import android.content.ContentValues;
import android.content.Context;

import database.DatabaseHelper;
import database.schema.CommitmentContract;

/**
 * Created by mahmudfasihulazam on 2017-11-05.
 */

public class CommitmentTable extends Table {
    private static CommitmentTable instance;

    private CommitmentTable(Context c) {
        super(c);
        this.tableName = CommitmentContract.TABLE_NAME;
        this.primaryKeyName = CommitmentContract.CommitmentEntry.COLUMN_NAME_TITLE;
    }

    public static Table getInstance(Context c) {
        if(instance == null) {
            instance = new CommitmentTable(c);
        }
        return (Table)instance;
    }

    @Override
    public ContentValues query(String q) {
        return null;
    }

}
