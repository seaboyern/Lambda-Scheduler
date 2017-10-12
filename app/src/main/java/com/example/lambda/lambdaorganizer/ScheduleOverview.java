package com.example.lambda.lambdaorganizer;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.graphics.RectF;
import android.widget.Toast;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

public class ScheduleOverview extends AppCompatActivity implements WeekView.EventClickListener,
        MonthLoader.MonthChangeListener, WeekView.EmptyViewClickListener {

    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private WeekView mWeekView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_overview);

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

        ///////////////////////
        // Set up Month View //
        ///////////////////////

        CaldroidFragment caldroidFragment = new CaldroidFragment();

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
     * Callback when user touches an empty space on the weekly view.
     * @param time: The time corresponding to the empty space.
     */
    @Override
    public void onEmptyViewClicked(Calendar time) {
        addTask("Test", time);
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
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        endTime.set(Calendar.MONTH, newMonth - 1);
        WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);

        return events;
    }

    /**
     * Add a new task to the weekly view
     * @param title
     * @param time
     */
    public void addTask(String title, Calendar time) {
        Toast.makeText(this, "TODO: Create new event.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display the details of the task clicked.
     * @param event
     */
    public void showTask(WeekViewEvent event) {
        Toast.makeText(this, "TODO: Open event " + event.getName(), Toast.LENGTH_SHORT).show();
    }
}
