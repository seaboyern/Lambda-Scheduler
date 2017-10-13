package entities;

import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import database.schema.TaskContract;

/**
 * Created by mahmudfasihulazam on 2017-10-12.
 */

public class Task {

    private String title;
    private Date date;
    private String desc;
    private Date start;
    private Date end;
    private int priority;
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
        SimpleDateFormat timeFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Parse dates and times:
        try {
            this.setDate(dateFmt.parse(dateStr));
            this.setStart(dateFmt.parse(dateStr + " " + startStr));
            this.setEnd(dateFmt.parse(dateStr + " " + endStr));
        } catch(Exception e) {
            Log.e(TAG, "Date parse error");
            e.printStackTrace();
        }
    }

    public Task(String title, String date, String desc, String start, String end, int priority) {
        this.title = title;
        this.desc = desc;
        this.priority = priority;
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Parse dates and times:
        try {
            this.setDate(dateFmt.parse(date));
            this.setStart(dateFmt.parse(date + " " + start));
            this.setEnd(dateFmt.parse(date + " " + end));
        } catch(Exception e) {
            Log.e(TAG, "Date parse error");
            e.printStackTrace();
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
        return "Task: Title: "
                + this.title + "; Date: "
                + this.date.toString()
                + "; Start: "
                + this.start.toString()
                + "; End: " + this.end.toString()
                + "; Description: " + this.desc;
    }

    /**
     * Does this task conflict with a given task?
     */
    public boolean conflict(Task otherTask) {
        return true;
    }

    /**
     * Insert this task into the database
     */
    public void insertIntoDb() {
        return;
    }

}
