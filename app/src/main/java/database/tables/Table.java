package database.tables;

import android.content.ContentValues;
import android.content.Context;

import database.DatabaseHelper;
import database.DatabaseObject;

/**
 * Created by mahmudfasihulazam on 2017-11-05.
 */

public abstract class Table {
    protected Context myContext;
    protected String tableName;

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

    public void insert(DatabaseObject record) {
        DatabaseHelper dbHelper = new DatabaseHelper(this.myContext);
        long status = -1;
        try {
            status = dbHelper.getWritableDatabase().insert(this.tableName, null,
                    record.databaseObject());
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            dbHelper.close();
            if(-1 == status) throw new RuntimeException("Insertion failed");
        }
    }

    public void remove(DatabaseObject record) {
        DatabaseHelper dbHelper = new DatabaseHelper(this.myContext);
        try {
            dbHelper.getWritableDatabase().execSQL(this.removeQuery(record.databaseObject()));
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            dbHelper.close();
        }
    }

    public abstract ContentValues query(String q);
    protected abstract  String removeQuery(ContentValues values);

}
