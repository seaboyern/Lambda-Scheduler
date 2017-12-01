package database.tables;

import android.content.Context;
import android.util.Log;

import com.example.lambda.lambdaorganizer.FormatDateTime;

import java.text.ParseException;
import java.util.LinkedList;

import entities.Session;

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
            a[i].setTimeZone("-6:00");
            a[i].setRecCount(i);
            a[i].setRecFreq(frequencies[i % 2]);
            a[i].setGoogleId("SOME_GOOGLE_ID");
            Log.d(TAG, a[i].toString());
            try {
                SessionTable.getInstance(c).insert(a[i]);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}