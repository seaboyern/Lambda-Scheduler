package adapters;

import com.example.lambda.lambdaorganizer.FormatDateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;

import database.DatabaseObject;
import entities.StudyEvent;
import entities.interfaces.SessionInterface;
import entities.interfaces.TimeSlot;

/**
 * Created by mahmudfasihulazam on 2017-11-30.
 */

public class StudyEventToSessionAdapter implements SessionInterface, DatabaseObject {

    private StudyEvent s;

    public StudyEventToSessionAdapter(StudyEvent s) {
        this.s = s;
    }

    @Override
    public String getTitle() {
        return s.getEventSummary();
    }

    @Override
    public String getDesc() {
        return s.getEventDescription();
    }

    @Override
    public int getPrio() {
        return -1;
    }

    @Override
    public String getGoogleId() {
        return s.getGoogleEventID();
    }

    @Override
    public Date getSequencingDateTime() {
        return new Date();
    }

    public Date getStart() {
        String startDate = s.getEventStartDate();
        String startTime = s.getEventStarTime();
        String timeZone = s.getEventTimeZone();
        try {
            Date ret = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    String.format("%s %s%s", startDate, startTime, timeZone));
            return ret;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setStart(Date start) {
        String startDate = new SimpleDateFormat("yyyy-MM-dd").format(start);
        String startTime = new SimpleDateFormat("HH:mm:ss").format(start);
        s.setEventStartDate(startDate);
        s.setEventStarTime(startTime);
    }

    @Override
    public Date getEnd() {
        String endDate = s.getEventEndDate();
        String endTime = s.getEventEndTime();
        try {
            Date ret = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    String.format("%s %s", endDate, endTime));
            return ret;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int compareTo(TimeSlot other) {
        return this.compareTo(other);
    }

    @Override
    public void setEnd(Date end) {
        String endDate = new SimpleDateFormat("yyyy-MM-dd").format(end);
        String endTime = new SimpleDateFormat("HH:mm:ss").format(end);
        s.setEventEndDate(endDate);
        s.setEventEndTime(endTime);
    }

    @Override
    public String getTimeZone() {
        return s.getEventTimeZone();
    }

    @Override
    public void setTimeZone(String timeZone) {
        s.setEventTimeZone(timeZone);
    }

    @Override
    public LinkedList<String> getAttendeesEmail() {
        LinkedList<String> list = new LinkedList<>();
        ListIterator<String> iter = s.getAttendees().listIterator();
        while(iter.hasNext()) {
            String next = iter.next();
            list.add(next);
        }
        return list;
    }

    @Override
    public void setAttendeesEmail(LinkedList<String> attendees) {
        ArrayList<String> list = new ArrayList<>();
        ListIterator<String> iter = attendees.listIterator();
        while(iter.hasNext()) {
            String next = iter.next();
            list.add(next);
        }
        this.s.setAttendees(list);
    }

    @Override
    public String getLocation() {
        return this.s.getEventLocation();
    }

    @Override
    public void setLocation(String location) {
        this.s.setEventLocation(location);
    }

    public String getRecFreq() {
        return s.getEventRecurrenceFrequency();
    }

    public int getRecCount() {
        return s.getEventRecurrenceCount();
    }

    @Override
    public void setRecFreq(String freq) {
        s.setEventRecurrenceFrequency(freq);
    }

    @Override
    public void setRecCount(int count) {
        s.setEventRecurrenceCount(count);
    }

    @Override
    public Date getNext() {
        try {
            return FormatDateTime.getDateFromString(this.s.getNextStart());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setNext(Date next) {
        this.s.setNextStart(FormatDateTime.getDateTimeStringFromDate(next));
    }

    public static LinkedList<StudyEventToSessionAdapter> adaptStudyEvents(LinkedList<StudyEvent> input) {
        LinkedList<StudyEventToSessionAdapter> result = new LinkedList<>();
        for(StudyEvent i : input) {
            result.addLast(new StudyEventToSessionAdapter(i));
        }
        return result;
    }

}
