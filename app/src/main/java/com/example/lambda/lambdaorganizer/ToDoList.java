package com.example.lambda.lambdaorganizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by ReedPosehn on 2017-10-05.
 */

public class ToDoList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);

        // Declaration of objects
        final Button addButton = (Button) findViewById(R.id.button);
        final Button delButton = (Button) findViewById(R.id.button2);
        final Button edButton = (Button) findViewById(R.id.button3);
        final Button viewButton = (Button) findViewById(R.id.button4);

        // Button initialization
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ToDoList.this, ToDoAdd.class));
            }
        });
        delButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ToDoList.this, ToDoDel.class));
            }
        });
        edButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ToDoList.this, ToDoEdit.class));
            }
        });
        viewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ToDoList.this, ToDoView.class);
                startActivity(intent);
            }
        });
    }
}
