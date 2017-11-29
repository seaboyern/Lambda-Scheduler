package com.example.lambda.lambdaorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lambda.lambdaorganizer.GradeCalculator.GradeCalculatorMain;
import com.example.lambda.lambdaorganizer.ToDo.ToDoListManager;
import com.example.lambda.lambdaorganizer.NotificationSystem.NotificationSystem;

import database.DatabaseHelper;

import database.InitDb;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // declaration of all objects
        final Button btnScheduler = (Button) findViewById(R.id.launch_scheduler_btn);
        final Button btnSessionEng = (Button) findViewById(R.id.launch_sessioneng_btn);
        final Button btnPomodoro = (Button) findViewById(R.id.launch_pomodoro_btn);
        final Button btnToDoList = (Button) findViewById(R.id.launch_todolist_btn);
        final Button btnGradeCalc = (Button) findViewById(R.id.launch_gradecalc_btn);
        final Button btnDbTest = (Button) findViewById(R.id.launch_dbtest);


        // button initialization
        btnScheduler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScheduleOverview.class));
            }
        });

        btnSessionEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SessionEngineer.class));
            }
        });

        btnPomodoro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PomodoroTimer.class));
            }
        });

        btnToDoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ToDoListManager.class));
            }
        });

        btnGradeCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GradeCalculatorMain.class));
            }
        });

        btnDbTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,
                                "Please check the android monitor for results",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                // Run database operations on a separate thread to prevent UI blocking:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Create a database with dummy data:
                        // Helper for opening a database connection:
                        InitDb.initDb(getBaseContext());
                    }
                }).start();
            }
        });

    }
}
