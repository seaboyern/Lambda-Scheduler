package com.example.lambda.lambdaorganizer.SessionEngineer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lambda.lambdaorganizer.DateTimePicker;
import com.example.lambda.lambdaorganizer.R;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import entities.StudyEvent;

import static android.R.attr.data;

/**
 * Created by priom on 11/8/2017.
 */

public class AddStudyTime extends AppCompatActivity  {

    private Button submitButton;
    private FloatingActionButton addEmailButton;
    private EditText eventSummary;
    private EditText eventDescription;
    private EditText eventLocation;
    private EditText eventStartTime;
    private EditText eventEndTime;
    private Spinner recurrenceDropdown;
    private TextView errorText;



    private TextView attendeesEmail;
    private ArrayList<String> attendees;

    private String eventRecurrenceFrequency;
    private TextView eventRecurrenceCount;

    private SimpleDateFormat dateTimeFormat = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
    private SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timeFormat = new java.text.SimpleDateFormat("hh:mm:ss");
    public static String TAG = "AddStudyTime";
    private Intent getIntent;

    static final int REQUEST_CODE_ADD_STUDY_TIME =42;

    String startDate = new String();
    String endDate = new String();
    String startTime = new String();
    String endTime = new String();
    String timeZone = new String();


    private String message = new String();

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_study_time);


        attendees = new ArrayList<String>();
        submitButton = (Button) findViewById(R.id.eventSubmitButton);
        addEmailButton = (FloatingActionButton) findViewById(R.id.addEmailButton);
        eventSummary = (EditText) findViewById(R.id.eventSummary);
        eventDescription = (EditText) findViewById(R.id.eventDescription);
        eventLocation = (EditText) findViewById(R.id.eventLocation);
        eventStartTime = (EditText) findViewById(R.id.eventStartTime);
        eventEndTime = (EditText) findViewById(R.id.eventEndTime);
        attendeesEmail = (EditText) findViewById(R.id.attendeesEmail);
        eventRecurrenceCount = (EditText) findViewById(R.id.eventRecurrenceCount);


        eventRecurrenceFrequency = new String();

        eventStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                dateTimePicker(v);



            }


        });

        eventEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                dateTimePicker(v);



            }


        });


        //get the spinner from the xml.
        recurrenceDropdown = (Spinner)findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        String[] items = new String[]{"DAILY", "WEEKLY"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        recurrenceDropdown.setAdapter(adapter);




        recurrenceDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                // On selecting a spinner item
                eventRecurrenceFrequency = adapter.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Toast.makeText(getApplicationContext(),
                        "Selected Recurrence Frequency : " + eventRecurrenceFrequency, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });



        errorText = (TextView) findViewById(R.id.inputError);
        try {
            if(getIntent()!=null){

                Log.d(TAG, "Got Request from SessonEngineer.");
                // There is a Bug is here. Need to implement it for auto populate date field.
//                    Bundle newBundle = getIntent.getExtras();
//                    if (!newBundle.isEmpty()) {
//                        Log.d(TAG, "Its here...................................");
//
//
//                        if (newBundle.containsKey("startTime") && newBundle.containsKey("endTime")) {
//
//                             try {
//
//                                eventStartTime.setText(newBundle.getString("startTime"));
//                                eventEndTime.setText(newBundle.getString("endTime"));
//                            } catch (Exception e) {
//                                errorText.setText(e.getMessage());
//                                Log.d(TAG, "Event Create Failed: " + e.getMessage());
//                            }
//
//                            Log.d(TAG, "Auto Field Populated : OK");
//                        } else {
//                            Log.d(TAG, "Auto Field Populated: No Ref. Found in newBundle.");
//
//                        }
//
//
//                    }


            }
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }


        addEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!attendeesEmail.getText().toString().isEmpty() && isValidEmail(attendeesEmail.getText().toString())){


                    attendees.add(attendeesEmail.getText().toString());
                    message = attendeesEmail.getText().toString()+" added to the attendees list";


                }else {

                    message = "Invalid Email Address.";
                }


                errorText.setText(message.toString());

            }


        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(eventDescription.getText().toString().isEmpty()){
                        message = "Event Description not set.";

                    }else if(eventStartTime.getText().toString().isEmpty() || !isTimeStampValid(eventStartTime.getText().toString())){

                        message = "eventStartTime not set.";

                    }else if (eventEndTime.getText().toString().isEmpty() || !isTimeStampValid(eventEndTime.getText().toString())){

                        message = "eventEndTime not set.";
                    }else {

                        message = "Event Created";
                        try {
                            Intent sendIntent = new Intent(getApplicationContext(), SessionEngineer.class);

                            sendIntent.putExtra("eventSummary", eventSummary.getText().toString());
                            sendIntent.putExtra("eventDescription", eventDescription.getText().toString());
                            sendIntent.putExtra("eventLocation", eventLocation.getText().toString());

                            sendIntent.putExtra("eventStartDate", startDate);
                            sendIntent.putExtra("eventEndDate", endDate);
                            sendIntent.putExtra("eventStartTime", startTime);
                            sendIntent.putExtra("eventEndTime", endTime);
                            sendIntent.putExtra("eventTimeZone", timeZone);


                            if(attendees.size()>0) {
                                sendIntent.putStringArrayListExtra("attendee", attendees);
                                Log.d(TAG, "attendee"+attendees.size());
                            }else{
                                Log.d(TAG, "attendee is zero"+attendees.size());
                            }

                            sendIntent.putExtra("eventRecurrenceFrequency", eventRecurrenceFrequency);

                            sendIntent.putExtra("eventRecurrenceCount", Integer.valueOf(eventRecurrenceCount.getText().toString()));

                            setResult(RESULT_OK, sendIntent);
                            Log.d(TAG, "Sending Result to SessonEngineer.");

                            finish();
                        }catch (Exception e){
                            Log.d(TAG, "setOnClickListener :"+ e.getMessage());
                        }
                    }

                errorText.setText(message.toString());

            }
        });


    }



