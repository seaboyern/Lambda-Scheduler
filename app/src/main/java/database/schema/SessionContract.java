package database.schema;

import android.provider.BaseColumns;

/**
 * Created by mahmudfasihulazam on 2017-11-30.
 */

public class SessionContract {

    /**
     * Singleton constructor
     */
    private SessionContract() {}

    public static final String TABLE_NAME = "session";

    /**
     * Table creation command:
     */
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME + " (" +
                    SessionEntry.COLUMN_NAME_TITLE + " TEXT," +
                    SessionEntry.COLUMN_NAME_START + " TEXT," +
                    SessionEntry.COLUMN_NAME_END + " TEXT," +
                    SessionEntry.COLUMN_NAME_LOC + " TEXT," +
                    SessionEntry.COLUMN_NAME_TIME_ZONE + " TEXT," +
                    "PRIMARY KEY ("
                    + SessionEntry.COLUMN_NAME_TITLE
                    + "))";
    /**
     * Table deletion command:
     */
    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * Fields and table name
     */
    public static final class SessionEntry implements BaseColumns {
        private SessionEntry() {}

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_START = "start";
        public static final String COLUMN_NAME_END = "end";
        public static final String COLUMN_NAME_LOC = "location";
        public static final String COLUMN_NAME_TIME_ZONE = "time_zone";
    }

}
