package database.tables;

import android.content.ContentValues;
import android.content.Context;

import database.DatabaseObject;
import database.schema.AssignmentContract;
import database.schema.CourseCommitmentContract;
import entities.Assignment;
import entities.CourseCommitment;

/**
 * Created by mahmudfasihulazam on 2017-11-05.
 */

public class CourseCommitmentTable extends Table {

    private static CourseCommitmentTable instance;

    private CourseCommitmentTable(Context c) {
        super(c);
        this.tableName = CourseCommitmentContract.TABLE_NAME;
        // Create a mapping for all allowable types:
        typeMap.put(Assignment.class.getName(), AssignmentContract.TABLE_NAME);
    }

    @Override
    public synchronized void insert(DatabaseObject record) {
        CourseCommitment comm = (CourseCommitment)record;
        ContentValues crsCommValues = new ContentValues();

        crsCommValues.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_TITLE,
                comm.getTitle());
        crsCommValues.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_CRID,
                comm.getCourseId());
        crsCommValues.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_WT,
                comm.getWeight());
        crsCommValues.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_REQ,
                comm.getRequired());
        crsCommValues.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_ACH,
                comm.getAchieved());
        crsCommValues.put(CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_TYPE,
                getTypeName(record));

        super.rawInsert(crsCommValues);
    }

    public synchronized static Table getInstance(Context c) {
        if(instance == null) {
            instance = new CourseCommitmentTable(c);
        }
        return (Table)instance;
    }

    @Override
    protected String removeQuery(DatabaseObject record) {
        CourseCommitment crsComm = (CourseCommitment)record;
        return "DELETE FROM " + CourseCommitmentContract.TABLE_NAME + " WHERE "
                + CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_TITLE + " = '"
                + crsComm.getTitle()
                + "' AND "
                + CourseCommitmentContract.CourseCommitmentEntry.COLUMN_NAME_CRID + " = '"
                + crsComm.getCourseId()
                + "';";
    }

}
