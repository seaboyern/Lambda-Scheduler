package entities.interfaces;

import java.util.Date;

/**
 * Created by mahmudfasihulazam on 2017-11-30.
 */

public interface RecurringCommitment extends CommitmentInterface, TimeSlot {
    public String getRecFreq();
    public int getRecCount();
    public void setRecFreq(String freq);
    public void setRecCount(int count);
    public Date getNext();
    public void setNext(Date next);
    public Date getStart();
    public Date getEnd();
    public void setStart(Date start);
    public void setEnd(Date end);
}
