package com.example.lambda.lambdaorganizer;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.lang.Runnable ;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.graphics.RectF;
import android.widget.Toast;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.util.Log;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import entities.Task;
import entities.Assignment;
import com.example.lambda.lambdaorganizer.ToDo.TaskInfoDialog;
import com.example.lambda.lambdaorganizer.ToDo.TaskAddDialog;
import com.example.lambda.lambdaorganizer.TaskDisplay;
import database.tables.TaskTable;
import database.tables.CommitmentTable;

public class ScheduleOverview extends AppCompatActivity implements WeekView.EventClickListener,
        MonthLoader.MonthChangeListener, WeekView.EmptyViewClickListener,
        TaskDisplay,
        WeekView.EventLongPressListener{

    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 3;
    private static final int TYPE_WEEK_VIEW = 7;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private int mEventIDCounter = 0;
    private WeekView mWeekView;
    private List<WeekViewEvent> mEvents;
    private List<WeekViewEvent> mTaskEvents;
    private List<WeekViewEvent> mAssignmentEvents;
    private List<Task> mTasks;
    private List<Assignment> mAssignments;
    private Map<WeekViewEvent, Task> mEventToTask;
    private Map<WeekViewEvent, Assignment> mEventToAssignment;
    private boolean viewAssignments = true;
    private boolean viewTasks = true;
    private boolean viewEvents = true;
    private CaldroidFragment caldroidFragment;
    private static final String TAG = "ScheduleOverview";
    protected SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_overview);

        mEvents = new LinkedList<WeekViewEvent>();
        mAssignmentEvents = new LinkedList<WeekViewEvent>();
        mTaskEvents = new LinkedList<WeekViewEvent>();
        mTasks = new LinkedList<Task>();
        mAssignments = new LinkedList<Assignment>();
        mEventToTask = new HashMap<WeekViewEvent, Task>();
        mEventToAssignment = new HashMap<WeekViewEvent, Assignment>();

        //////////////////////
        // Set up Week view //
        // ///////////////////

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Handle events generated when user clicks on events and empty space
        mWeekView.setOnEventClickListener(this);
        mWeekView.setEmptyViewClickListener(this);
        mWeekView.setEventLongPressListener(this);

        mWeekView.setNumberOfVisibleDays(mWeekViewType);

        ///////////////////////
        // Set up Month View //
        ///////////////////////

        initializeMonthView();


        ///////////////////////////////
        // Set up some example tasks //
        ///////////////////////////////

        Assignment a = new Assignment("Assignment", "desc", 1);
        Date d = new Date();
        d.setHours(0);
        a.setDeadline(d);
        addAssignment(a);

        getTasksDB();

        Task tsk = new Task("Task1", "desc", 1);
        Date start = new Date();
        start.setHours(2);
        Date end = new Date();
        end.setHours(3);
        tsk.setStart(start);
        tsk.setEnd(end);
        tsk.setDate(start);
        // addTaskNoDB(tsk);

    }

    private void getAssignmentDB() {

    }

    private void getTasksDB() {
        TaskTable table = TaskTable.getInstance(getBaseContext());
        LinkedList<Task> tasks = table.selectAll();
        Log.v(TAG, "DB"+table.toString());
        for(Task t : tasks) {
            addTaskNoDB(t);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWeekView.notifyDatasetChanged();
            }
        });
    }

    /**
     * Set up the caldroid based month view.
     * Also usefull to clear the colors on the month view.
     */
    private void initializeMonthView() {
        caldroidFragment = new CaldroidFragment();
        CaldroidListener caldroidListener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                Toast.makeText(getApplicationContext(), dateFormatter.format(date),
                        Toast.LENGTH_SHORT).show();
                // Hide the caldroid calendar and show the week view
                LinearLayout caldroid = (LinearLayout)ScheduleOverview.this.findViewById(R.id.caldroid);
                caldroid.setVisibility(View.GONE);
                mWeekView.setNumberOfVisibleDays(TYPE_DAY_VIEW);
                mWeekView.setVisibility(View.VISIBLE);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                mWeekView.goToDate(cal);
            }
        };
        caldroidFragment.setCaldroidListener(caldroidListener);

        // Get the current date
        Calendar cal = Calendar.getInstance();

        // Arguments for creating the Caldroid calendar
        Bundle args = new Bundle();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

        android.support.v4.app.FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        // Turn the Linear layout in the activity_schedule_overview
        // layout into a Caldroid calendar
        tr.replace(R.id.caldroid, caldroidFragment);
        tr.commit();
    }

    /**
     * Create a week view event from a Task.
     * @param t a task with start and end times set.
     * @return A new week view event.
     */
    private WeekViewEvent taskToEvent(Task t) {
        Calendar startTime = Calendar.getInstance();
        startTime.setTime(t.getDate());
        startTime.set(Calendar.HOUR, t.getStart().getHours());
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(t.getDate());
        endTime.set(Calendar.HOUR, t.getEnd().getHours());
        WeekViewEvent event = new WeekViewEvent(
                mEventIDCounter, t.getTitle(), startTime, endTime);
        mEventIDCounter++;
        event.setColor(getResources().getColor(R.color.task_color));
        return event;
    }

    /**
     * Create a week view event from an Assignment.
     * @param a an Assignment with the deadline set.
     * @return A new week view event.
     */
    private WeekViewEvent assignmentToEvent(Assignment a) {
        Calendar deadline = Calendar.getInstance();
        deadline.setTime(a.getDeadline());
        Calendar endTime = (Calendar)deadline.clone();
        endTime.add(Calendar.HOUR, 1);
        WeekViewEvent event = new WeekViewEvent(
                mEventIDCounter, a.getTitle(), deadline, endTime);
        mEventIDCounter++;
        // event.setAllDay(true);
        event.setColor(getResources().getColor(R.color.assignment_color));
        return event;

    }

    /**
     * Adds an Assignment to the internal data structures for the week view.
     * @param a An Assignment with the dealine set.
     */
    public void addAssignment(Assignment a) {
        WeekViewEvent e = assignmentToEvent(a);
        mEventToAssignment.put(e, a);
        mAssignmentEvents.add(e);
        mAssignments.add(a);
    }

    /**
     * Add a Task to the internal data structures for the week view.
     * @param t A task with the start end end times set.
     */
    public void addTask(Task t, final String message) {
        Log.v(TAG, "ADD: "+t.toString());
        TaskTable.getInstance(getApplicationContext()).insert(t);
        CommitmentTable.getInstance(getApplicationContext()).insert(t);
        addTaskNoDB(t);
        Log.v(TAG, "DB"+TaskTable.getInstance(getApplicationContext()).toString());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWeekView.notifyDatasetChanged();
            }
        });
    }

    public void addTaskNoDB(Task t) {
        Log.v(TAG, "ADD noDB: "+t.toString());
        WeekViewEvent e = taskToEvent(t);
        mEventToTask.put(e, t);
        mTaskEvents.add(e);
        mTasks.add(t);
    }

    public void deleteTask(Task t, final String message) {
        Log.v(TAG, "DELETE"+ t.toString());
        // mTasks.remove(t);
        LinkedList<Task> list = TaskTable.getInstance(
                getApplicationContext()).selectByTitle(t.getTitle());
        TaskTable.getInstance(getApplicationContext()).remove(list.getFirst());
        CommitmentTable.getInstance(getApplicationContext()).remove(list.getFirst());
        for (Map.Entry<WeekViewEvent, Task> e : mEventToTask.entrySet()) {
            Log.v(TAG, e.getValue().toString());
            if (e.getValue().toString().equals(t.toString())) {
                Log.v(TAG, e.getKey().getName());
                deleteEvent(e.getKey());
                break;
            }
        }
    }

    public void showDialog(DialogFragment dialog, String tag) {
        dialog.show(getFragmentManager(), tag);
    }

    /**
     * Creates an options menu when user taps the button in the top right corner.
     * @param menu
     * @return True if menu created successfully
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.scheduler_settings, menu);
        menu.findItem(R.id.showAssignments).setChecked(viewAssignments);
        menu.findItem(R.id.showTasks).setChecked(viewTasks);
        menu.findItem(R.id.showEvents).setChecked(viewEvents);
        return true;
    }

    /**
     * Called when the user selects an option from the options menu.
     * @param item
     * @return True if selection processed successfully.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get instance of the caldroid calendar view
        LinearLayout caldroid = (LinearLayout)this.findViewById(R.id.caldroid);

        switch(item.getItemId()) {
            case R.id.weekview:
                // Hide the caldroid calendar and show the week view
                caldroid.setVisibility(View.GONE);
                mWeekView.setNumberOfVisibleDays(mWeekViewType);
                mWeekView.setVisibility(View.VISIBLE);
                return true;
            case R.id.monthview:
                // Hide the week view and show the caldroid calendar
                refreshMonthView();
                mWeekView.setVisibility(View.GONE);
                caldroid.setVisibility(View.VISIBLE);
                return true;
            case R.id.showAssignments:
                viewAssignments = !item.isChecked();
                item.setChecked(!item.isChecked());
                refreshMonthView();
                mWeekView.notifyDatasetChanged();
                return true;
            case R.id.showTasks:
                viewTasks = !item.isChecked();
                item.setChecked(!item.isChecked());
                refreshMonthView();
                mWeekView.notifyDatasetChanged();
                return true;
            case R.id.showEvents:
                viewEvents = !item.isChecked();
                item.setChecked(!item.isChecked());
                refreshMonthView();
                mWeekView.notifyDatasetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Add colors to the month view based on the type of event.
     */
    private void refreshMonthView() {
        initializeMonthView(); // Clear colors
        ColorDrawable event_color = new ColorDrawable(getResources().getColor(R.color.event_color));
        ColorDrawable task_color = new ColorDrawable(getResources().getColor(R.color.task_color));
        ColorDrawable assign_color = new ColorDrawable(getResources().getColor(R.color.assignment_color));
        HashMap<Date, Drawable> colorMap = new HashMap<Date, Drawable>();
        if(viewTasks){
            for (Task t : mTasks) {
                if(!colorMap.containsKey(t.getStart())) {
                    colorMap.put(t.getStart(), task_color);
                }
            }
        }
        if(viewEvents) {
            for (WeekViewEvent e : mEvents) {
                if(!colorMap.containsKey(e.getStartTime().getTime())) {
                    colorMap.put(e.getStartTime().getTime(), event_color);
                }
            }
        }
        if(viewAssignments){
            for (Assignment a : mAssignments) {
                if(!colorMap.containsKey(a.getDeadline())) {
                    colorMap.put(a.getDeadline(), assign_color);
                }
            }
        }
        caldroidFragment.setBackgroundDrawableForDates(colorMap);
        caldroidFragment.refreshView();
    }

    /**
     * Callback when user touches an event on the weekly view.
     * @param event
     * @param eventRect
     */
    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        showEvent(event);
        // editEvent(event);
    }

    /**
     * Callback when user long presses an event on the weekly view.
     * @param event
     * @param eventRect
     */
    @Override
    public void onEventLongPress(final WeekViewEvent event, RectF eventRect) {
        new AlertDialog.Builder(this)
            .setTitle("Confirm")
            .setMessage("Are you sure you want to delete this task?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int button) {
                    deleteEvent(event);
                }
            })
            .setNegativeButton("No", null).show();
    }

    /**
     * Callback when user touches an empty space on the weekly view.
     * @param time: The time corresponding to the empty space.
     */
    @Override
    public void onEmptyViewClicked(Calendar time) {
        Calendar endTime = (Calendar) time.clone();
        endTime.add(Calendar.HOUR, 1);

        showDialog(new TaskAddDialog(), "New task");
        // addEvent("Test", time, endTime);
    }

    /**
     * Create an event title to show on the weekly schedule
     * @param time: A Calendar object that stores the time of the event
     * @return String representing the event title.
     */
    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY),
                time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Checks if a WeekView Event matches the integer month
     * @param e a WeekViewEvent with a start time set.
     * @param month the month of the year as an integer
     * @return true if the WeekViewEvent is in the compared month.
     */
    private boolean eventMatchesMonth(WeekViewEvent e, int month) {
        return e.getStartTime().get(Calendar.MONTH) == month;
    }


    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        // Populate the week view with some events.
        // Work around Week view library adding the events 3 times
        ArrayList<WeekViewEvent> eventsMonth = new ArrayList<WeekViewEvent>();
        if(viewEvents) {
            for (WeekViewEvent e : mEvents) {
                if (eventMatchesMonth(e, newMonth)) {
                    eventsMonth.add(e);
                }
            }
        }
        if(viewAssignments) {
            for (WeekViewEvent e : mAssignmentEvents) {
                if (eventMatchesMonth(e, newMonth)) {
                    eventsMonth.add(e);
                }
            }
        }
        if(viewTasks) {
            for (WeekViewEvent e : mTaskEvents) {
                if (eventMatchesMonth(e, newMonth)) {
                    eventsMonth.add(e);
                }
            }
        }
        Log.v(TAG, "Month load");
        for (WeekViewEvent e : mTaskEvents) {
            Log.v(TAG, e.getName());
        }
        return eventsMonth;
    }

    /**
     * Checks if two WeekViewEvent objects are equal.
     * @param event1 The first WeekViewEvent.
     * @param event2 The second WeekViewEvent.
     * @return True if the objects represent the same event.
     */
    private boolean eventsEqual(WeekViewEvent event1, WeekViewEvent event2) {
        return (event1.getStartTime() == event2.getStartTime())
            && (event1.getEndTime() == event2.getEndTime())
            && (event1.getName() == event2.getName())
            && (event1.getId() == event2.getId());
    }

    /**
     * Add a new task to the weekly view
     * @param title
     * @param startTime
     * @param endTime
     */
    public void addEvent(String title, Calendar startTime, Calendar endTime) {
        WeekViewEvent event = new WeekViewEvent(mEventIDCounter, getEventTitle(startTime), startTime, endTime);
        mEventIDCounter++;
        event.setColor(getResources().getColor(R.color.event_color));
        Log.v(TAG, startTime.toString());
        Log.v(TAG, endTime.toString());

        mEvents.add(event);
        editEvent(event);

        mWeekView.notifyDatasetChanged();
        for (WeekViewEvent e : mEvents) {
            Log.v(TAG, e.getName());
        }
    }

    /**
     * Display the details of the task clicked.
     * @param event
     */
    public void showEvent(final WeekViewEvent event) {
        if(mEventToTask.containsKey(event)) {
            Task t = (Task)mEventToTask.get(event);
            TaskInfoDialog infoscreen = new TaskInfoDialog();
            Bundle taskBundle = TaskInfoDialog.buildBundleFromTask(t);
            infoscreen.setArguments(taskBundle);
            infoscreen.show(getSupportFragmentManager(), "Task info");
        } else if(mEventToAssignment.containsKey(event)) {
            Assignment a = (Assignment)mEventToAssignment.get(event);
            TaskInfoDialog infoscreen = new TaskInfoDialog();
            Bundle args = new Bundle();
            args.putString("info", a.toString());
            infoscreen.setArguments(args);
            infoscreen.show(getSupportFragmentManager(), "Assignment info");
        } else {
            TaskInfoDialog infoscreen = new TaskInfoDialog();
            Bundle args = new Bundle();
            args.putString("info", event.getName());
            infoscreen.setArguments(args);
            infoscreen.show(getSupportFragmentManager(), "Assignment info");
        }

    }

    public void editEvent(final WeekViewEvent event) {
        // Example of how to use the date time picker
        class ShowTaskListener implements DateTimePicker.DateTimeListener {
            @Override
            public void onDateTimePickerConfirm(Date d) {
                Calendar newStart = Calendar.getInstance();
                Log.v(TAG, d.toString());
                newStart.set(d.getYear(), d.getMonth(), d.getDate(),
                        d.getHours(), d.getMinutes());
                Log.v(TAG, newStart.toString());
                event.setStartTime(newStart);
                Calendar newEnd = (Calendar)newStart.clone();
                newEnd.add(Calendar.HOUR, 1);
                event.setEndTime(newEnd);
                if (mEventToTask.containsKey(event)) {
                    // Update task, and update database
                    Task t = mEventToTask.get(event);
                    TaskTable.getInstance(getBaseContext()).remove(t);
                    t.setStart(newStart.getTime());
                    t.setEnd(newEnd.getTime());
                    TaskTable.getInstance(getBaseContext()).insert(t);
                }
                mWeekView.notifyDatasetChanged();
            }
        }
        DateTimePicker datetimepicker = new DateTimePicker();
        datetimepicker.setDateTimeListener(new ShowTaskListener());
        datetimepicker.show(getSupportFragmentManager(), "date time picker");
    }

    public void deleteEvent(WeekViewEvent event) {
        ArrayList<WeekViewEvent> UpdatedList = new ArrayList<WeekViewEvent>();
        if(mEvents.contains(event)) {
            for(WeekViewEvent e: mEvents) {
                if(!eventsEqual(e, event)) {
                    UpdatedList.add(e);
                }
            }
            mEvents = UpdatedList;
        } else if(mAssignmentEvents.contains(event)) {
            for(WeekViewEvent e: mAssignmentEvents) {
                if(!eventsEqual(e, event)) {
                    UpdatedList.add(e);
                }
            }
            mEventToAssignment.remove(event);
            mAssignmentEvents = UpdatedList;
        } else if(mTaskEvents.contains(event)) {
            for(WeekViewEvent e: mTaskEvents) {
                if(!eventsEqual(e, event)) {
                    UpdatedList.add(e);
                }
            }
            mEventToTask.remove(event);
            mTaskEvents = UpdatedList;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWeekView.notifyDatasetChanged();
            }
        });
    }
}
