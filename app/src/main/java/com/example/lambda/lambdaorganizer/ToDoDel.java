package com.example.lambda.lambdaorganizer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.LinkedList;

import database.tables.CommitmentTable;
import database.tables.TaskTable;
import entities.Task;

/**
 * Created by ReedPosehn on 2017-10-12.
 */

public class ToDoDel extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tododel);


        /**
         * Buttons:
         */
        final Button subButton = (Button) findViewById(R.id.btnSubmit);
        final Button backButton = (Button) findViewById(R.id.btnBack);

        /**
         * Form
         */
        final EditText tEdit =  (EditText) findViewById(R.id.txtdTaskName);

        // Submit handling:
        subButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    String task = tEdit.getText().toString();
                    LinkedList<Task> list = TaskTable.getInstance(getBaseContext()).selectByTitle(task);
                    TaskTable.getInstance(getBaseContext()).remove(list.getFirst());
                    CommitmentTable.getInstance(getBaseContext()).remove(list.getFirst());
                    Toast.makeText(getBaseContext(),
                            "Task deleted!",
                            Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(getBaseContext(),
                            "Error deleting task from database; Cannot be found",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ToDoDel.this, ToDoListManager.class));
            }
        });
    }
}
