package database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.lambda.lambdaorganizer.FormatDateTime;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import adapters.StudyEventToSessionAdapter;
import database.DatabaseObject;
import database.schema.AssignmentContract;
import database.schema.CommitmentContract;
import database.schema.CourseCommitmentContract;
import database.schema.RecurringCommitmentContract;
import database.schema.TaskContract;
import entities.Assignment;
import entities.Commitment;
import entities.CourseCommitment;
import entities.Session;
import entities.Task;
import entities.interfaces.CommitmentInterface;

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
        typeMap.put(Assignment.class.getName(), CourseCommitmentContract.TABLE_NAME);
        typeMap.put(Task.class.getName(), TaskContract.TABLE_NAME);
        typeMap.put(Session.class.getName(), RecurringCommitmentContract.TABLE_NAME);
        typeMap.put(StudyEventToSessionAdapter.class.getName(),
                RecurringCommitmentContract.TABLE_NAME);
    }

    @Override
    public synchronized void insert(DatabaseObject record) {
        CommitmentInterface comm = (CommitmentInterface)record;
        ContentValues commitmentValues = new ContentValues();

        commitmentValues.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_TITLE, comm.getTitle());
        commitmentValues.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_DATE,
                FormatDateTime.getDateTimeStringFromDate(comm.getSequencingDateTime()));
        commitmentValues.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_DESC, comm.getDesc());
        commitmentValues.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_GOOGLE_ID,
                comm.getGoogleId());
        commitmentValues.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_PRIO,
                comm.getPrio());
        commitmentValues.put(CommitmentContract.CommitmentEntry.COLUMN_NAME_TYPE,
                getTypeName(record));

        super.rawInsert(commitmentValues);
    }

    public synchronized static CommitmentTable getInstance(Context c) {
        if(instance == null) {
            instance = new CommitmentTable(c);
        }
        return instance;
    }

    public LinkedList<Commitment> selectByDate(Date date) {
        LinkedList<Task> tasks = TaskTable.getInstance(this.context).selectByDate(
                FormatDateTime.getDateStringFromDate(date)
        );
        LinkedList<Assignment> assignments =
                AssignmentTable.getInstance(this.context).selectByDeadlineDate(date);
        LinkedList<Session> sessions =
                SessionTable.getInstance(this.context).selectByNextDate(date);
        LinkedList<Commitment> commitments = new LinkedList<Commitment>();
        if(null != tasks) commitments.addAll(tasks);
        else Log.d(this.getTableName(), "NULL tasks list");
        if(null != sessions) commitments.addAll(sessions);
        else Log.d(this.getTableName(), "NULL sessions list");
        if(null != assignments) commitments.addAll(assignments);
        else Log.d(this.getTableName(), "NULL assignments list");
        return commitments;
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
