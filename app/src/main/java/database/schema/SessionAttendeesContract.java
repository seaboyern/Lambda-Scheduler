package database.schema;

import android.provider.BaseColumns;

/**
 * Created by mahmudfasihulazam on 2017-11-30.
 */

public class SessionAttendeesContract {

    /**
     * Singleton constructor
     */
    private SessionAttendeesContract() {}

    public static final String TABLE_NAME = "SessionAttendees";

    /**
     * Table creation command:
     */
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME + " (" +
                    SessionAttendeesContract.SessionAttendeesEntry.COLUMN_NAME_SESSION + " TEXT," +
                    SessionAttendeesContract.SessionAttendeesEntry.COLUMN_NAME_EMAIL + " TEXT," +
                    "PRIMARY KEY ("
                    + SessionAttendeesContract.SessionAttendeesEntry.COLUMN_NAME_EMAIL + ","
                    + SessionAttendeesEntry.COLUMN_NAME_SESSION
                    + "))";
    /**
     * Table deletion command:
     */
    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * Fields and table name
     */
    public static final class SessionAttendeesEntry implements BaseColumns {
        private SessionAttendeesEntry() {}

        public static final String COLUMN_NAME_SESSION = "session";
        public static final String COLUMN_NAME_EMAIL = "email";
    }

}
