package com.example.lambda.lambdaorganizer.ToDo;

import java.text.SimpleDateFormat;

import entities.Task;

/**
 * Created by mahmudfasihulazam on 2017-11-21.
 */

public class TaskView {
    private Task task;
    private static final String TASK_DISPLAY_FMT = "%s:\n  on %s\n  from %s to %s";

    public TaskView(Task t) {
        this.setTask(t);
    }

    public static String getTaskDisplayFmt() {
        return TASK_DISPLAY_FMT;
    }

    @Override
    public String toString() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(this.getTask().getDate());
        String start = new SimpleDateFormat("HH:mm:ss").format(this.getTask().getStart());
        String end = new SimpleDateFormat("HH:mm:ss").format(this.getTask().getEnd());
        return String.format(getTaskDisplayFmt(), this.getTask().getTitle(),
                date,
                start,
                end);
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
