package database.schema;

import android.provider.BaseColumns;

/**
 * Created by mahmudfasihulazam on 2017-10-12.
 * Schema for Course entity
 */

public class CourseContract {
    /**
     * Singleton constructor
     */
    private CourseContract() {
    }

    public static final String TABLE_NAME = "course";

    /**
     * Table creation command:
     */
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME + " (" +
                    CourseEntry.COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                    CourseEntry.COLUMN_NAME_TITLE + " TEXT," +
                    CourseEntry.COLUMN_NAME_YEAR + " INTEGER," +
                    CourseEntry.COLUMN_NAME_TARGET_GRADE + " REAL," +
                    CourseEntry.COLUMN_NAME_PROJ_GRADE + " REAL," +
                    CourseEntry.COLUMN_NAME_ACQ_GRADE + " REAL)";
    /**
     * Table deletion command:
     */
    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * Fields and table name
     */
    public static class CourseEntry implements BaseColumns {
        public static final String COLUMN_NAME_ID = "course_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_YEAR = "year";
        public static final String COLUMN_NAME_TARGET_GRADE = "target";
        public static final String COLUMN_NAME_PROJ_GRADE = "proj";
        public static final String COLUMN_NAME_ACQ_GRADE = "acq";
        // There is a mapping table to match courses with course commitments
    }

}
