package com.example.lambda.lambdaorganizer.ToDo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lambda.lambdaorganizer.R;
import com.example.lambda.lambdaorganizer.ToDo.ToDoView;

import entities.Task;

/**
 * Created by mahmudfasihulazam on 2017-11-26.
 */

public class TaskAddDialog extends DialogFragment {

    private EditText nameEdit;
    private EditText dateEdit;
    private EditText startEdit;
    private EditText endEdit;
    private EditText descEdit;
    private EditText priorEdit;

    private class SubmitHandler implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            String name = nameEdit.getText().toString();
            String date = dateEdit.getText().toString();
            String start = startEdit.getText().toString();
            String end = endEdit.getText().toString();
            String description = descEdit.getText().toString();
            String priority = priorEdit.getText().toString();
            try {
                final Task newTask = new Task(name, description, Integer.parseInt(priority));
                newTask.setTimeBounds(date, start, end);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ToDoView v = (ToDoView)getActivity();
                        v.addTask(newTask,
                                "Successfully added task " + newTask.getTitle());
                    }
                }).start();

            } catch(NumberFormatException e) { // Error in priority format
                Toast.makeText(getActivity().getApplicationContext(),
                        "Priority must be a number",
                        Toast.LENGTH_SHORT).show();
                return;
            } catch(RuntimeException e) { // Error in  date or time format
                Toast.makeText(getActivity().getApplicationContext(),
                        "Date format must be: yyyy-MM-dd\nTime format must be: HH:MM:SS",
                        Toast.LENGTH_SHORT).show();
            } catch(Exception e) { // Unknown error: need to debug
                Toast.makeText(getActivity().getApplicationContext(),
                        "Unknown error",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.todoadd, null);

        nameEdit  = (EditText) view.findViewById(R.id.txtName);
        dateEdit  = (EditText) view.findViewById(R.id.txtDate);
        startEdit  = (EditText) view.findViewById(R.id.txtStart);
        endEdit  = (EditText) view.findViewById(R.id.txtEnd);
        descEdit  = (EditText) view.findViewById(R.id.txtDesc);
        priorEdit  = (EditText) view.findViewById(R.id.txtPrior);

        return builder.setTitle("New task")
                .setView(view)
                .setPositiveButton("Submit", new SubmitHandler())
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                })
                .create();
    }
}
