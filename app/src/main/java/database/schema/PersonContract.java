package database.schema;

import android.provider.BaseColumns;

/**
 * Created by mahmudfasihulazam on 2017-11-16.
 */

public final class PersonContract {
    private PersonContract() {}

    public static final String  TABLE_NAME = "person";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                PersonEntry.COLUMN_NAME_FNAME + " TEXT," +
                PersonEntry.COLUMN_NAME_LNAME + " TEXT," +
                PersonEntry.COLUMN_NAME_EMAIL + " TEXT," +
            "PRIMARY KEY(" +
                PersonEntry.COLUMN_NAME_EMAIL + "," +
                PersonEntry.COLUMN_NAME_LNAME + "," +
                PersonEntry.COLUMN_NAME_EMAIL +
            "));";

    public static final class PersonEntry implements BaseColumns {
        private PersonEntry() {}
        public static final String COLUMN_NAME_FNAME = "fname";
        public static final String COLUMN_NAME_LNAME = "lname";
        public static final String COLUMN_NAME_EMAIL = "email";
    }
}
