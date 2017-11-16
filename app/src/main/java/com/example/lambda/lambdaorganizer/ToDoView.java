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
    Context c;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todoview);

        /**
         * ListViews
         */
        final ListView lv = (ListView) findViewById(R.id.listView);
        final ListView lv2 = (ListView) findViewById(R.id.listView2);
        final ListView lv3 = (ListView) findViewById(R.id.listView3);

        /**
         * Arraylists for the listviews
         */
        ArrayList<String> lsArr = new ArrayList<String>();
        ArrayList<String> lsArr2 = new ArrayList<String>();
        ArrayList<String> lsArr3 = new ArrayList<String>();

        /**
         * ArrayAdapters for each of the arraylists
         */
        ArrayAdapter<String> adapter  = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, lsArr);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
            (this, android.R.layout.simple_list_item_1, lsArr2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, lsArr3);

        /**
         * Setting the array adapters for the list views
         */
        lv.setAdapter(adapter);
        lv2.setAdapter(adapter2);
        lv3.setAdapter(adapter3);

        try{
            TreeMap<Integer, LinkedList<Task>> prioMap = TaskTable.getInstance(c).priorityMap();
            for(Integer i : prioMap.keySet()) {
                LinkedList<Task> l = prioMap.get(i);
                for(Task j : l) {
                        if (i == 1)
                        {
                            lsArr.add(j.getTitle());
                            String dateLs = simpleDateFormat.format(j.getDate());
                            String startLs = simpleDateFormat.format(j.getStart());
                            String endLs = simpleDateFormat.format(j.getEnd());
                            lsArr.add(dateLs);
                            lsArr.add(startLs);
                            lsArr.add(endLs);
                            lsArr.add(j.getDesc());
                        }
                        else if(i == 2)
                        {
                            lsArr2.add(j.getTitle());
                            String dateLs = simpleDateFormat.format(j.getDate());
                            String startLs = simpleDateFormat.format(j.getStart());
                            String endLs = simpleDateFormat.format(j.getEnd());
                            lsArr2.add(dateLs);
                            lsArr2.add(startLs);
                            lsArr2.add(endLs);
                            lsArr2.add(j.getDesc());
                        }
                        else if(i == 3)
                        {
                            lsArr3.add(j.getTitle());
                            String dateLs = simpleDateFormat.format(j.getDate());
                            String startLs = simpleDateFormat.format(j.getStart());
                            String endLs = simpleDateFormat.format(j.getEnd());
                            lsArr3.add(dateLs);
                            lsArr3.add(startLs);
                            lsArr3.add(endLs);
                            lsArr3.add(j.getDesc());
                        }
                }
            }
        }
        catch(Exception e){
            Toast.makeText(getBaseContext(),
                    "Error receiving tasks from database; Cannot be found",
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
