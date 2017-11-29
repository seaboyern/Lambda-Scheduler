package com.example.lambda.lambdaorganizer.NotificationSystem;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.lambda.lambdaorganizer.MainActivity;
import com.example.lambda.lambdaorganizer.R;
import com.example.lambda.lambdaorganizer.ToDo.ToDoView;

/**
 * Created by jadenball on 2017-11-11.
 * Broadcast receiver which wakes up after receiving wakeup messages
 * and sends notifications to the user
 */

public class NotifReceiver extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = "Task is Due";
        Intent alarmIntent = new Intent(context, ToDoView.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

        Notification n = new Notification.Builder(context)
                .setContentTitle("Lambda Organizer")
                .setContentText(msg)
                .setSmallIcon(R.drawable.lambda_icon)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();

//        Notification n = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(id, n);
    }
}
