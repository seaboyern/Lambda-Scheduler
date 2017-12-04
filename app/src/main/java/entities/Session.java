package entities;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import database.DatabaseObject;
import entities.interfaces.SessionInterface;

/**
 * Created by mahmudfasihulazam on 2017-11-30.
 */

public class Session extends RecurringCommitment implements
        SessionInterface, DatabaseObject {
    private String timeZone;
    private LinkedList<String> attendeesEmail;

    public Session(String title, String desc, int prio) {
        super(title, desc, prio);
    }

    @Override
    public Date getSequencingDateTime() {
        Date now = new Date();
        if(now.compareTo(this.getStart()) < 0) {
            return this.getStart();
        } else if(now.compareTo(this.getStart()) < 0) {
            return this.getEnd();
        } else {
            if(this.getRecCount() >= 0) {
                return this.getNext();
            } else {
                return null;
            }
        }
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public LinkedList<String> getAttendeesEmail() {
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
                this.getAttendeesEmail().toString());
    }

}
