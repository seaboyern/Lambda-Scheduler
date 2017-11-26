package com.example.lambda.lambdaorganizer.Scheduler;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.app.Dialog;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.util.Log;

import com.example.lambda.lambdaorganizer.FormatDateTime;
import com.example.lambda.lambdaorganizer.ToDo.ToDoView;

import java.text.ParseException;

import database.tables.TaskTable;
import entities.Task;

public class TaskInfoDialog extends DialogFragment {

    private static final String TAG = "TaskInfoDialog";

    private static final String displayFormat = "Description: %s\nPriority: %s\nStart: %s\nEnd: %s";
    private static final String[] priorities = {"Urgent", "Moderate", "Passive"};

    Task task;

    public static final Task buildTaskFromBundle(Bundle b) throws ParseException {
        String title = b.getString("title");
        String description = b.getString("desc");
        int priority = b.getInt("prio");

        Task ret = new Task(title, description, priority);
        ret.setTimeBounds(b.getString("date"), b.getString("start"),
                b.getString("end"));

        return ret;
    }

    public static final Bundle buildBundleFromTask(Task t) {
        Bundle b = new Bundle();
        Log.d(TAG, t.toString());
        b.putString("title", t.getTitle());
        b.putString("desc", t.getDesc());
        b.putInt("prio", t.getPrio());
        b.putString("date", FormatDateTime.getDateStringFromDate(t.getDate()));
        b.putString("start", FormatDateTime.getTimeStringFromDate(t.getStart()));
        b.putString("end", FormatDateTime.getTimeStringFromDate(t.getEnd()));
        return b;
    }

    private class TaskEditListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            //
        }
    }

    private class TaskDeleteListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            // Delete task from database asynchronously:
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ToDoView v = (ToDoView)getActivity();
                    v.deleteTask(task);
                    dismiss();
                }
            }).start();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get the task for which to generate dialog:
        try {
            this.task = buildTaskFromBundle(this.getArguments());
        } catch(Exception e) {
            e.printStackTrace();
        }

        String prio = priorities[task.getPrio() - 1];

        String dialogMessage = String.format(displayFormat,
                task.getDesc(), prio + "(" + task.getPrio() + ")", task.getStart(), task.getEnd());

        // Set up dialog and listeners for buttons:
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(task.getTitle())
                .setMessage(dialogMessage)
                .setPositiveButton("Edit", new TaskEditListener())
                .setNegativeButton("Delete", new TaskDeleteListener())
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       // dismiss dialog
                   }
               });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
