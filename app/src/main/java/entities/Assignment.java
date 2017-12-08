package entities;

import com.example.lambda.lambdaorganizer.FormatDateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

import database.tables.AssignmentTable;

/**
 * Created by mahmudfasihulazam on 2017-11-11.
 */

public class Assignment extends CourseCommitment {

    private Date deadline;

    public Assignment(String title, String desc, int prio) {
        super(title, desc, prio);
    }

    @Override
    public Date getSequencingDateTime() {
        return this.getDeadline();
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Task: Title: " + this.getTitle()
                + "; Due Date: " + FormatDateTime.getDateTimeStringFromDate(this.deadline)
                + "; Description: " + this.getDesc()
                + "; Priority: " + this.getPrio();
    }

}
