package entities;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mahmudfasihulazam on 2017-10-12.
 */

public class Task extends Commitment {

    /**
     * Fields:
     */
    protected Date date;
    protected Date start;
    protected Date end;

    /**
     * Android log tag:
     */
    private static String TAG = "Task";

    public Task(String title, String desc, int prio) {
        super(title, desc, prio);
    }

    // Getters and setters:
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    @Override
    public String toString() {
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm:ss");
        return "Task: Title: " + this.title
                + "; Date: " + dateFmt.format(this.date)
                + "; Start: " + timeFmt.format(this.start)
                + "; End: " + timeFmt.format(this.end)
                + "; Description: " + this.desc
                + "; Priority: " + this.prio;
    }

    /**
     * Does this task conflict with a given task?
     */
    public boolean conflict(final Task otherTask) {
        return true;
    }

}
