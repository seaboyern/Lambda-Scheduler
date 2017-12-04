package database.tables;

import android.content.Context;
import android.util.Log;

import com.example.lambda.lambdaorganizer.FormatDateTime;

import java.text.ParseException;
import java.util.LinkedList;

import adapters.SessionToStudyEventAdapter;
import adapters.StudyEventToSessionAdapter;
import entities.Commitment;
import entities.Session;
import entities.StudyEvent;

/**
 * Created by mahmudfasihulazam on 2017-11-30.
 */

public final class TestSessionTable {

    private static int size = 3;
    private static String TAG = "TestSessionTable";

    private TestSessionTable() {}

    private static String[] frequencies = { "DAILY" , "WEEKLY" };

    public static void testSessionTable(Context c) throws ParseException {
        Session[] a = new Session[size];
        for(int i = 0; i < size; i ++) {
            a[i] = new Session("Session " + (i + 1), "A session", 1);
            LinkedList<String> emails = new LinkedList<>();
            emails.add("hello1@gmail.com");
            emails.add("hello2@gmail.com");
            emails.add("hello3@gmail.com");
            a[i].setAttendeesEmail(emails);
            a[i].setTimeZone("-0600");
            a[i].setStart(FormatDateTime.getDateFromString(
                    String.format("2017-11-%s 02:30:00",
                        Integer.toString(i + 2)))
            );
            a[i].setEnd(FormatDateTime.getDateFromString(
                    String.format("2017-11-%s 03:30:00",
                            Integer.toString(i + 2)))
            );
            a[i].setNext(a[i].getStart());
            a[i].setTimeZone("-6:00");
            a[i].setRecCount(i);
            a[i].setRecFreq(frequencies[i % 2]);
            a[i].setGoogleId("SOME_GOOGLE_ID");
            a[i].setLocation("Saskatoon");
            try {
                SessionTable.getInstance(c).insert(a[i]);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        for(int i = 0; i < size; i ++) {
            a[i] = new Session("Session " + (i + 4), "A session", 1);
            LinkedList<String> emails = new LinkedList<>();
            emails.add("hello1@gmail.com");
            emails.add("hello2@gmail.com");
            emails.add("hello3@gmail.com");
            a[i].setAttendeesEmail(emails);
            a[i].setTimeZone("-0600");
            a[i].setStart(FormatDateTime.getDateFromString("2017-11-11 02:30:00"));
            a[i].setEnd(FormatDateTime.getDateFromString(
                    String.format("2017-11-1%s 03:30:00",
                            Integer.toString(i + 2)))
            );
            a[i].setNext(a[i].getStart());
            a[i].setTimeZone("-6:00");
            a[i].setRecCount(i);
            a[i].setRecFreq(frequencies[i % 2]);
            a[i].setGoogleId("SOME_GOOGLE_ID");
            a[i].setLocation("Saskatoon");
            try {
                SessionTable.getInstance(c).insert(a[i]);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        Session titleResult = SessionTable.getInstance(c).selectByTitle("Session 1");
        Log.d(TAG, "\n#####\n# Select from Session Table by title:" + titleResult.toString());

        /**
         * Retrieving sessions from the database as study sessions:
         */
        LinkedList<Session> startResult = SessionTable.getInstance(c).selectByStartDate(
                FormatDateTime.getDateFromString("2017-11-11 02:30:00")
        );
        Log.d(TAG, startResult != null
                // found
                ? "\n#####\n# Select from Session Table by start:\n" + startResult.toString()
                // not founds
                : "NULL List for selectByStartDate");

        LinkedList<SessionToStudyEventAdapter> startResultAdapted =
                SessionToStudyEventAdapter.adaptSessions(startResult);

        Log.d(TAG, startResultAdapted != null
                // found
                ? "\n#####\n# Select from Session Table by start and adapt to StudyEvent:\n"
                        + ((startResultAdapted.toString().equals(startResult.toString()))
                                ? "SUCCESS" : "FAILED")
                // not founds
                : "NULL List for selectByStartDate");

        LinkedList<Session> nextResult = SessionTable.getInstance(c)
                .selectByNextDate(startResult.getFirst().getNext());

        Log.d(TAG, startResult != null
                // found
                ? "\n#####\n# Select from Session Table by next:\n" + nextResult.toString()
                // not founds
                : "NULL List for selectByNextDate");

        LinkedList<Commitment> commitments =
                CommitmentTable.getInstance(c).selectByDate(startResult.getFirst().getStart());

        Log.d(TAG, commitments != null
            ? "\n#####\n# Select from Commitment Table by date:\n" + commitments.toString()
            : "NULL List for selectByDate");

    }

}
