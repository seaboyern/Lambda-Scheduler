package entities.interfaces;

import java.util.Date;
import java.util.LinkedList;

/**
 * Created by mahmudfasihulazam on 2017-11-30.
 */

public interface SessionInterface extends CommitmentInterface, RecurringCommitment {
    public Date getStart();

    public void setStart(Date start);

    public Date getEnd();

    public void setEnd(Date end);

    public String getTimeZone();

    public void setTimeZone(String timeZone);

    public LinkedList<String> getAtendeesEmail();

    public void setAttendeesEmail(LinkedList<String> atendees);
}
