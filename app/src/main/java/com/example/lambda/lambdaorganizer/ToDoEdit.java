package com.example.lambda.lambdaorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.LinkedList;

import database.tables.CommitmentTable;
import database.tables.TaskTable;
import entities.Task;
import com.example.lambda.lambdaorganizer.ToDoAdd;


/**
 * Created by ReedPosehn on 2017-10-12.
 */

public class ToDoEdit extends AppCompatActivity {

    String datePattern = "yyyy-MM-dd";
    String timePattern = "HH:mm:ss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(timePattern);
    Context c;
    public final String TAG = "ToDoEdit";

    public void sendNotification(final String notification) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ToDoEdit.this, notification, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void toast(final String toastStr) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ToDoEdit.this, toastStr, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void clearFields() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /**
                 * Form fields:
                 */
                final EditText nameEdit  = (EditText) findViewById(R.id.txtName);
                final EditText dateEdit  = (EditText) findViewById(R.id.txtDate);
                final EditText startEdit  = (EditText) findViewById(R.id.txtStart);
                final EditText endEdit  = (EditText) findViewById(R.id.txtEnd);
                final EditText descEdit  = (EditText) findViewById(R.id.txtDesc);
                final EditText priorEdit  = (EditText) findViewById(R.id.txtPrior);
                nameEdit.setText("");
                dateEdit.setText("");
                startEdit.setText("");
                endEdit.setText("");
                descEdit.setText("");
                priorEdit.setText("");
            }
        });
    }

    private class AsyncAddToDatabaseEdit implements Runnable {
        Task task;
        AsyncAddToDatabaseEdit(Task task) {
            this.task = task;
        }

        @Override
        public void run() {
            try {
                CommitmentTable.getInstance(getBaseContext()).insert(this.task);
                TaskTable.getInstance(getBaseContext()).insert(this.task);
                clearFields();
                toast("Added " + this.task.getTitle());
            } catch(Exception e) {
                toast(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todoedit);

        /**
         * Buttons:
         */
        final Button taskButton = (Button) findViewById(R.id.btnTask);
        final Button subButton = (Button) findViewById(R.id.btnSubmit);
        final Button backButton = (Button) findViewById(R.id.btnBack);

        /**
         * Form fields:
         */
        final EditText taskEdit = (EditText) findViewById(R.id.txtTaskName);
        final EditText nameEdit  = (EditText) findViewById(R.id.txtName);
        final EditText dateEdit  = (EditText) findViewById(R.id.txtDate);
        final EditText startEdit  = (EditText) findViewById(R.id.txtStart);
        final EditText endEdit  = (EditText) findViewById(R.id.txtEnd);
        final EditText descEdit  = (EditText) findViewById(R.id.txtDesc);
        final EditText priorEdit  = (EditText) findViewById(R.id.txtPrior);

        /**
         *  Get Task info
         */
        taskButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
              try {
                  String task = taskEdit.getText().toString();
                  LinkedList<Task> list = TaskTable.getInstance(c).selectByTitle(task);
                  Task t = list.getFirst();
                  nameEdit.setText(t.getTitle());
                  String dateEd = simpleDateFormat.format(t.getDate());
                  String startEd = simpleDateFormat.format(t.getStart());
                  String endEd = simpleDateFormat.format(t.getEnd());
                  dateEdit.setText(dateEd);
                  startEdit.setText(startEd);
                  endEdit.setText(endEd);
                  descEdit.setText(t.getDesc());
                  priorEdit.setText(String.valueOf(t.getPrio()));
              }
              catch (Exception e)
              {
                  Toast.makeText(getBaseContext(),
                          "Task can't be found",
                          Toast.LENGTH_SHORT).show();
              }
           }
        });

        /**
         * Submit handling:
         */
        subButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String name = nameEdit.getText().toString();
                    String date = dateEdit.getText().toString();
                    String start = startEdit.getText().toString();
                    String end = endEdit.getText().toString();
                    String description = descEdit.getText().toString();
                    String priority = priorEdit.getText().toString();
                    String task = taskEdit.getText().toString();
                    LinkedList<Task> list = TaskTable.getInstance(c).selectByTitle(task);
                    TaskTable.getInstance(c).remove(list.getFirst());
                    Task newTask = new Task(name, description, Integer.parseInt(priority));
                    newTask.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
                    newTask.setStart(new SimpleDateFormat("HH:mm:ss").parse(start));
                    newTask.setEnd(new SimpleDateFormat("HH:mm:ss").parse(end));
                    new Thread(new ToDoEdit.AsyncAddToDatabaseEdit(newTask)).start();
                    Toast.makeText(getBaseContext(),
                            "Task added!",
                            Toast.LENGTH_SHORT).show();

                } catch(NumberFormatException e) { // Error in priority format
                    Toast.makeText(getBaseContext(),
                            "Priority must be a number",
                            Toast.LENGTH_SHORT).show();
                    return;
                } catch(RuntimeException e) { // Error in  date or time format
                    Toast.makeText(getBaseContext(),
                            "Date format must be: yyyy-MM-dd\nTime format must be: HH:MM:SS",
                            Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(getBaseContext(),
                            "Task not in database",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ToDoEdit.this, ToDoListManager.class));
            }
        });
    }
}
