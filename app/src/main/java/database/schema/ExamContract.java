package database.schema;

import android.provider.BaseColumns;

/**
 * Created by mahmudfasihulazam on 2017-11-05.
 */

public final class ExamContract {
    /**
     * Singleton constructor
     */
    private ExamContract() {}

    public static final String TABLE_NAME = "exam";

    /**
     * Table creation command:
     */
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME + " (" +
                    ExamEntry.COLUMN_NAME_TITLE + " TEXT," +
                    ExamEntry.COLUMN_NAME_CRID + " TEXT," +
                    ExamEntry.COLUMN_NAME_DATE + " TEXT," +
                    ExamEntry.COLUMN_NAME_START + " TEXT," +
                    ExamEntry.COLUMN_NAME_END + " TEXT," +
                    ExamEntry.COLUMN_NAME_LOC + " TEXT," +
                    "PRIMARY KEY ("
                        + ExamEntry.COLUMN_NAME_CRID + ","
                        + ExamEntry.COLUMN_NAME_TITLE
                    + "))";

    /**
     * Table deletion command:
     */
    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final class ExamEntry implements BaseColumns {
        private ExamEntry() {}

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CRID = "course_id";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_START = "start";
        public static final String COLUMN_NAME_END = "end";
        public static final String COLUMN_NAME_LOC = "location";
    }

}
