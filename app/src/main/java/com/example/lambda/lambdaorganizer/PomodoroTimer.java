package com.example.lambda.lambdaorganizer;


import android.app.Notification;
import android.icu.util.TimeUnit;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lambda.lambdaorganizer.NotificationSystem.NotificationSystem;

import java.sql.Time;

public class PomodoroTimer extends AppCompatActivity {
/* Created boolean values to check for

 */
private boolean boolp = false;
    private boolean boolc = false;
    long timeRemaing = 0;
    TextView text;
    Button start;
    Button stop;
    Button pause;
    Button resume;
    AppCompatActivity pomoActivity = this;

    @Override
    protected void onCreate( Bundle savedInstanceState) {


        /* Setting the values for ui

         */


        super.onCreate(savedInstanceState);
        setContentView(R.layout.pomodoro_timer);
        start = (Button) findViewById(R.id.button11);
        stop = (Button) findViewById(R.id.button12);
         pause = (Button) findViewById(R.id.button14);
         resume = (Button) findViewById(R.id.button13);
        text = (TextView) findViewById(R.id.Time);

        /* Settings for the buttons

         */

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    /* Boolean values to check for when to cancel */
                boolp = false;
                boolc = false;
                int minutes = 1500000;
               // Testing methods
                int min = 10000;

                CountDownTimer timer = new CountDownTimer(min,1000) {
                    @Override

                    /*
                    Stop if its true
                     */
                    public void onTick(long l) {
                        if(boolc || boolp){
                            cancel();
                        }
                                else {

                            /* Setting it to minutes and seconds format

                             */
                            String format = String.format("%02d:%02d", java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(l),
                                    java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(l)-
                                    java.util.concurrent.TimeUnit.MINUTES.toSeconds(java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(l)));
                            text.setText(format);
                            timeRemaing = l;
                        }
                    }

                    @Override
                    public void onFinish() {
                        /*
                        On finish set it back to 25 minutes and is done
                         */
                        text.setText("25:00");
                        NotificationSystem.sendNotification(pomoActivity,pomoActivity,1,"Time Done");



                    }
                }.start();

            }
        });

        /* Setting the stop timer

         */
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolc = true;
                text.setText("25:00");
            }
        });
        /* Setting the resume timer

         */
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolp =false;
                boolc = false;
                /* Time remaining for the app

                 */
                long newTime = timeRemaing;
                CountDownTimer timer = new CountDownTimer(newTime,1000) {
                    @Override
                    /*
                        If this is true it cancels on tick
                     */
                    public void onTick(long l) {
                        if(boolp || boolc){
                            cancel();
                        }

                        else{
                            /*
                            Setting the text and formatting if its done
                             */
                            String format = String.format("%02d:%02d", java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(l),
                                    java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(l)-
                                            java.util.concurrent.TimeUnit.MINUTES.toSeconds(java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(l)));
                            text.setText(format);
                            timeRemaing = l;
                        }
                    }

                    @Override
                    public void onFinish() {
                            text.setText("25:00");
                    }
                }.start();

            }
        });

        /*
        Setting the boolp variable to true
         */
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolp = true;
            }
        });


    }

}
