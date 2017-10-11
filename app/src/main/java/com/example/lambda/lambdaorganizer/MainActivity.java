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
import database.PomodoroContract;

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
                        SQLiteDatabase db_w = dbOpener.getWritableDatabase();
                        SQLiteDatabase db_r = dbOpener.getReadableDatabase();
                        // Refresh the database (drop tables and then recreate tables):
                        dbOpener.onCreate(db_w);

                        // Build a table entry for table pomodoro:
                        ContentValues values = new ContentValues();
                        values.put(PomodoroContract.PomodoroEntry.COLUMN_NAME_SESSION_NAME,
                                "Session1");
                        values.put(PomodoroContract.PomodoroEntry.COLUMN_NAME_START_TIME,
                                "00:00");
                        values.put(PomodoroContract.PomodoroEntry.COLUMN_NAME_END_TIME,
                                "00:45");
                        values.put(PomodoroContract.PomodoroEntry.COLUMN_NAME_DESCRIPTION,
                                "My first pomodoro session");
                        // INSERT INTO pomodoro:
                        long newRowId =
                                db_w.insert(PomodoroContract.PomodoroEntry.TABLE_NAME,
                                        null,
                                        values);

                        // Log table status:
                        Log.v("MainActivity", "Database command executed; row id: " + newRowId);
                        Log.v("MainActivity", dbOpener.getTableAsString("pomodoro"));
                    }
                }).start();
            }
        });


    }
}
