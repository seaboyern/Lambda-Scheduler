package database.tables;

import android.content.ContentValues;
import android.content.Context;

import database.DatabaseHelper;
import database.schema.AssignmentContract;

/**
 * Created by mahmudfasihulazam on 2017-11-05.
 */

public abstract class Table {
    protected Context myContext;
    protected String tableName;
    protected String primaryKeyName;

    protected Table(Context c) {
        this.myContext = c;
    }

    public String toString() {
        DatabaseHelper dbHelper = new DatabaseHelper(this.myContext);
        try {
            return dbHelper.getTableAsString(this.tableName);
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            dbHelper.close();
        }
    }

    public void insert(ContentValues record) {
        DatabaseHelper dbHelper = new DatabaseHelper(this.myContext);
        long status = -1;
        try {
            status = dbHelper.getWritableDatabase().insert(this.tableName, null, record);
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            dbHelper.close();
            if(-1 == status) throw new RuntimeException("Insertion failed");
        }
    }

    public void remove(ContentValues record) {
        DatabaseHelper dbHelper = new DatabaseHelper(this.myContext);
        dbHelper.getWritableDatabase().execSQL("DELETE FROM " + this.tableName
                + " WHERE " + this.primaryKeyName + " = "
                + "'" + record.get(this.primaryKeyName) + "'");
        dbHelper.close();
    }

    public abstract ContentValues query(String q);

}
