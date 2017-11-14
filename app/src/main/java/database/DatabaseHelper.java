package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import database.schema.AssignmentContract;
import database.schema.CommitmentContract;
import database.schema.CourseCommitmentContract;
import database.schema.CourseContract;
import database.schema.ExamContract;
import database.schema.TaskContract;

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
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CourseContract.CREATE_TABLE);
        db.execSQL(TaskContract.CREATE_TABLE);
        db.execSQL(CommitmentContract.CREATE_TABLE);
        db.execSQL(CourseCommitmentContract.CREATE_TABLE);
        db.execSQL(AssignmentContract.CREATE_TABLE);
        db.execSQL(ExamContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    /**
     * Returns a String representation of the specified table:
     */
    public String getTableAsString(String tableName) {
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
