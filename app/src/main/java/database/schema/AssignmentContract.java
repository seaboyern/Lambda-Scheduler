package database.schema;

import android.provider.BaseColumns;

/**
 * Created by mahmudfasihulazam on 2017-11-05.
 */

public final class AssignmentContract {
    private AssignmentContract() {}

    public static final String TABLE_NAME = "assignment";

    /**
     * Table creation command:
     */
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME + " (" +
                    AssignmentEntry.COLUMN_NAME_TITLE + " TEXT," +
                    AssignmentEntry.COLUMN_NAME_CRID + " TEXT," +
                    AssignmentEntry.COLUMN_NAME_DEADLINE + " TEXT," +
                    "PRIMARY KEY ("
                        + AssignmentEntry.COLUMN_NAME_CRID + ","
                        + AssignmentEntry.COLUMN_NAME_TITLE
                    + "))";
    /**
     * Table deletion command:
     */
    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final class AssignmentEntry implements BaseColumns {
        private AssignmentEntry() {}

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CRID = "course_id";
        public static final String COLUMN_NAME_DEADLINE = "deadline";
    }
}
