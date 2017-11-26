package com.example.lambda.lambdaorganizer.ToDo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lambda.lambdaorganizer.MainActivity;
import com.example.lambda.lambdaorganizer.R;

/**
 * Created by ReedPosehn on 2017-10-05.
 */

public class ToDoListManager extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);

        // Declaration of objects
        final Button addButton = (Button) findViewById(R.id.button);
        final Button viewButton = (Button) findViewById(R.id.button4);
        final Button menButton = (Button) findViewById(R.id.btnMain);

        // Button initialization
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ToDoListManager.this, ToDoAdd.class));
            }
        });
        viewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ToDoListManager.this, ToDoView.class));
            }
        });
        menButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ToDoListManager.this, MainActivity.class));
            }
        });
    }
}
