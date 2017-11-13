package com.example.lambda.lambdaorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import entities.Task;

/**
 * Created by ReedPosehn on 2017-10-12.
 */

public class ToDoEdit extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todoedit);

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

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ToDoEdit.this, ToDoListManager.class));
            }
        });
    }
}
