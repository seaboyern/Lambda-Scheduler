package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mahmudfasihulazam on 2017-10-10.
 * Singleton wrapper for local SQLite Database
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     * TAG constant for logging to console:
     */
    private static final String TAG = "DatabaseHelper";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "lambda.db";


    /**
     * Constructor
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(PomodoroContract.DELETE_TABLE);
        sqLiteDatabase.execSQL(PomodoroContract.CREATE_TABLE);
        // More tables should be created here:
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(PomodoroContract.DELETE_TABLE);
        sqLiteDatabase.execSQL(PomodoroContract.CREATE_TABLE);
    }

    /**
     * Returns a String representation of the specified table:
     */
    public String getTableAsString(String tableName) {
        Log.d(TAG, "getTableAsString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows = this.getReadableDatabase().rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst()) {
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name : columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }
        allRows.close();

        return tableString;
    }
}
