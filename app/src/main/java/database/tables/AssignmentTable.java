package database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.lambda.lambdaorganizer.FormatDateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import database.DatabaseHelper;
import database.DatabaseObject;
import database.schema.AssignmentContract;
import database.schema.CommitmentContract;
import database.schema.CourseCommitmentContract;
import entities.Assignment;

/**
 * Created by mahmudfasihulazam on 2017-11-05.
 */

public class AssignmentTable extends Table {
    private static AssignmentTable instance;

    private static String joinCrsCommLike = String.format(Table.uniDimLikeQuery,
            AssignmentContract.TABLE_NAME + " p", CourseCommitmentContract.TABLE_NAME + " q",
            "p", AssignmentContract.AssignmentEntry.COLUMN_NAME_TITLE,
            "q", AssignmentContract.AssignmentEntry.COLUMN_NAME_TITLE,
            "p", "%s", "%s");
    private static String joinAllLike = String.format(joinTwo,
            String.format("(%s) r", joinCrsCommLike), CommitmentContract.TABLE_NAME + " s",
            "r", CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_TITLE,
            "s", CommitmentContract.CommitmentEntry.COLUMN_NAME_TITLE);

    private static String joinCrsCommExact = String.format(Table.uniDimQuery,
            AssignmentContract.TABLE_NAME + " p", CourseCommitmentContract.TABLE_NAME + " q",
            "p", AssignmentContract.AssignmentEntry.COLUMN_NAME_TITLE,
            "q", AssignmentContract.AssignmentEntry.COLUMN_NAME_TITLE,
            "p", "%s", "%s");
    private static String joinAllExact = String.format(joinTwo,
            String.format("(%s) r", joinCrsCommExact), CommitmentContract.TABLE_NAME + " s",
            "r", CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_TITLE,
            "s", CommitmentContract.CommitmentEntry.COLUMN_NAME_TITLE);

    private AssignmentTable(Context c) {
        super(c);
        this.tableName = AssignmentContract.TABLE_NAME;
    }

    @Override
    public synchronized void insert(DatabaseObject record) {
        Assignment a = (Assignment)record;
        ContentValues aValues = new ContentValues();

        aValues.put(AssignmentContract.AssignmentEntry.COLUMN_NAME_TITLE, a.getTitle());
        aValues.put(AssignmentContract.AssignmentEntry.COLUMN_NAME_CRID, a.getCourseId());
        aValues.put(AssignmentContract.AssignmentEntry.COLUMN_NAME_DEADLINE,
                FormatDateTime.getDateTimeStringFromDate(a.getDeadline()));

        super.rawInsert(aValues);
    }

    public synchronized static AssignmentTable getInstance(Context c) {
        if(instance == null) {
            instance = new AssignmentTable(c);
        }
        return instance;
    }

    private Assignment getFromCursor(Cursor cur) {
        String title = cur.getString(
                cur.getColumnIndex(AssignmentContract.AssignmentEntry.COLUMN_NAME_TITLE));
        String desc = cur.getString(
                cur.getColumnIndex(CommitmentContract.CommitmentEntry.COLUMN_NAME_DESC));
        int prio = cur.getInt(
                cur.getColumnIndex(CommitmentContract.CommitmentEntry.COLUMN_NAME_PRIO));
        String googleId = cur.getString(
                cur.getColumnIndex(CommitmentContract.CommitmentEntry.COLUMN_NAME_GOOGLE_ID));
        String deadline = cur.getString(
                cur.getColumnIndex(AssignmentContract.AssignmentEntry.COLUMN_NAME_DEADLINE));
        String crid = cur.getString(
                cur.getColumnIndex(AssignmentContract.AssignmentEntry.COLUMN_NAME_CRID));
        float weight = cur.getFloat(
                cur.getColumnIndex(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_WT));
        float req = cur.getFloat(
                cur.getColumnIndex(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_REQ));
        float achieved = cur.getFloat(
                cur.getColumnIndex(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_ACH));

        Assignment a = new Assignment(title, desc, prio);
        a.setGoogleId(googleId);
        try {
            a.setDeadline(FormatDateTime.getDateFromString(deadline));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        a.setCourseId(crid);
        a.setWeight(weight);
        a.setRequired(req);
        a.setAchieved(achieved);
        return a;
    }

    private LinkedList<Assignment> getFromQuery(String query) {
        DatabaseHelper db = new DatabaseHelper(this.context);
        Cursor cur = db.query(query);
        LinkedList<Assignment> list = null;
        if(cur.moveToFirst()) {
            list = new LinkedList<>();
            do {
                list.add(getFromCursor(cur));
            } while(cur.moveToNext());
        }
        db.close();
        cur.close();
        return list;
    }

    public Assignment selectByTitle(String title) {
        String query = String.format(joinAllExact,
                AssignmentContract.AssignmentEntry.COLUMN_NAME_TITLE,
                title);
        return getFromQuery(query).getFirst();
    }

    public LinkedList<Assignment> selectByDeadlineDate(Date date) {
        String query = String.format(joinAllLike,
                AssignmentContract.AssignmentEntry.COLUMN_NAME_DEADLINE,
                FormatDateTime.getDateStringFromDate(date) + "%");
        return getFromQuery(query);
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
