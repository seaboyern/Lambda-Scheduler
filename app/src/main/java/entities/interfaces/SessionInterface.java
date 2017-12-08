package entities.interfaces;

import java.sql.Time;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by mahmudfasihulazam on 2017-11-30.
 */

public interface SessionInterface extends CommitmentInterface, RecurringCommitment, TimeSlot {
    public String getTimeZone();
    public void setTimeZone(String timeZone);
    public LinkedList<String> getAttendeesEmail();
    public void setAttendeesEmail(LinkedList<String> attendees);
    public String getLocation();
    public void setLocation(String location);
}
