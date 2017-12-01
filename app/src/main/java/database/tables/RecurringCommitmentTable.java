package database.tables;

import android.content.ContentValues;
import android.content.Context;

import database.DatabaseObject;
import database.schema.RecurringCommitmentContract;
import database.schema.SessionContract;
import entities.Commitment;
import entities.Session;
import entities.interfaces.RecurringCommitment;

/**
 * Created by mahmudfasihulazam on 2017-11-30.
 */

public class RecurringCommitmentTable extends Table {

    private static RecurringCommitmentTable instance;

    protected RecurringCommitmentTable(Context c) {
        super(c);
        this.tableName = RecurringCommitmentContract.TABLE_NAME;
        typeMap.put(Session.class.getName(), SessionContract.TABLE_NAME);
    }

    public static RecurringCommitmentTable getInstance(Context c) {
        if(instance == null) {
            instance = new RecurringCommitmentTable(c);
        }
        return instance;
    }

    @Override
    public void insert(DatabaseObject record) {
        RecurringCommitment recComm = (RecurringCommitment)record;
        ContentValues values = new ContentValues();
        values.put(RecurringCommitmentContract.RecurringCommitmentEntry.COLUMN_NAME_TITLE,
                recComm.getTitle());
        values.put(RecurringCommitmentContract.RecurringCommitmentEntry.COLUMN_NAME_TYPE,
                getTypeName(record));
        values.put(RecurringCommitmentContract.RecurringCommitmentEntry.COLUMN_NAME_COUNT,
                recComm.getRecCount());
        values.put(RecurringCommitmentContract.RecurringCommitmentEntry.COLUMN_NAME_FREQ,
                recComm.getRecFreq());
        super.rawInsert(values);
    }

    @Override
    protected String removeQuery(DatabaseObject record) {
        String title = ((Commitment)record).getTitle();
        return "DELETE FROM " + RecurringCommitmentContract.TABLE_NAME + " WHERE "
                + RecurringCommitmentContract.RecurringCommitmentEntry.COLUMN_NAME_TITLE + " = '"
                + title
                + "' AND "
                + RecurringCommitmentContract.RecurringCommitmentEntry.COLUMN_NAME_TYPE + " = '"
                + getTypeName(record)
                + "';";
    }
}
