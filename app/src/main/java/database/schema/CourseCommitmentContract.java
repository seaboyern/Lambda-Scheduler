package database.schema;

import android.provider.BaseColumns;

/**
 * Created by mahmudfasihulazam on 2017-11-05.
 */

public final class CourseCommitmentContract {
    /**
     * Singleton constructor
     */
    private CourseCommitmentContract() {}

    public static final String TABLE_NAME = "course_commitment";

    /**
     * Table creation command:
     */
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME + " (" +
                    CourseCommitmentEntry.COLUMN_NAME_TITLE + " TEXT," +
                    CourseCommitmentEntry.COLUMN_NAME_CRID + " TEXT," +
                    CourseCommitmentEntry.COLUMN_NAME_WT + " FLOAT," +
                    CourseCommitmentEntry.COLUMN_NAME_REQ + " FLOAT," +
                    CourseCommitmentEntry.COLUMN_NAME_ACH + " FLOAT," +
                    CourseCommitmentEntry.COLUMN_NAME_TYPE + " TEXT," +
                    "PRIMARY KEY ("
                        + CourseCommitmentEntry.COLUMN_NAME_CRID + ","
                        + CourseCommitmentEntry.COLUMN_NAME_TITLE
                    + "))";
    /**
     * Table deletion command:
     */
    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final class CourseCommitmentEntry implements BaseColumns {
        private CourseCommitmentEntry() {}

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CRID = "course_id";
        public static final String COLUMN_NAME_WT = "weight";
        public static final String COLUMN_NAME_REQ = "required";
        public static final String COLUMN_NAME_ACH = "achieved";
        public static final String COLUMN_NAME_TYPE = "type";
    }

}
