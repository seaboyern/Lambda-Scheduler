package database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.lambda.lambdaorganizer.FormatDateTime;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import database.DatabaseHelper;
import database.DatabaseObject;
import database.schema.CommitmentContract;
import database.schema.RecurringCommitmentContract;
import database.schema.SessionAttendeesContract;
import database.schema.SessionContract;
import entities.Session;
import entities.interfaces.SessionInterface;

/**
 * Created by mahmudfasihulazam on 2017-11-30.
 */

public class SessionTable extends Table {

    private static String joinCommitment =
            String.format(
                    Table.uniDimQuery, //"SELECT * FROM %s\nJOIN %s\nON %s.%s = %s.%s\nWHERE %s.%s = '%s';"
                    SessionContract.TABLE_NAME, CommitmentContract.TABLE_NAME,
                    SessionContract.TABLE_NAME, SessionContract.SessionEntry.COLUMN_NAME_TITLE,
                    CommitmentContract.TABLE_NAME,
                        CommitmentContract.CommitmentEntry.COLUMN_NAME_TITLE,
                    SessionContract.TABLE_NAME,
                    "%s", "%s"
            );

    private static String joinTwo = "SELECT * FROM %s\n" +
            "JOIN %s\n" +
            "ON %s.%s = %s.%s";

    private static String joinRecComm =
            String.format(
                    joinTwo,
                    "(%s) p" /* Other table */, RecurringCommitmentContract.TABLE_NAME, // rec_comm
                    RecurringCommitmentContract.TABLE_NAME, "%s", // rec_comm.<field> =
                    "p", "%s" // p.<field>
            );

    private static String selectAllQuery =
            String.format(
                    Table.selectAllQuery,
                    SessionContract.TABLE_NAME, CommitmentContract.TABLE_NAME,
                    SessionContract.TABLE_NAME, SessionContract.SessionEntry.COLUMN_NAME_TITLE,
                    CommitmentContract.TABLE_NAME,
                    CommitmentContract.CommitmentEntry.COLUMN_NAME_TITLE,
                    SessionContract.TABLE_NAME
            );

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
        SessionInterface s = (SessionInterface)record;
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
        CommitmentTable.getInstance(this.context).insert(record);
        RecurringCommitmentTable.getInstance(this.context).insert(record);

        super.rawInsert(values);

    }

    @Override
    protected String removeQuery(DatabaseObject record) {
        return null;
    }

    private LinkedList<String> getAttendeesFromCursor(Cursor cur) {
        LinkedList<String> result = new LinkedList<>();
        String query = "SELECT * FROM " + SessionAttendeesContract.TABLE_NAME + " p WHERE p."
                + SessionAttendeesContract.SessionAttendeesEntry.COLUMN_NAME_SESSION + " = '"
                + cur.getString(cur.getColumnIndex(SessionContract.SessionEntry.COLUMN_NAME_TITLE))
                + "'";
        DatabaseHelper db = new DatabaseHelper(this.context);
        Cursor cur2 = db.query(query);
        if(cur2.moveToFirst()) {
            do {
                result.add(cur2.getString(cur2.getColumnIndex(
                        SessionAttendeesContract.SessionAttendeesEntry.COLUMN_NAME_EMAIL)));
            } while(cur2.moveToNext());
        }
        return result;
    }

    private Session getSessionFromCursor(Cursor cur) {
        String title = cur.getString(
                cur.getColumnIndex(SessionContract.SessionEntry.COLUMN_NAME_TITLE));
        String desc = cur.getString(
                cur.getColumnIndex(CommitmentContract.CommitmentEntry.COLUMN_NAME_DESC));
        int prio = cur.getInt(
                cur.getColumnIndex(CommitmentContract.CommitmentEntry.COLUMN_NAME_PRIO));
        String googleId = cur.getString(
                cur.getColumnIndex(CommitmentContract.CommitmentEntry.COLUMN_NAME_GOOGLE_ID));
        String start = cur.getString(
                cur.getColumnIndex(SessionContract.SessionEntry.COLUMN_NAME_START));
        String end = cur.getString(
                cur.getColumnIndex(SessionContract.SessionEntry.COLUMN_NAME_END));
        String timeZone = cur.getString(
                cur.getColumnIndex(SessionContract.SessionEntry.COLUMN_NAME_TIME_ZONE));
        String recFreq = cur.getString(
                cur.getColumnIndex(RecurringCommitmentContract.RecurringCommitmentEntry.COLUMN_NAME_FREQ));
        int recCount = cur.getInt(
                cur.getColumnIndex(RecurringCommitmentContract.RecurringCommitmentEntry.COLUMN_NAME_COUNT));
        Session s = new Session(title, desc, prio);
        s.setGoogleId(googleId);
        try {
            s.setStart(FormatDateTime.getDateFromString(start));
            s.setEnd(FormatDateTime.getDateFromString(end));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        s.setTimeZone(timeZone);
        s.setRecFreq(recFreq);
        s.setRecCount(recCount);
        LinkedList<String> attendeeEmails = getAttendeesFromCursor(cur);
        s.setAttendeesEmail(attendeeEmails);
        return s;
    }

    private LinkedList<Session> getSessionsFromQuery(String query) {
        DatabaseHelper db = new DatabaseHelper(this.context);
        Cursor cur = db.query(query);
        LinkedList<Session> list = null;
        if(cur.moveToFirst()) {
            list = new LinkedList<Session>();
            do {
                list.add(getSessionFromCursor(cur));
            } while (cur.moveToNext());
        } else {
            Log.d(this.getTableName(), "Cursor did not move to first");
        }
        db.close();
        cur.close();
        return list;
    }

    public Session selectByTitle(String title) {
        String commJoin = String.format(joinCommitment,
                SessionContract.SessionEntry.COLUMN_NAME_TITLE, title);
        String recJoin = String.format(joinRecComm, commJoin,
                RecurringCommitmentContract.RecurringCommitmentEntry.COLUMN_NAME_TITLE,
                SessionContract.SessionEntry.COLUMN_NAME_TITLE) + ";";
        LinkedList<Session> l = this.getSessionsFromQuery(recJoin);
        Session result = (l != null && !l.isEmpty())
                ? l.getFirst() // found
                : null; // not found
        return result;
    }

    public LinkedList<Session> selectByStart(Date start) {
        String startString = FormatDateTime.getDateTimeStringFromDate(start);
        String commJoin = String.format(joinCommitment,
                SessionContract.SessionEntry.COLUMN_NAME_START, startString);
        String recJoin = String.format(joinRecComm, commJoin,
                RecurringCommitmentContract.RecurringCommitmentEntry.COLUMN_NAME_TITLE,
                SessionContract.SessionEntry.COLUMN_NAME_TITLE) + ";";
        LinkedList<Session> result = this.getSessionsFromQuery(recJoin);
        return result;
    }
}
