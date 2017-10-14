package com.example.lambda.lambdaorganizer;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import database.DatabaseHelper;
import database.InitDb;
import database.schema.PomodoroContract;
import database.schema.TaskContract;

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
                startActivity(new Intent(MainActivity.this, GradeCalculator.class));
            }
        });

        btnDbTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Run database operations on a separate thread to prevent UI blocking:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Create a database with dummy data:
                        // Helper for opening a database connection:
                        DatabaseHelper dbOpener = new DatabaseHelper(getBaseContext());
                        InitDb.initDb(dbOpener.getWritableDatabase());
                        dbOpener.close();
                    }
                }).start();
            }
        });

        NotificationSystem notif = new NotificationSystem();
        notif.sendNotification(MainActivity.this,
                "App initialization",
                "Finished initializing the main activity.");

    }
}
