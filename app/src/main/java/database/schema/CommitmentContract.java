package database.schema;

import android.provider.BaseColumns;

/**
 * Created by mahmudfasihulazam on 2017-11-05.
 */

public final class CommitmentContract {

    /**
     * Singleton constructor
     */
    private CommitmentContract() {}

    public static final String TABLE_NAME = "commitment";

    /**
     * Table creation command:
     */
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME + " (" +
                    CommitmentEntry.COLUMN_NAME_TITLE + " TEXT," +
                    CommitmentEntry.COLUMN_NAME_DESC + " TEXT," +
                    CommitmentEntry.COLUMN_NAME_PRIO + " INTEGER," +
                    CommitmentEntry.COLUMN_NAME_TYPE + " TEXT," +
                    "PRIMARY KEY ("
                        + CommitmentEntry.COLUMN_NAME_TITLE + ","
                        + CommitmentEntry.COLUMN_NAME_TYPE
                    + "))";
    /**
     * Table deletion command:
     */
    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * Fields and table name
     */
    public static final class CommitmentEntry implements BaseColumns {
        private CommitmentEntry() {}

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESC = "desc";
        public static final String COLUMN_NAME_PRIO = "priority";
        public static final String COLUMN_NAME_TYPE = "type";
    }

}
