package entities;

import android.provider.ContactsContract;
import android.util.Log;

import com.google.api.client.util.DateTime;

import java.lang.reflect.Array;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;

import entities.interfaces.SessionInterface;
import entities.interfaces.StudyEventInterface;

/**
 * Created by priom on 11/13/2017.
 */

public class StudyEvent implements StudyEventInterface {

    private String eventDescription;
    private String eventLocation;
    private String eventSummary;

    private String eventStartDate;
    private String eventEndDate;
    private String eventStarTime;
    private String eventEndTime;
    private String eventTimeZone;

    private String eventRecurrenceFrequency;
    private int eventRecurrenceCount;
    private ArrayList<String> attendees;
    private String googleEventID;
    private String nextStart;

    public static String TAG = "StudyEvent";


    public StudyEvent() {

    }


    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventSummary() {
        return eventSummary;
    }

    public void setEventSummary(String eventSummary) {
        this.eventSummary = eventSummary;
    }



    public ArrayList<String> getAttendees() {
        return attendees;
    }

    public void setAttendees(ArrayList<String> attendees) {

            this.attendees = attendees;

    }


    public String getEventRecurrenceFrequency() {
        return eventRecurrenceFrequency;
    }

    public void setEventRecurrenceFrequency(String eventRecurrenceFrequency) {
        this.eventRecurrenceFrequency = eventRecurrenceFrequency;
    }

    public int getEventRecurrenceCount() {
        return eventRecurrenceCount;
    }

    public void setEventRecurrenceCount(int eventRecurrenceCount) {
        this.eventRecurrenceCount = eventRecurrenceCount;
    }


    public String getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(String eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getEventStarTime() {
        return eventStarTime;
    }

    public void setEventStarTime(String eventStarTime) {
        this.eventStarTime = eventStarTime;
    }

    public String getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(String eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public String getEventTimeZone() {
        return eventTimeZone;
    }

    public void setEventTimeZone(String eventTimeZone) {
        this.eventTimeZone = eventTimeZone;
    }

    public String getGoogleEventID() {
        return googleEventID;
    }

    public void setGoogleEventID(String googleEventID) {
        this.googleEventID = googleEventID;
    }

    @Override
    public String toString() {
        return "StudyEvent{" +
                "eventDescription='" + eventDescription + '\'' +
                ", eventLocation='" + eventLocation + '\'' +
                ", eventSummary='" + eventSummary + '\'' +
                ", eventStartDate='" + eventStartDate + '\'' +
                ", eventEndDate='" + eventEndDate + '\'' +
                ", eventStarTime='" + eventStarTime + '\'' +
                ", eventEndTime='" + eventEndTime + '\'' +
                ", eventTimeZone='" + eventTimeZone + '\'' +
                ", eventRecurrenceFrequency='" + eventRecurrenceFrequency + '\'' +
                ", eventRecurrenceCount=" + eventRecurrenceCount +
                ", attendees=" + attendees +
                ", googleEventID=" + googleEventID +
                '}';
    }


    public String getNextStart() {
        return nextStart;
    }

    public void setNextStart(String nextStart) {
        this.nextStart = nextStart;
    }
}
