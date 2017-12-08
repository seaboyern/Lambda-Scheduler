package adapters;

import com.example.lambda.lambdaorganizer.FormatDateTime;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;

import entities.Session;
import entities.interfaces.StudyEventInterface;

/**
 * Created by mahmudfasihulazam on 2017-12-04.
 */

public class SessionToStudyEventAdapter implements StudyEventInterface {


    private Session s;

    public SessionToStudyEventAdapter(Session s) {
        this.s = s;
    }

    @Override
    public String getEventDescription() {
        return this.s.getDesc();
    }

    @Override
    public void setEventDescription(String eventDescription) {
        this.s.setDesc(eventDescription);
    }

    @Override
    public String getEventLocation() {
        return this.s.getLocation();
    }

    @Override
    public void setEventLocation(String eventLocation) {
        this.s.setLocation(eventLocation);
    }

    @Override
    public String getEventSummary() {
        return this.s.getTitle();
    }

    @Override
    public void setEventSummary(String eventSummary) {
        this.s.setTitle(eventSummary);
    }

    @Override
    public ArrayList<String> getAttendees() {
        return new ArrayList<String>(this.s.getAttendeesEmail());
    }

    @Override
    public void setAttendees(ArrayList<String> attendees) {
        this.s.setAttendeesEmail(new LinkedList<String>(attendees));
    }

    @Override
    public String getEventRecurrenceFrequency() {
        return this.s.getRecFreq();
    }

    @Override
    public void setEventRecurrenceFrequency(String eventRecurrenceFrequency) {
        this.s.setRecFreq(eventRecurrenceFrequency);
    }

    @Override
    public int getEventRecurrenceCount() {
        return this.s.getRecCount();
    }

    @Override
    public void setEventRecurrenceCount(int eventRecurrenceCount) {
        this.s.setRecCount(eventRecurrenceCount);
    }

    @Override
    public String getEventStartDate() {
        return FormatDateTime.getDateStringFromDate(this.s.getStart());
    }

    @Override
    public void setEventStartDate(String eventStartDate) {
        String startDate = String.format("%s %s",
                eventStartDate,
                FormatDateTime.getTimeStringFromDate(this.s.getStart()));
        try {
            this.s.setStart(FormatDateTime.getDateFromString(startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getEventEndDate() {
        return FormatDateTime.getDateStringFromDate(this.s.getEnd());
    }

    @Override
    public void setEventEndDate(String eventEndDate) {
        String endDate = String.format("%s %s",
                eventEndDate,
                FormatDateTime.getTimeStringFromDate(this.s.getEnd()));
        try {
            this.s.setEnd(FormatDateTime.getDateFromString(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getEventStarTime() {
        return FormatDateTime.getTimeStringFromDate(this.s.getStart());
    }

    @Override
    public void setEventStarTime(String eventStarTime) {
        String startTime = String.format("%s %s",
                FormatDateTime.getDateStringFromDate(this.s.getStart()),
                eventStarTime);
        try {
            this.s.setStart(FormatDateTime.getDateFromString(startTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getEventEndTime() {
        return FormatDateTime.getTimeStringFromDate(this.s.getEnd());
    }

    @Override
    public void setEventEndTime(String eventEndTime) {
        String endTime = String.format("%s %s",
                FormatDateTime.getDateStringFromDate(this.s.getEnd()),
                eventEndTime);
        try {
            this.s.setEnd(FormatDateTime.getDateFromString(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getEventTimeZone() {
        return this.s.getTimeZone();
    }

    @Override
    public void setEventTimeZone(String eventTimeZone) {
        this.s.setTimeZone(eventTimeZone);
    }

    @Override
    public String getGoogleEventID() {
        return this.s.getGoogleId();
    }

    @Override
    public void setGoogleEventID(String googleEventID) {
        this.s.setGoogleId(googleEventID);
    }

    @Override
    public String toString() {
        return this.s.toString();
    }

    public static LinkedList<SessionToStudyEventAdapter> adaptSessions(LinkedList<Session> input) {
        LinkedList<SessionToStudyEventAdapter> result = new LinkedList<>();
        for(Session i : input) {
            result.addLast(new SessionToStudyEventAdapter(i));
        }
        return result;
    }

}
