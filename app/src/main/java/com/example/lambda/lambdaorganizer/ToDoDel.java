package com.example.lambda.lambdaorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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

        // Submit handling:
        subButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ToDoDel.this, ToDoListManager.class));
            }
        });
    }
}
