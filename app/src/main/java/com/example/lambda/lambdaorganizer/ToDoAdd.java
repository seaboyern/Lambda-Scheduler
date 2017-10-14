package com.example.lambda.lambdaorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

/**
 * Created by ReedPosehn on 2017-10-12.
 */

public class ToDoAdd extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todoadd);

        final Button subButton = (Button) findViewById(R.id.btnSubmit);
        final Button backButton = (Button) findViewById(R.id.btnBack);

        final EditText nameEdit  = (EditText) findViewById(R.id.txtName);
        final EditText dateEdit  = (EditText) findViewById(R.id.txtDate);
        final EditText startEdit  = (EditText) findViewById(R.id.txtStart);
        final EditText endEdit  = (EditText) findViewById(R.id.txtEnd);
        final EditText descEdit  = (EditText) findViewById(R.id.txtDesc);
        final EditText priorEdit  = (EditText) findViewById(R.id.txtPrior);

        subButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("Name", nameEdit.getText().toString());
                Log.v("Date", dateEdit.getText().toString());
                Log.v("Start", startEdit.getText().toString());
                Log.v("End", endEdit.getText().toString());
                Log.v("Description", descEdit.getText().toString());
                Log.v("Priority", priorEdit.getText().toString());
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ToDoAdd.this, ToDoListManager.class));
            }
        });
    }
}
