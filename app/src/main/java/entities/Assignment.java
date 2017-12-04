package entities;

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
        return String.format("\nAssignment: %s\nDeadline: %s\nCourse ID: %s\n",
                this.getTitle(), this.getDeadline().toString(), this.getCourseId());
    }

}
