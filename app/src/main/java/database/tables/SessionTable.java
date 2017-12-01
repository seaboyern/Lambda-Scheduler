package database.tables;

import android.content.ContentValues;
import android.content.Context;

import com.example.lambda.lambdaorganizer.FormatDateTime;

import java.util.List;

import database.DatabaseObject;
import database.schema.SessionContract;
import entities.Session;

/**
 * Created by mahmudfasihulazam on 2017-11-30.
 */

public class SessionTable extends Table {

    private static SessionTable instance;

    protected SessionTable(Context c) {
        super(c);
        this.tableName = SessionContract.TABLE_NAME;
    }

    public static SessionTable getInstance(Context c) {
        if(instance == null) {
            instance = new SessionTable(c);
        }
        return instance;
    }

    @Override
    public void insert(DatabaseObject record) {
        Session s = (Session)record;
        ContentValues values = new ContentValues();

        values.put(SessionContract.SessionEntry.COLUMN_NAME_TITLE, s.getTitle());
        values.put(SessionContract.SessionEntry.COLUMN_NAME_START,
                FormatDateTime.getDateTimeStringFromDate(s.getStart()));
        values.put(SessionContract.SessionEntry.COLUMN_NAME_END,
                FormatDateTime.getDateTimeStringFromDate(s.getEnd()));
        values.put(SessionContract.SessionEntry.COLUMN_NAME_TIME_ZONE,
                s.getTimeZone());

        List<String> attendees = s.getAtendeesEmail();
        for(String email : attendees) {
            SessionAttendeesTable.getInstance(this.context).insert(email, s.getTitle());
        }
        CommitmentTable.getInstance(this.context).insert(s);
        if(0 != s.getRecCount()) RecurringCommitmentTable.getInstance(this.context).insert(s);

        super.rawInsert(values);

    }

    @Override
    protected String removeQuery(DatabaseObject record) {
        return null;
    }
}
