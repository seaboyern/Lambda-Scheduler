package entities;

import com.example.lambda.lambdaorganizer.FormatDateTime;

import java.text.ParseException;
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
        super.setGoogleId("");
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
        try {
            String startStr = new SimpleDateFormat("HH:mm:ss").format(start);
            this.start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    new SimpleDateFormat("yyyy-MM-dd").format(this.date) + " " + startStr
                    );
        } catch (Exception e) {
        }
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        try {
            String endStr = new SimpleDateFormat("HH:mm:ss").format(end);
            this.end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    new SimpleDateFormat("yyyy-MM-dd").format(this.date) + " " + endStr
                    );
        } catch (Exception e) {
        }
    }

    public void setTimeBounds(String date, String start, String end) throws ParseException {
        String startStr = String.format("%s %s", date, start);
        String endStr = String.format("%s %s", date, end);
        String dateStr = startStr;
        this.setDate(FormatDateTime.getDateFromString(dateStr));
        this.setStart(FormatDateTime.getDateFromString(startStr));
        this.setEnd(FormatDateTime.getDateFromString(endStr));
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

    /**
     * Does this task conflict with a given task?
     */
    public boolean conflict(final Task otherTask) {
        return true;
    }

}
