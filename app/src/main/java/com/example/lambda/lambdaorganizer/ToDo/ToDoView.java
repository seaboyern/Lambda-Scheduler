package com.example.lambda.lambdaorganizer.ToDo;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

import database.tables.TaskTable;
import entities.Task;

import android.widget.ListView;
import android.widget.Toast;

import com.example.lambda.lambdaorganizer.R;
import com.example.lambda.lambdaorganizer.TaskDisplay;

/**
 * Created by ReedPosehn on 2017-10-12.
 */

public class ToDoView extends AppCompatActivity implements TaskDisplay{

    private static final int NUM_PRIO = 3; // Number of priority classes
    private static final String TAG = "ToDoView";

    private ListView[] lv;
    private ArrayList<TaskView>[] lsArr;
    private ArrayAdapter<TaskView>[] adapter;

    public void deleteTask(final Task task, final String message) {
        TaskTable.getInstance(getApplicationContext()).remove(task);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter[task.getPrio() - 1].remove(new TaskView(task));
                adapter[task.getPrio() - 1].notifyDataSetChanged();
                if(null != message) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void addTask(final Task newTask, final String message) {
        TaskTable.getInstance(getApplicationContext()).insert(newTask);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter[newTask.getPrio() - 1].add(new TaskView(newTask));
                adapter[newTask.getPrio() - 1].notifyDataSetChanged();
                if(null != message) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void showDialog(DialogFragment dialog, String tag) {
        dialog.show(getFragmentManager(), tag);
    }

    private class TaskClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            TaskView t = (TaskView) adapterView.getItemAtPosition(position);

            TaskInfoDialog dialog = new TaskInfoDialog();
            Bundle taskBundle = TaskInfoDialog.buildBundleFromTask(t.getTask());
            Log.d(TAG, taskBundle.toString());
            dialog.setArguments(taskBundle);
            dialog.show(getSupportFragmentManager(), "Task info");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.todolist_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.new_item:
                showDialog(new TaskAddDialog(), "New task");
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todoview);

        /**
         * ListViews
         */
        lv = new ListView[NUM_PRIO];
        lv[0] = (ListView) findViewById(R.id.listView);
        lv[1] = (ListView) findViewById(R.id.listView2);
        lv[2] = (ListView) findViewById(R.id.listView3);

        /**
         * Arraylists for the listviews
         */
        lsArr = new ArrayList[NUM_PRIO];

        /**
         * ArrayAdapters for each of the arraylists
         */
        adapter  = new ArrayAdapter[NUM_PRIO];

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
                    // Populate array for displaying:
                    lsArr[prio-1].add(new TaskView(task));
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getBaseContext(),
                    "No tasks at the moment",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