/*
  Check if the input time formate is correct
 */

    public boolean isTimeStampValid(String inputString) {
        try {
            dateTimeFormat.parse(inputString);
        } catch (Exception e) {
            errorText.setText(e.getMessage());
            return false;
        }
        return true;
    }

/*
   formating the date
 */

    public String formatDate(String inputString) {
        String out = "";
        try {
            Date date = dateFormat.parse(inputString);

            out = dateFormat.format(date);
            Log.d(TAG,"Date: "+ out);

        }catch (java.text.ParseException e) {
            e.printStackTrace();

        }
        return out;
    }

/*
    Formating the time
 */
    public String formatTime(String inputString) {
        String out = "";
        try {
            Time time = (Time) timeFormat.parse(inputString);

            out = timeFormat.format(time);
            Log.d(TAG,"Time: "+out);

        }catch (java.text.ParseException e) {
            e.printStackTrace();

        }
        return out;
    }


    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    public void dateTimePicker(final View v) {
        // Example of how to use the date time picker
        final Date newDate = new Date();

        class dateTimePicker implements DateTimePicker.DateTimeListener {
            @Override
            public void onDateTimePickerConfirm(Date d) {

               // String date =d.getYear()+"-"+(d.getMonth()+1)+"-"+d.getDay();
                String date =String.format("%02d-%02d-%02d",d.getYear(),(d.getMonth()+1),d.getDate());
                //String time =d.getHours()+":"+d.getMinutes()+":"+d.getSeconds()+"0";
                //String time =String.format("%02d:%02d:02%",d.getHours(),d.getMinutes(),d.getSeconds());
                String time =String.format("%02d:%02d:%02d",d.getHours(),d.getMinutes(),d.getSeconds());
                String curTimeZone =timeZone().toString();
                if(v.getId()==eventStartTime.getId()){
                    eventStartTime.setText(date+ "T" +time+ curTimeZone);
                    startDate = date;
                    startTime = time;
                }else if(v.getId()==eventEndTime.getId()){

                    eventEndTime.setText(date+ "T" +time+ curTimeZone);
                    endDate = date;
                    endTime = time;

                }

                timeZone = curTimeZone;

                Toast.makeText(AddStudyTime.this, "TODO: Open event " + d, Toast.LENGTH_SHORT).show();

            }
        }
        DateTimePicker datetimepicker = new DateTimePicker();
        datetimepicker.setDateTimeListener(new dateTimePicker());
        datetimepicker.show(getSupportFragmentManager(), "date time picker");

    }


    public static String timeZone()
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        String   timeZone = new SimpleDateFormat("Z").format(calendar.getTime());
        return timeZone.substring(0, 3) + ":"+ timeZone.substring(3, 5);
    }


}
