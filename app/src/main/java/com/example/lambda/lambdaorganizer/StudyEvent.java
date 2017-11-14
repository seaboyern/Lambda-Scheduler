package com.example.lambda.lambdaorganizer;

import android.provider.ContactsContract;
import android.util.Log;

import com.google.api.client.util.DateTime;

import java.lang.reflect.Array;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;

/**
 * Created by priom on 11/13/2017.
 */

public class StudyEvent {

    private String eventDescription;
    private String eventLocation;
    private String eventSummary;
    private String eventStartDate;
    private String eventEndDate;
    private String eventStarTime;
    private String eventEndTime;
    private String eventRecurrenceFrequency;
    private int eventRecurrenceCount;
    private ArrayList<String> attendees;

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
}
