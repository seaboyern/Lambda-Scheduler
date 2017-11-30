package com.example.lambda.lambdaorganizer;


import android.icu.util.TimeUnit;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolp = false;
                boolc = false;
                int minutes = 15000000;
                int min = 10000;
                CountDownTimer timer = new CountDownTimer(min,1000) {
                    @Override
                    public void onTick(long l) {
                        if(boolc || boolp){
                            cancel();
                        }
                                else {
                            String format = String.format("%02d:%02d", java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(l),
                                    java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(l));
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
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolc = true;
                text.setText("25:00");
            }
        });
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolp =false;
                boolc = false;
                long newTime = timeRemaing;
                CountDownTimer timer = new CountDownTimer(newTime,1000) {
                    @Override
                    public void onTick(long l) {
                        if(boolp || boolc){
                            cancel();
                        }
                        else{
                            String format = String.format("%02d:%02d", java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(l),
                                    java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(l));
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
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolp = true;
            }
        });


    }

}
