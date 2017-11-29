package entities;

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

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
        return "Task: Title: " + this.getTitle()
                + "; Due Date: " + dateFmt.format(this.deadline)
                + "; Description: " + this.getDesc()
                + "; Priority: " + this.getPrio();
    }

}
