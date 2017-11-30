package database.tables;

import android.content.ContentValues;
import android.content.Context;

import java.util.HashMap;
import java.util.LinkedList;

import database.DatabaseObject;
import database.schema.CommitmentContract;
import database.schema.CourseCommitmentContract;
import database.schema.TaskContract;
import entities.Assignment;
import entities.Commitment;
import entities.CourseCommitment;
import entities.Task;

/**
 * Created by mahmudfasihulazam on 2017-11-05.
 */

public class CommitmentTable extends Table {

    private static CommitmentTable instance;

    private CommitmentTable(Context c) {
        super(c);
        this.tableName = CommitmentContract.TABLE_NAME;
        // Create a mapping for allowable types:
        typeMap = new HashMap<String, String>();
        typeMap.put(CourseCommitment.class.getName(), CourseCommitmentContract.TABLE_NAME);
        typeMap.put(Task.class.getName(), TaskContract.TABLE_NAME);
        typeMap.put(Assignment.class.getName(), CourseCommitmentContract.TABLE_NAME);
    }

    @Override
    public synchronized void insert(DatabaseObject record) {
        Commitment comm = (Commitment)record;
        ContentValues commitmentValues = new ContentValues();

        commitmentValues.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_TITLE, comm.getTitle());
        commitmentValues.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_GOOGLE_ID,
                comm.getGoogleId());
        commitmentValues.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_DESC, comm.getDesc());
        commitmentValues.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_PRIO,
                comm.getPrio());
        commitmentValues.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_TYPE,
                getTypeName(record));

        super.rawInsert(commitmentValues);
    }

    public synchronized static Table getInstance(Context c) {
        if(instance == null) {
            instance = new CommitmentTable(c);
        }
        return (Table)instance;
    }

    @Override
    protected String removeQuery(DatabaseObject record) {
        Commitment comm = (Commitment)record;
        return "DELETE FROM " + CommitmentContract.TABLE_NAME + " WHERE "
                + CommitmentContract.CommitmentEntry.COLUMN_NAME_TITLE + " = '"
                + comm.getTitle()
                + "' AND "
                + CommitmentContract.CommitmentEntry.COLUMN_NAME_TYPE + " = '"
                + getTypeName(record)
                + "';";
    }

}
