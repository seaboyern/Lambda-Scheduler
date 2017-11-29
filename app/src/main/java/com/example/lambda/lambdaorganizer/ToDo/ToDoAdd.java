package com.example.lambda.lambdaorganizer.ToDo;

import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lambda.lambdaorganizer.DateTimePicker;
import com.example.lambda.lambdaorganizer.R;

import database.tables.CommitmentTable;
import database.tables.TaskTable;
import entities.Task;

/**
 * Created by ReedPosehn on 2017-10-12.
 */

public class ToDoAdd extends AppCompatActivity {

    public final String TAG = "ToDoAdd";
    String datePattern = "yyyy-MM-dd";
    String timePattern = "HH:mm:ss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(timePattern);

    public void sendNotification(final String notification) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ToDoAdd.this, notification, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void toast(final String toastStr) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ToDoAdd.this, toastStr, Toast.LENGTH_LONG).show();
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

    private class AsyncAddToDatabase implements Runnable {
        Task task;
        AsyncAddToDatabase(Task task) {
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
        setContentView(R.layout.todoadd);

        /**
         * Buttons:
         */
        final Button subButton = (Button) findViewById(R.id.btnSubmit);
        final Button backButton = (Button) findViewById(R.id.btnBack);

        /**
         * Form fields:
         */
        final EditText nameEdit  = (EditText) findViewById(R.id.txtName);
        final EditText dateEdit  = (EditText) findViewById(R.id.txtDate);
        final EditText startEdit  = (EditText) findViewById(R.id.txtStart);
        final EditText endEdit  = (EditText) findViewById(R.id.txtEnd);
        final EditText descEdit  = (EditText) findViewById(R.id.txtDesc);
        final EditText priorEdit  = (EditText) findViewById(R.id.txtPrior);

        startEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                class addStartDateListener implements DateTimePicker.DateTimeListener {
                    public void onDateTimePickerConfirm(Date d) {
                        String startEd = simpleTimeFormat.format(d.getTime());
                        startEdit.setText(startEd);
                    }
                }
                DateTimePicker datetimepicker = new DateTimePicker();
                datetimepicker.setDateTimeListener(new addStartDateListener());
                datetimepicker.show(getSupportFragmentManager(), "date time picker");
            }

        });

        endEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                class addStartDateListener implements DateTimePicker.DateTimeListener {
                    public void onDateTimePickerConfirm(Date d) {
                        String endEd = simpleTimeFormat.format(d.getTime());
                        endEdit.setText(endEd);
                    }
                }
                DateTimePicker datetimepicker = new DateTimePicker();
                datetimepicker.setDateTimeListener(new addStartDateListener());
                datetimepicker.show(getSupportFragmentManager(), "date time picker");
            }

        });

        // Submit handling:
        subButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Consruct task object:
                String name = nameEdit.getText().toString();
                String date = dateEdit.getText().toString();
                String start = startEdit.getText().toString();
                String end = endEdit.getText().toString();
                String description = descEdit.getText().toString();
                String priority = priorEdit.getText().toString();
                try {
                    Task newTask = new Task(name, description, Integer.parseInt(priority));
                    newTask.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
                    newTask.setStart(new SimpleDateFormat("HH:mm:ss").parse(start));
                    newTask.setEnd(new SimpleDateFormat("HH:mm:ss").parse(end));
                    new Thread(new AsyncAddToDatabase(newTask)).start();
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
                } catch(Exception e) { // Unknown error: need to debug
                    Toast.makeText(getBaseContext(),
                            "Unknown error",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ToDoAdd.this, ToDoListManager.class));
            }
        });
    }
}
