package database.schema;

import android.provider.BaseColumns;

/**
 * Created by mahmudfasihulazam on 2017-11-16.
 */

public final class RecurringCommitmentContract {
    private RecurringCommitmentContract() {}

    /**
     * Table Name:
     */
    public static String TABLE_NAME = "rec_comm";

    /**
     * Creation query:
     */
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
        TABLE_NAME + " (" +
        RecurringCommitmentEntry.COLUMN_NAME_TITLE + " TEXT," +
        RecurringCommitmentEntry.COLUMN_NAME_TYPE + " TEXT," +
        RecurringCommitmentEntry.COLUMN_NAME_FREQ + " TEXT," +
        RecurringCommitmentEntry.COLUMN_NAME_COUNT + " INTEGER," +
            "PRIMARY KEY ("
                + RecurringCommitmentEntry.COLUMN_NAME_TITLE
            + "))";

    /**
     * Drop query:
     */
    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public static final class RecurringCommitmentEntry implements BaseColumns {
        private RecurringCommitmentEntry() {}

        public static final String COLUMN_NAME_TITLE =
                CommitmentContract.CommitmentEntry.COLUMN_NAME_TITLE;
        public static final String COLUMN_NAME_TYPE =
                CommitmentContract.CommitmentEntry.COLUMN_NAME_TYPE;
        public static final String COLUMN_NAME_FREQ = "freq";
        public static final String COLUMN_NAME_COUNT = "count";
        
    }
}
