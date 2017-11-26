package com.example.lambda.lambdaorganizer.Scheduler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lambda.lambdaorganizer.FormatDateTime;
import com.example.lambda.lambdaorganizer.R;
import com.example.lambda.lambdaorganizer.ToDo.ToDoView;

import java.text.ParseException;

import entities.Task;

/**
 * Created by mahmudfasihulazam on 2017-11-25.
 */

public class TaskEditDialog extends DialogFragment {
    Task task;
    View view;

    /**
     * Form fields:
     */
    private EditText dateEdit;
    private EditText startEdit;
    private EditText endEdit;
    private EditText descEdit;
    private EditText priorEdit;

    protected class SubmitListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            try {
                String date = dateEdit.getText().toString();
                String start = startEdit.getText().toString();
                String end = endEdit.getText().toString();
                String description = descEdit.getText().toString();
                String priority = priorEdit.getText().toString();
                final Task newTask = new Task(task.getTitle(), description, Integer.parseInt(priority));
                newTask.setTimeBounds(date, start, end);
                final Task oldTask = task;

                final String message = task.getTitle().equals(newTask.getTitle())
                        ? "Successfully edited task " + newTask.getTitle()
                        : "Successfully changed task " + task.getTitle() + " to "
                            + newTask.getTitle();
                final ToDoView v = (ToDoView)getActivity();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        v.deleteTask(oldTask, null);
                        v.addTask(newTask, message);
                    }
                }).start();
//                v.editTask(task, newTask, message);
            } catch(NumberFormatException e) { // Error in priority format
                Toast.makeText(getActivity().getApplicationContext(),
                        "Priority must be a number",
                        Toast.LENGTH_SHORT).show();
                return;
            } catch(RuntimeException e) { // Error in  date or time format
                Toast.makeText(getActivity().getApplicationContext(),
                        "Date format must be: yyyy-MM-dd\nTime format must be: HH:MM:SS",
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstaceState) {
        try {
            this.task = TaskInfoDialog.buildTaskFromBundle(getArguments());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Build the embedded view:
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.todoedit, null);
        this.view = view;
        this.dateEdit  = (EditText) this.view.findViewById(R.id.txtDate);
        this.startEdit  = (EditText) this.view.findViewById(R.id.txtStart);
        this.endEdit  = (EditText) this.view.findViewById(R.id.txtEnd);
        this.descEdit  = (EditText) this.view.findViewById(R.id.txtDesc);
        this.priorEdit  = (EditText) this.view.findViewById(R.id.txtPrior);

        // Populate fields with Task information:
        this.dateEdit.setText(FormatDateTime.getDateStringFromDate(this.task.getDate()));
        this.startEdit.setText(FormatDateTime.getTimeStringFromDate(this.task.getStart()));
        this.endEdit.setText(FormatDateTime.getTimeStringFromDate(this.task.getEnd()));
        this.descEdit.setText(this.task.getDesc());
        this.priorEdit.setText(String.format("%d", this.task.getPrio()));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        return builder.setView(this.view)
                    .setTitle("Edit task: " + this.task.getTitle())
                    .setPositiveButton("Submit", new SubmitListener())
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dismiss();
                        }
                    })
                    .create();
    }

}
