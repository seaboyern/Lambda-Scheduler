package com.example.lambda.lambdaorganizer.ToDo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

import database.tables.TaskTable;
import entities.Task;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lambda.lambdaorganizer.R;
import com.example.lambda.lambdaorganizer.Scheduler.TaskInfoDialog;
import com.google.common.io.LittleEndianDataInputStream;

/**
 * Created by ReedPosehn on 2017-10-12.
 */

public class ToDoView extends AppCompatActivity {

    String datePattern = "yyyy-MM-dd";
    String timePattern = "HH:mm:ss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(timePattern);

    private static final int NUM_PRIO = 3; // Number of priority classes
    private static final String taskDisplayFormat = "%s\n  on %s,\n  from %s to %s";

    public void toast(final String toastStr) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ToDoView.this, toastStr, Toast.LENGTH_LONG).show();
            }
        });
    }

    private class TaskClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            TaskView t = (TaskView) adapterView.getItemAtPosition(position);
            toast("TODO: Display dialog for " + t.getTask().getTitle());

            TaskInfoDialog dialog = new TaskInfoDialog();
            Bundle taskBundle = new Bundle();
            taskBundle.putString("info", t.getTask().toString());
            dialog.setArguments(taskBundle);
            dialog.show(getSupportFragmentManager(), "Task info");
        }
    }

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
        ArrayList<TaskView>[] lsArr = new ArrayList[NUM_PRIO];

        /**
         * ArrayAdapters for each of the arraylists
         */
        ArrayAdapter<TaskView>[] adapter  = new ArrayAdapter[NUM_PRIO];

        for(int i = 0; i < NUM_PRIO; i++) {
            lsArr[i] = new ArrayList<TaskView>();
            adapter[i] = new ArrayAdapter<TaskView>(this, android.R.layout.simple_list_item_1,
                    lsArr[i]);
            lv[i].setAdapter(adapter[i]);
            lv[i].setOnItemClickListener(new TaskClickListener());
        }

        try{
            TreeMap<Integer, LinkedList<Task>> prioMap = TaskTable.getInstance(getBaseContext()).priorityMap();
            for(Integer prio : prioMap.keySet()) {
                LinkedList<Task> l = prioMap.get(prio);
                for(Task task : l) {
                    // Parse dates and times:
                    String dateLs = simpleDateFormat.format(task.getDate());
                    String startLs = simpleTimeFormat.format(task.getStart());
                    String endLs = simpleTimeFormat.format(task.getEnd());
                    // Build string to display:
                    lsArr[prio-1].add(new TaskView(task));
                }
            }
        }
        catch(Exception e){
            Toast.makeText(getBaseContext(),
                    "No tasks at the moment",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
