package entities;

import java.util.Date;

import database.DatabaseObject;

/**
 * Created by mahmudfasihulazam on 2017-12-01.
 */

public class RecurringCommitment extends Commitment implements
        entities.interfaces.RecurringCommitment, DatabaseObject {

    private Date start;
    private Date end;
    private Date next;
    private String recFreq;
    private int recCount;

    public RecurringCommitment(String title, String desc, int prio) {
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

    @Override
    public Date getStart() {
        return start;
    }

    @Override
    public void setStart(Date start) {
        this.start = start;
    }

    @Override
    public Date getEnd() {
        return end;
    }

    @Override
    public void setEnd(Date end) {
        this.end = end;
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
