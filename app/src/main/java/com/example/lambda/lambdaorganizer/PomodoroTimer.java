package com.example.lambda.lambdaorganizer;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PomodoroTimer extends AppCompatActivity {
    private boolean paused = false;


EditText editText;
    Button start;
    TextView texts;
    Button stop;

private long timing = 0;
    protected void onCreate(Bundle savedInstanceState){
         paused= false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pomodoro_timer);

        editText = (EditText) findViewById(R.id.editText3);
        start = (Button) findViewById(R.id.button2);
        texts = (TextView) findViewById(R.id.textView3);
        stop = (Button) findViewById(R.id.button);



        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
paused = false;


                int secs = Integer.valueOf(editText.getText().toString());

                CountDownTimer timer = new CountDownTimer(secs * 1000,1000) {
                    @Override

                    public void onTick(long l) {

                            if(paused){
                                cancel();
                            }

                            else {
                                texts.setText("seconds: " + (int) (l / 1000));
                                timing = l;

                            }
                    }


                    @Override
                    public void onFinish() {
                        texts.setText("Finished!!!");
                    }
                }.start();

            }

        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paused = true;

            }
        });




    }






}
