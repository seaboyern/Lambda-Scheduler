package com.example.lambda.lambdaorganizer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mahmudfasihulazam on 2017-11-25.
 */

public final class FormatDateTime {
    public static final String dateFormatString = "yyyy-MM-dd";
    public static final String timeFormatString = "HH:mm:ss";
    public static final String dateTimeFormatString =
            String.format("%s %s", dateFormatString, timeFormatString);

    public static final String getDateStringFromDate(Date date) {
        String ret = new SimpleDateFormat(dateFormatString).format(date);
        return ret;
    }

    public static final String getTimeStringFromDate(Date date) {
        String ret = new SimpleDateFormat(timeFormatString).format(date);
        return ret;
    }

    public static final String getDateTimeStringFromDate(Date date) {
        String ret = new SimpleDateFormat(dateTimeFormatString).format(date);
        return ret;
    }

    public static final Date getDateFromString(String s) throws ParseException {
        Date ret = new SimpleDateFormat(dateTimeFormatString).parse(s);
        return ret;
    }
}
