package entities;

import java.util.Date;
import java.util.LinkedList;

import database.DatabaseObject;
import entities.interfaces.SessionInterface;

/**
 * Created by mahmudfasihulazam on 2017-11-30.
 */

public class Session extends Commitment implements
        SessionInterface, DatabaseObject {
    private Date start;
    private Date end;
    private Date next;
    private String timeZone;
    private String recFreq;
    private int recCount;
    private LinkedList<String> attendeesEmail;

    public Session(String title, String desc, int prio) {
        super(title, desc, prio);
    }

    @Override
    public String getRecFreq() {
        return this.recFreq;
    }

    @Override
    public int getRecCount() {
        return recCount;
    }

    @Override
    public void setRecFreq(String freq) {
        this.recFreq = freq;
    }

    @Override
    public void setRecCount(int count) {
        this.recCount = count;
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

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public LinkedList<String> getAtendeesEmail() {
        return attendeesEmail;
    }

    public void setAttendeesEmail(LinkedList<String> attendeesEmail) {
        this.attendeesEmail = attendeesEmail;
    }

    @Override
    public String toString() {
        return String.format("\nSession: %s\nStart: %s,\nNext: %s,\nEnd: %s, Time Zone: %s\nRec: (%s, %d)\n" +
                        "Emails:\n%s\n",
                this.getTitle(), this.getStart().toString(), this.getNext().toString(),
                this.getEnd().toString(),
                this.getTimeZone(), this.getRecFreq(), this.getRecCount(),
                this.getAtendeesEmail().toString());
    }

    @Override
    public Date getNext() {
        return next;
    }

    @Override
    public void setNext(Date next) {
        this.next = next;
    }

}
