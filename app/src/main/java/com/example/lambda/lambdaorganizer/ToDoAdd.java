package com.example.lambda.lambdaorganizer;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;

import database.DatabaseHelper;
import database.schema.TaskContract;
import entities.Task;

/**
 * Created by ReedPosehn on 2017-10-12.
 */

public class ToDoAdd extends AppCompatActivity {
    public final String TAG = "ToDoAdd";

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
            DatabaseHelper dbOpener = new DatabaseHelper(getBaseContext());
            SQLiteDatabase db_w = dbOpener.getWritableDatabase();
            try {
            } catch(Exception e) {
            } finally {
                dbOpener.close();
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
                    Task newTask =
                            new Task(name, date, start, end, description,
                                    Integer.parseInt(priority));
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
