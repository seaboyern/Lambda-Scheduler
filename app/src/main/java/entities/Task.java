package entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import database.schema.TaskContract;

/**
 * Created by mahmudfasihulazam on 2017-10-12.
 */

public class Task implements DatabaseObject {

    /**
     * Fields:
     */
    private String title;
    private Date date;
    private String desc;
    private Date start;
    private Date end;
    private int priority;

    /**
     * Android log tag:
     */
    private static String TAG = "Task";

    public Task(Cursor cur) {
        if(cur.isClosed() || cur.isAfterLast() || cur.isBeforeFirst()) {
            throw new RuntimeException("Cannot create Task from cursor out of position");
        }
        // Get database data:
        this.setTitle(cur.getString(cur.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_TITLE)));
        String dateStr = cur.getString(cur.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_DATE));
        this.setDesc(cur.getString(cur.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_DESC)));
        String startStr = cur.getString(cur.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_START));
        String endStr = cur.getString(cur.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_END));
        this.setPriority(cur.getInt(cur.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_PRIO)));
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm:ss");

        // Parse dates and times:
        try {
            this.setDate(dateFmt.parse(dateStr));
            this.setStart(timeFmt.parse(startStr));
            this.setEnd(timeFmt.parse(endStr));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Task(String title, String date, String start, String end, String desc, int priority) {
        this.title = title;
        this.desc = desc;
        this.priority = priority;
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm:ss");
        // Parse dates and times:
        try {
            this.setDate(dateFmt.parse(date));
            this.setStart(timeFmt.parse(start));
            this.setEnd(timeFmt.parse(end));
        } catch(Exception e) {
            throw new RuntimeException(
                    "Format error: Date: " + date + "; Start: " + start + "; End: " + end);
        }
    }

    // Getters and setters:
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm:ss");
        return "Task: Title: " + this.title
                + "; Date: " + dateFmt.format(this.date)
                + "; Start: " + timeFmt.format(this.start)
                + "; End: " + timeFmt.format(this.end)
                + "; Description: " + this.desc
                + "; Priority: " + this.priority;
    }

    /**
     * Does this task conflict with a given task?
     */
    public boolean conflict(final Task otherTask) {
        return true;
    }

    /**
     * Insert this task into the database
     */
    public ContentValues databaseObject() {
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm:ss");
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_NAME_TITLE, this.title);
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DATE, dateFmt.format(this.date));
        values.put(TaskContract.TaskEntry.COLUMN_NAME_START, timeFmt.format(this.start));
        values.put(TaskContract.TaskEntry.COLUMN_NAME_END, timeFmt.format(this.end));
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DESC, this.desc);
        values.put(TaskContract.TaskEntry.COLUMN_NAME_PRIO, this.priority);
        return values;
    }

}
