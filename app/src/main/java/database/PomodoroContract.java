package database;

import android.provider.BaseColumns;

/**
 * Created by mahmudfasihulazam on 2017-10-10.
 * Schema for table for Pomodoro timer view
 */
public final class PomodoroContract {
    /**
     * Table creation command:
     */
    static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
                    PomodoroEntry.TABLE_NAME + " (" +
                    PomodoroEntry._ID + " INTEGER PRIMARY KEY," +
                    PomodoroEntry.COLUMN_NAME_SESSION_NAME + " TEXT," +
                    PomodoroEntry.COLUMN_NAME_START_TIME + " TEXT," +
                    PomodoroEntry.COLUMN_NAME_END_TIME + " TEXT," +
                    PomodoroEntry.COLUMN_NAME_DESCRIPTION + " TEXT)";
    /**
     * Table deletion command:
     */
    static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + PomodoroEntry.TABLE_NAME;

    /**
     * Singleton constructor
     */
    private PomodoroContract() {
    }

    /**
     * Fields and table name
     */
    public static class PomodoroEntry implements BaseColumns {
        public static final String TABLE_NAME = "pomodoro";
        public static final String COLUMN_NAME_SESSION_NAME = "name";
        public static final String COLUMN_NAME_START_TIME = "start";
        public static final String COLUMN_NAME_END_TIME = "end";
        public static final String COLUMN_NAME_DESCRIPTION = "desc";
    }

}
