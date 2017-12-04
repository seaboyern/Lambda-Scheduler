package com.example.lambda.lambdaorganizer;

import entities.Task;
import android.app.DialogFragment;

public interface TaskDisplay {
    public void deleteTask(final Task task, final String message);
    public void addTask(final Task newTask, final String message);
    public void showDialog(DialogFragment dialog, String tag);
}
