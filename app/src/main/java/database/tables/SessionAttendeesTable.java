
package database.tables;
import android.content.ContentValues;
import android.content.Context;

import database.DatabaseHelper;
import database.DatabaseObject;
import database.schema.SessionAttendeesContract;

/**
 * Created by mahmudfasihulazam on 2017-11-30.
 */

public class SessionAttendeesTable extends Table { // Cannot be accessed outside package

    private static SessionAttendeesTable instance;

    protected SessionAttendeesTable(Context c) {
        super(c);
        this.tableName = SessionAttendeesContract.TABLE_NAME;
    }

    public static SessionAttendeesTable getInstance(Context c) {
        if(instance == null) {
            instance = new SessionAttendeesTable(c);
        }
        return instance;
    }

    @Override
    public void insert(DatabaseObject record) {
        ; // Cannot insert using this method
    }

    @Override
    protected String removeQuery(DatabaseObject record) {
        return null; // cannot be used
    }

    protected void remove(String session) {
        DatabaseHelper dbHelper = new DatabaseHelper(this.context);
        String removeQuery = "DELETE FROM TABLE " + SessionAttendeesContract.TABLE_NAME
                + " WHERE "
                + SessionAttendeesContract.SessionAttendeesEntry.COLUMN_NAME_SESSION
                + " = '" + session
                + "';";
        dbHelper.getWritableDatabase().execSQL(removeQuery);
    }

    protected void insert(String email, String session) {
        ContentValues values = new ContentValues();
        values.put(SessionAttendeesContract.SessionAttendeesEntry.COLUMN_NAME_EMAIL, email);
        values.put(SessionAttendeesContract.SessionAttendeesEntry.COLUMN_NAME_SESSION, session);
        super.rawInsert(values);
    }
}
