package database.tables;

import android.content.ContentValues;
import android.content.Context;

import java.util.HashMap;
import java.util.LinkedList;

import database.DatabaseHelper;
import database.DatabaseObject;

/**
 * Created by mahmudfasihulazam on 2017-11-05.
 */

public abstract class Table {
    protected Context context;
    protected String tableName;
    protected HashMap<String, String> typeMap;

    protected Table(Context c) {
        this.context = c;
        this.typeMap = new HashMap<String, String>();
    }

    @Override
    public synchronized String toString() {
        DatabaseHelper dbHelper = new DatabaseHelper(this.context);
        try {
            return dbHelper.getTableAsString(this.getTableName());
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            dbHelper.close();
        }
    }

    public String getTypeName(DatabaseObject obj) {
        return typeMap.get(obj.getClass().getName());
    }

    public abstract void insert(DatabaseObject record);

    protected void rawInsert(ContentValues values) {
        DatabaseHelper dbHelper = new DatabaseHelper(this.context);
        long status = -1;
        try {
            status = dbHelper.getWritableDatabase().insert(this.tableName, null, values);
        } catch(Exception e) {
            throw e;
        } finally {
            dbHelper.close();
            if(-1 == status) throw new RuntimeException("Insertion failed");
        }

    }

    public synchronized void remove(DatabaseObject record) {
        DatabaseHelper dbHelper = new DatabaseHelper(this.context);
        try {
            dbHelper.getWritableDatabase().execSQL(this.removeQuery(record));
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            dbHelper.close();
        }
    }

    protected abstract  String removeQuery(DatabaseObject record);

    public synchronized String getTableName() {
        return this.tableName;
    }

}
