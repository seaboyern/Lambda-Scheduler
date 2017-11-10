package com.example.lambda.lambdaorganizer;


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

import java.util.List;
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
        NotificationManager notif=(NotificationManager)activity.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification.Builder
                (activity.getApplicationContext()).setContentTitle(sTitle).setContentText(body).
                setContentTitle(subject).setSmallIcon(R.drawable.lambda_icon).build();

        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.notify(0, notify);

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
        NotificationManager mNotificationManager =
                (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);

        // mNotificationId is a unique integer your app uses to identify the
        // notification. For example, to cancel the notification, you can pass its ID
        // number to NotificationManager.cancel().
        mNotificationManager.notify(notifID, mBuilder.build());
    }


    // Cancels the notification with the id notifID
    public static void notifCancel(AppCompatActivity activity, int notifID){
        NotificationManager mNotificationManager =
                (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(notifID);
    }

}
