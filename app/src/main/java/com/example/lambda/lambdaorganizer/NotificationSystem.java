package com.example.lambda.lambdaorganizer;


import android.app.Notification;
//import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.app.NotificationCompat;
import android.app.NotificationManager;
//import android.app.PendingIntent;
import android.content.Context;
//import android.content.Intent;


/**
 * Created by jadenball on 2017-10-12.
 */

public class NotificationSystem {

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

//    public static void notifInit(){
        //        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.lambda_icon)
//                        .setContentTitle("Lambda Organizer")
//                        .setContentText("Lambda Organizer notification");
//        // Creates an explicit intent for an Activity in your app
//        Intent resultIntent = new Intent(this, ResultActivity.class);
//
//        // The stack builder object will contain an artificial back stack for the
//        // started Activity.
//        // This ensures that navigating backward from the Activity leads out of
//        // your app to the Home screen.
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        // Adds the back stack for the Intent (but not the Intent itself)
//        stackBuilder.addParentStack(ResultActivity.class);
//        // Adds the Intent that starts the Activity to the top of the stack
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//        mBuilder.setContentIntent(resultPendingIntent);
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        // mNotificationId is a unique integer your app uses to identify the
//        // notification. For example, to cancel the notification, you can pass its ID
//        // number to NotificationManager.cancel().
//        int mNotificationId = 1; // temporary until system for giving unique ids is made
//        mNotificationManager.notify(mNotificationId, mBuilder.build());
//    }

}
