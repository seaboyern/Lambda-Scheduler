package entities;

import com.example.lambda.lambdaorganizer.FormatDateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import entities.interfaces.TimeSlot;

/**
 * Created by mahmudfasihulazam on 2017-10-12.
 */

public class Task extends Commitment implements TimeSlot {

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
        super.setGoogleId("");
    }

    @Override
    public Date getSequencingDateTime() {
        Date now = new Date();
        if(now.compareTo(this.getStart()) < 0) {
            return this.getStart();
        } else {
            return this.getEnd();
        }
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

    @Override
    public int compareTo(TimeSlot other) {
        return this.getStart().compareTo(other.getStart());
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm:ss");
        return "Task: Title: " + this.getTitle()
                + "; Google ID: " + this.getGoogleId()
                + "; Date: " + dateFmt.format(this.date)
                + "; Start: " + timeFmt.format(this.start)
                + "; End: " + timeFmt.format(this.end)
                + "; Description: " + this.getDesc()
                + "; Priority: " + this.getPrio();
    }

    public void setTimeBounds(String date, String start, String end) throws ParseException {
        String startStr = String.format("%s %s", date, start);
        String endStr = String.format("%s %s", date, end);
        String dateStr = startStr;
        this.setDate(FormatDateTime.getDateFromString(dateStr));
        this.setStart(FormatDateTime.getDateFromString(startStr));
        this.setEnd(FormatDateTime.getDateFromString(endStr));
    }

    /**
     * Does this task conflict with a given task?
     */
    public boolean conflict(final Task otherTask) {
        return true;
    }

}
