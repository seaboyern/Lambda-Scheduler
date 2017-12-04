package entities.interfaces;

import java.util.ArrayList;

/**
 * Created by mahmudfasihulazam on 2017-12-04.
 */

public interface StudyEventInterface {

    public String getEventDescription();

    public void setEventDescription(String eventDescription);

    public String getEventLocation();

    public void setEventLocation(String eventLocation);

    public String getEventSummary();

    public void setEventSummary(String eventSummary);

    public ArrayList<String> getAttendees();

    public void setAttendees(ArrayList<String> attendees);

    public String getEventRecurrenceFrequency();

    public void setEventRecurrenceFrequency(String eventRecurrenceFrequency);

    public int getEventRecurrenceCount();

    public void setEventRecurrenceCount(int eventRecurrenceCount);

    public String getEventStartDate();

    public void setEventStartDate(String eventStartDate);

    public String getEventEndDate();

    public void setEventEndDate(String eventEndDate);

    public String getEventStarTime();

    public void setEventStarTime(String eventStarTime);

    public String getEventEndTime();

    public void setEventEndTime(String eventEndTime);

    public String getEventTimeZone();

    public void setEventTimeZone(String eventTimeZone);

    public String getGoogleEventID();

    public void setGoogleEventID(String googleEventID);

}
