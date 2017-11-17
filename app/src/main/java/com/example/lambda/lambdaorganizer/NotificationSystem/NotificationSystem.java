package com.example.lambda.lambdaorganizer.NotificationSystem;


import android.app.AlarmManager;
import android.app.Notification;
//import android.support.v4.app.TaskStackBuilder;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.app.NotificationCompat;
import android.app.NotificationManager;
//import android.app.PendingIntent;
import android.content.Context;

import com.example.lambda.lambdaorganizer.R;

import java.util.Calendar;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;
//import android.content.Intent;


/**
 * Created by jadenball on 2017-10-12.
 */

public class NotificationSystem {

    private static List<Integer> notifIDs;

    public static void sendNotification(AppCompatActivity activity, String subject, String body){

        String sTitle = "Lambda Organizer";
//        String body = "Lambda Scheduler notification";
//        String subject = "Lambda Subject";
        NotificationManager mNM =
                (NotificationManager)activity.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notif = new Notification.Builder(activity.getApplicationContext())
                .setContentTitle(sTitle)
                .setContentText(body)
                .setContentTitle(subject)
                .setSmallIcon(R.drawable.lambda_icon)
                .build();

        notif.flags |= Notification.FLAG_AUTO_CANCEL;
        mNM.notify(0, notif);

    }

    public static void notifCreate(AppCompatActivity activity, AppCompatActivity resultActivity, int notifID, String msg){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(activity)
                        .setSmallIcon(R.drawable.lambda_icon)
                        .setContentTitle("Lambda Organizer")
                        .setContentText(msg);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(activity, resultActivity.getClass()); /*this, ResultActivity.class*/

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your app to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(activity);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(resultActivity);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        //NotificationManager mNotificationManager =
        //        (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);

        mBuilder.build();

        // mNotificationId is a unique integer your app uses to identify the
        // notification. For example, to cancel the notification, you can pass its ID
        // number to NotificationManager.cancel().
        //mNotificationManager.notify(notifID, mBuilder.build());
    }


    // Cancels the notification with the id notifID
    public static void notifCancel(AppCompatActivity activity, int notifID){
        NotificationManager mNotificationManager =
                (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(notifID);
    }


    public static void createAlarmNotif(AppCompatActivity activity,
                                        int minute, int hour, int day, int month, String AM_PM){
        Intent intent = new Intent(this, NotifService.class);
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(activity.this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR, hour);
        if(AM_PM.equals("AM"))
            calendar.set(Calendar.AM_PM, Calendar.AM);
        else
            calendar.set(Calendar.AM_PM, Calendar.PM);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

}
