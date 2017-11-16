package com.example.lambda.lambdaorganizer;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.graphics.RectF;
import android.widget.Toast;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.util.Log;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.alamkanak.weekview.WeekViewLoader;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import entities.Task;
import com.example.lambda.lambdaorganizer.Scheduler.TaskInfoDialog;

public class ScheduleOverview extends AppCompatActivity implements WeekView.EventClickListener,
        MonthLoader.MonthChangeListener, WeekView.EmptyViewClickListener,
        WeekViewLoader, DateTimePicker.DateTimeListener,
        WeekView.EventLongPressListener{

    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 3;
    private static final int TYPE_WEEK_VIEW = 7;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private int mEventIDCounter = 0;
    private WeekView mWeekView;
    private List<WeekViewEvent> mEvents;
    private Map<WeekViewEvent, Task> mEventToTask;
    private static final String TAG = "ScheduleOverview";
    protected SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_overview);

        mEvents = new ArrayList<WeekViewEvent>();
        mEventToTask = new HashMap<WeekViewEvent, Task>();

        //////////////////////
        // Set up Week view //
        // ///////////////////

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);
        // mWeekView.setWeekViewLoader(this);

        // Handle events generated when user clicks on events and empty space
        mWeekView.setOnEventClickListener(this);
        mWeekView.setEmptyViewClickListener(this);
        mWeekView.setEventLongPressListener(this);

        mWeekView.setNumberOfVisibleDays(mWeekViewType);

        ///////////////////////
        // Set up Month View //
        ///////////////////////

        CaldroidFragment caldroidFragment = new CaldroidFragment();
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

        android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        // Turn the Linear layout in the activity_schedule_overview
        // layout into a Caldroid calendar
        t.replace(R.id.caldroid, caldroidFragment);
        t.commit();
    }

    public WeekView getWeekView() {
        return mWeekView;
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
                Toast.makeText(this, "Week view", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.monthview:
                // Hide the week view and show the caldroid calendar
                mWeekView.setVisibility(View.GONE);
                caldroid.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Month view", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Callback when user touches an event on the weekly view.
     * @param event
     * @param eventRect
     */
    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        showTask(event);
    }

    /**
     * Callback when user long presses an event on the weekly view.
     * @param event
     * @param eventRect
     */
    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        deleteTask(event);
    }

    /**
     * Callback when user touches an empty space on the weekly view.
     * @param time: The time corresponding to the empty space.
     */
    @Override
    public void onEmptyViewClicked(Calendar time) {
        Calendar endTime = (Calendar) time.clone();
        endTime.add(Calendar.HOUR, 1);

        addTask("Test", time, endTime);
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


    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        // Populate the week view with some events.
        // Work around Week view library adding the events 3 times
        ArrayList<WeekViewEvent> eventsMonth = new ArrayList<WeekViewEvent>();
        for (int i = 0; i < mEvents.size(); i++) {
            if (mEvents.get(i).getStartTime().get(Calendar.MONTH) == newMonth) {
                eventsMonth.add(mEvents.get(i));
            }
        }
        Log.v(TAG, "onMonthChange");
        return eventsMonth;
    }

    @Override
    public List<? extends WeekViewEvent> onLoad(int periodIndex) {
        ArrayList<WeekViewEvent> eventsMonth = new ArrayList<WeekViewEvent>();
        for (int i = 0; i < mEvents.size(); i++) {
            if (mEvents.get(i).getStartTime().get(Calendar.DAY_OF_MONTH) == periodIndex) {
                eventsMonth.add(mEvents.get(i));
            }
        }
        Log.v(TAG, "onLoad");
        return eventsMonth;
    }

    @Override
    public double toWeekViewPeriodIndex(Calendar instance) {
        return instance.get(Calendar.DAY_OF_MONTH);
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
    public void addTask(String title, Calendar startTime, Calendar endTime) {
        WeekViewEvent event = new WeekViewEvent(mEventIDCounter, getEventTitle(startTime), startTime, endTime);
        mEventIDCounter++;
        event.setColor(getResources().getColor(R.color.event_color_01));
        Log.v(TAG, startTime.toString());
        Log.v(TAG, endTime.toString());

        Task t = new Task(event.getName(), "Description of "+event.getName(), 1);
        t.setDate(startTime.getTime());
        t.setStart(startTime.getTime());
        t.setEnd(endTime.getTime());

        mEvents.add(event);

        mEventToTask.put(event, t);

        mWeekView.notifyDatasetChanged();
        for (WeekViewEvent e : mEvents) {
            Log.v(TAG, e.getName());
        }
    }

    @Override
    public void onDateTimePickerConfirm(Date d) {
        Calendar startTime = Calendar.getInstance();
        startTime.setTime(d);
        Calendar endTime = (Calendar)startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        Log.v(TAG, startTime.toString());
        Log.v(TAG, endTime.toString());
        WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime), startTime, endTime);
        mEvents.add(event);

        mWeekView.notifyDatasetChanged();
        for (WeekViewEvent e : mEvents) {
            Log.v(TAG, e.getName());
        }
        Toast.makeText(ScheduleOverview.this, "TODO: Open event " + event.getName(), Toast.LENGTH_SHORT).show();
    }
    /**
     * Display the details of the task clicked.
     * @param event
     */
    public void showTask(final WeekViewEvent event) {
        // Example of how to use the date time picker
        // class ShowTaskListener implements DateTimePicker.DateTimeListener {
        //     @Override
        //     public void onDateTimePickerConfirm(Date d) {
        //         Calendar newStart = Calendar.getInstance();
        //         newStart.setTime(d);
        //         event.setStartTime(newStart);
        //         // Toast.makeText(ScheduleOverview.this, "TODO: Open event " + d, Toast.LENGTH_SHORT).show();
        //     }
        // }
        // DateTimePicker datetimepicker = new DateTimePicker();
        // datetimepicker.setDateTimeListener(this);
        // datetimepicker.show(getSupportFragmentManager(), "date time picker");
        Task t = (Task)mEventToTask.get(event);
        TaskInfoDialog infoscreen = new TaskInfoDialog();
        Bundle args = new Bundle();
        args.putString("info", t.toString());
        infoscreen.setArguments(args);
        infoscreen.show(getSupportFragmentManager(), "Task info");
    }

    public void deleteTask(WeekViewEvent event) {
        ArrayList<WeekViewEvent> UpdatedList = new ArrayList<WeekViewEvent>();
        for(WeekViewEvent e: mEvents) {
            if(!eventsEqual(e, event)) {
                UpdatedList.add(e);
            }
        }
        mEvents = UpdatedList;
        mEventToTask.remove(event);
        mWeekView.notifyDatasetChanged();
    }
}
