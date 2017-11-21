package com.example.lambda.lambdaorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

import database.tables.TaskTable;
import entities.Task;
import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by ReedPosehn on 2017-10-12.
 */

public class ToDoView extends AppCompatActivity {

    String datePattern = "yyyy-MM-dd";
    String timePattern = "HH:mm:ss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(timePattern);

    private static final int NUM_PRIO = 3;
    private static final String taskDisplayFormat = "%s\n  on %s,\n  from %s to %s";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todoview);

        /**
         * ListViews
         */
        ListView[] lv = new ListView[NUM_PRIO];
        lv[0] = (ListView) findViewById(R.id.listView);
        lv[1] = (ListView) findViewById(R.id.listView2);
        lv[2] = (ListView) findViewById(R.id.listView3);

        /**
         * Arraylists for the listviews
         */
        ArrayList<String>[] lsArr = new ArrayList[NUM_PRIO];

        /**
         * ArrayAdapters for each of the arraylists
         */
        ArrayAdapter<String>[] adapter  = new ArrayAdapter[NUM_PRIO];

        for(int i = 0; i < NUM_PRIO; i++) {
            lsArr[i] = new ArrayList<String>();
            adapter[i] = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    lsArr[i]);
            lv[i].setAdapter(adapter[i]);
        }

        try{
            TreeMap<Integer, LinkedList<Task>> prioMap = TaskTable.getInstance(getBaseContext()).priorityMap();
            for(Integer i : prioMap.keySet()) {
                LinkedList<Task> l = prioMap.get(i);
                for(Task j : l) {
                    // Parse dates and times:
                    String dateLs = simpleDateFormat.format(j.getDate());
                    String startLs = simpleTimeFormat.format(j.getStart());
                    String endLs = simpleTimeFormat.format(j.getEnd());
                    // Build string to display:
                    String display = String.format(ToDoView.taskDisplayFormat,
                            j.getTitle(),
                            dateLs,
                            startLs,
                            endLs);
                    lsArr[i-1].add(display);
                }
            }
        }
        catch(Exception e){
            Toast.makeText(getBaseContext(),
                    "No tasks at the moment",
                    Toast.LENGTH_SHORT).show();
        }
        final Button backButton = (Button) findViewById(R.id.btnBack);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ToDoView.this, ToDoListManager.class));
            }
        });
    }
}
