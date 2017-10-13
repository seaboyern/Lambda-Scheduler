package database.schema;

import android.provider.BaseColumns;

/**
 * Created by mahmudfasihulazam on 2017-10-12.
 * Schema for task table
 */

public final class TaskContract {
    /**
     * Table creation command:
     */
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
                    TaskEntry.TABLE_NAME + " (" +
                    TaskEntry.COLUMN_NAME_TITLE + " TEXT," +
                    TaskEntry.COLUMN_NAME_DESC + " TEXT," +
                    TaskEntry.COLUMN_NAME_DATE + " TEXT," +
                    TaskEntry.COLUMN_NAME_START + " TEXT," +
                    TaskEntry.COLUMN_NAME_END + " TEXT," +
                    TaskEntry.COLUMN_NAME_PRIO + " INTEGER," +
                    "PRIMARY KEY (" + TaskEntry.COLUMN_NAME_DATE + ","
                        + TaskEntry.COLUMN_NAME_TITLE + ","
                        + TaskEntry.COLUMN_NAME_START
                        + "))";
    /**
     * Table deletion command:
     */
    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TaskEntry.TABLE_NAME;

    /**
     * Singleton constructor
     */
    private TaskContract() {
    }

    /**
     * Fields and table name
     */
    public static class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESC = "desc";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_START = "start";
        public static final String COLUMN_NAME_END = "end";
        public static final String COLUMN_NAME_PRIO = "priority";
        // There is a mapping table to match courses with course commitments
    }

}
