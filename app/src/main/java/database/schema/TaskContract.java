package database.schema;

import android.provider.BaseColumns;

/**
 * Created by mahmudfasihulazam on 2017-10-12.
 * Schema for task table
 */

public final class TaskContract {

    /**
     * Singleton constructor
     */
    private TaskContract() {}

    public static final String TABLE_NAME = "task";

    /**
     * Table creation command:
     */
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME + " (" +
                    TaskEntry.COLUMN_NAME_TITLE + " TEXT," +
                    TaskEntry.COLUMN_NAME_DATE + " TEXT," +
                    TaskEntry.COLUMN_NAME_START + " TEXT," +
                    TaskEntry.COLUMN_NAME_END + " TEXT," +
                    "PRIMARY KEY ("
                        + TaskEntry.COLUMN_NAME_DATE + ","
                        + TaskEntry.COLUMN_NAME_TITLE + ","
                        + TaskEntry.COLUMN_NAME_START
                    + "))";
    /**
     * Table deletion command:
     */
    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * Fields and table name
     */
    public static final class TaskEntry implements BaseColumns {
        private TaskEntry() {}

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_START = "start";
        public static final String COLUMN_NAME_END = "end";
    }

}
