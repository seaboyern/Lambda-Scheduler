package com.example.lambda.lambdaorganizer.SessionEngineer;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lambda.lambdaorganizer.MainActivity;
import com.example.lambda.lambdaorganizer.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import adapters.SessionToStudyEventAdapter;
import adapters.StudyEventToSessionAdapter;
import database.tables.CommitmentTable;
import database.tables.SessionTable;
import entities.Commitment;
import entities.Session;
import entities.StudyEvent;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class SessionEngineer extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{
    GoogleAccountCredential mCredential;
    private ArrayList<String> mOutputText;
    private TextView googleResult;
    private FloatingActionButton mCallApiButton;
    private ArrayAdapter<String> adapter;
    private Button backButton;
    private Button addStudyButton;


    private ListView listView;

    Button showListButton;

    ProgressDialog mProgress;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    static final int REQUEST_CODE_ADD_STUDY_TIME =42;

    private static final String BUTTON_TEXT = "Sync Google Calendar";
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = { CalendarScopes.CALENDAR};

    private DateTime startTime;
    private DateTime endTime;


    public static String TAG = "SessionEngineer";

    private String eventDescription;
    private String eventStartTime;
    private String eventEndTime;
    boolean createEvent =false;
    boolean getResult = false;
    StudyEvent newStudyEvent;

    /**
     * Inserts a StudyEvent into the local database:
     */
    public void addStudyEventToDatabase(StudyEvent e) {
        StudyEventToSessionAdapter a = new StudyEventToSessionAdapter(e);
        SessionTable.getInstance(getApplicationContext()).insert(a);
    }

    public LinkedList<Commitment> getEventsByDate(Date date) {
        LinkedList<Commitment> list = CommitmentTable.getInstance(this.getApplicationContext())
                .selectByDate(date);
        return list;
    }

    /**
     * Create the main activity.
     * @param savedInstanceState previously saved instance data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesson_engineer);

        mCallApiButton = (FloatingActionButton) findViewById(R.id.googleActionButton);

        backButton = (Button) findViewById(R.id.Back_Menu);

        addStudyButton = (Button) findViewById(R.id.AddStudyTiime);


        listView = (ListView) findViewById(R.id.contentList1);
        showListButton = (Button) findViewById(R.id.showList);


        eventDescription =new String();
        eventStartTime=new String();
        eventEndTime=new String();

        //newStudyEvent = new StudyEvent();


        //startTime = new DateTime(System.currentTimeMillis());
        //endTime = new DateTime(System.currentTimeMillis());


        mOutputText = new ArrayList<String>();

        //mOutputText.add("Press Google Button To see the content.");


        adapter = new ArrayAdapter<String>
                (this, R.layout.google_cal_result,R.id.listItem, mOutputText);


        googleResult = (TextView) findViewById(R.id.textviewAPIResult);

        googleResult.setPadding(16, 16, 16, 16);
        googleResult.setVerticalScrollBarEnabled(true);
        googleResult.setMovementMethod(new ScrollingMovementMethod());
        googleResult.setText(
                "Click the \'" + BUTTON_TEXT +"\' button to Sync With Google Calender.");


        // mCallApiButton.setText(BUTTON_TEXT);
        mCallApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallApiButton.setEnabled(false);
                googleResult.setText("");
                //mOutputText.clear();
                sendRequestToGoogleApi();
                //googleResult.setText( mOutputText.toString());
                mCallApiButton.setEnabled(true);
                adapter.notifyDataSetChanged();
            }
        });



        addStudyButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.d(TAG, "Sending Requent to AddStudy.");
                addStudyButton.setEnabled(false);
                mCallApiButton.setEnabled(false);

                Intent studyIntent=new Intent(getApplicationContext(), AddStudyTime.class);
                studyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                try{
                    startActivityForResult(studyIntent,REQUEST_CODE_ADD_STUDY_TIME);
                }catch (Exception e){
                    googleResult.setText("addStudyButton:"+e.getMessage());
                }
                createEvent = true;
                addStudyButton.setEnabled(true);
                mCallApiButton.setEnabled(true);

            }

        });



        showListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //mOutputText.add("RAM");

                LinkedList<Commitment> list = getEventsByDate(new Date());
                Log.d(TAG, "Commitments today: " + list.toString());

                adapter.notifyDataSetChanged();
            }
        });




        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });





        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Calling Google Calendar API ...");




        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());




        listView.setAdapter(adapter);



    }






    /**
     * Attempt to call the API, after verifying that all the preconditions are
     * satisfied. The preconditions are: Google Play Services installed, an
     * account was selected and the device currently has online access. If any
     * of the preconditions are not satisfied, the app will prompt the user as
     * appropriate.
     */
    private void sendRequestToGoogleApi() {
        //mOutputText.add("Executed: getResultsFromApi ");
        if (! isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if ( !isDeviceOnline()) {
            //mOutputText.setText("No network connection available.");
            //mOutputText.clear();
            googleResult.setText("No network connection available.");

        } else {
                new syncWithGoogleAPI(mCredential).execute();

        }
    }


    /**
     * Attempt to call the API, after verifying that all the preconditions are
     * satisfied. The preconditions are: Google Play Services installed, an
     * account was selected and the device currently has online access. If any
     * of the preconditions are not satisfied, the app will prompt the user as
     * appropriate.
     */
    private void sendRequestToGoogleApi(StudyEvent event) {
        //mOutputText.add("Executed: getResultsFromApi ");
        if (! isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if ( !isDeviceOnline()) {
            //mOutputText.setText("No network connection available.");
            //mOutputText.clear();
            googleResult.setText("No network connection available.");

        } else {
            new syncWithGoogleAPI(mCredential, event).execute();

        }
    }



    /**
     * Attempts to set the account used with the API credentials. If an account
     * name was previously saved it will use that one; otherwise an account
     * picker dialog will be shown to the user. Note that the setting the
     * account to use with the credentials object requires the app to have the
     * GET_ACCOUNTS permission, which is requested here if it is not already
     * present. The AfterPermissionGranted annotation indicates that this
     * function will be rerun automatically whenever the GET_ACCOUNTS permission
     * is granted.
     */
    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        //mOutputText.add("Executed: chooseAccount ");
        if (EasyPermissions.hasPermissions(
                this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                sendRequestToGoogleApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode code indicating the result of the incoming
     *     activity result.
     * @param data Intent (containing result data) returned by incoming
     *     activity result.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "Activity Change.......requestCode:"+requestCode+"resultCode:"+resultCode+":"+RESULT_OK);
        if(requestCode == REQUEST_CODE_ADD_STUDY_TIME && resultCode == RESULT_OK){
            Log.d(TAG, "Activity Result Found from AddStudyTime.......................................");
            Bundle newBundle = data.getExtras();
            if (!newBundle.isEmpty()) {


                if (newBundle.containsKey("eventDescription")&& newBundle.containsKey("eventStartTime")&&newBundle.containsKey("eventEndTime")) {


                    newStudyEvent = new StudyEvent();

                    try {
                        newStudyEvent.setEventDescription(newBundle.getString("eventDescription"));
                        newStudyEvent.setEventLocation(newBundle.getString("eventLocation"));
                        newStudyEvent.setEventSummary(newBundle.getString("eventSummary"));
                        newStudyEvent.setEventLocation(newBundle.getString("eventLocation"));


                        newStudyEvent.setEventStartDate(newBundle.getString("eventStartDate").toString());
                        newStudyEvent.setEventEndDate(newBundle.getString("eventStartDate").toString());
                        newStudyEvent.setEventStarTime(newBundle.getString("eventStartTime").toString());
                        newStudyEvent.setEventEndTime(newBundle.getString("eventEndTime").toString());
                        newStudyEvent.setEventTimeZone(newBundle.getString("eventTimeZone").toString());

                        newStudyEvent.setEventRecurrenceFrequency(newBundle.getString("eventRecurrenceFrequency"));
                        newStudyEvent.setEventRecurrenceCount(newBundle.getInt("eventRecurrenceCount"));
                        newStudyEvent.setAttendees((newBundle.getStringArrayList("attendees")));
                        this.eventDescription = newBundle.getString("eventDescription");
                        this.eventStartTime = newBundle.getString("eventStartTime");
                        this.eventEndTime = newBundle.getString("eventEndTime");
                    }catch (Exception e){
                        googleResult.setText(e.getMessage());
                        Log.d(TAG, "Event Create Failed: "+e.getMessage());
                    }

                    Log.d(TAG, "onActivityResult: All Ref found :"+newStudyEvent.toString());
                    //sendRequestToGoogleApi(newStudyEvent);
                    addStudyEventToDatabase(newStudyEvent);
                } else {
                    Log.d(TAG, "onActivityResult: No Ref. Found in newBundle.");

                }


            }
        }


        //mOutputText.add("Executed: onActivityResult ");
        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    //mOutputText.setText("This app requires Google Play Services. Please install " + "Google Play Services on your device and relaunch this app.");
                    //mOutputText.clear();
                    googleResult.setText("This app requires Google Play Services. Please install " + "Google Play Services on your device and relaunch this app.");

                } else {
                    sendRequestToGoogleApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        sendRequestToGoogleApi();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    sendRequestToGoogleApi();
                }
                break;

        }
    }

    /**
     * Respond to requests for permissions at runtime for API 23 and above.
     * @param requestCode The request code passed in
     *     requestPermissions(android.app.Activity, String, int, String[])
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //mOutputText.add("Executed: onRequestPermissionsResult ");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }

    /**
     * Callback for when a permission is granted using the EasyPermissions
     * library.
     * @param requestCode The request code associated with the requested
     *         permission
     * @param list The requested permission list. Never null.
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
       // mOutputText.add("Executed: onPermissionsGranted ");
        // Do nothing.
    }

    /**
     * Callback for when a permission is denied using the EasyPermissions
     * library.
     * @param requestCode The request code associated with the requested
     *         permission
     * @param list The requested permission list. Never null.
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        //mOutputText.add("Executed: onPermissionsDenied ");
        // Do nothing.
    }

    /**
     * Checks whether the device currently has a network connection.
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
       // mOutputText.add("Executed: isDeviceOnline ");
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     * @return true if Google Play Services is available and up to
     *     date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
       // mOutputText.add("Executed: isGooglePlayServicesAvailable ");
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    /**
     * Attempt to resolve a missing, out-of-date, invalid or disabled Google
     * Play Services installation via a user dialog, if possible.
     */
    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }


    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     * @param connectionStatusCode code describing the presence (or lack of)
     *     Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }



    /**
     * An asynchronous task that handles the Google Calendar API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    private class syncWithGoogleAPI extends AsyncTask<Void, Void, List<String>> {
        private com.google.api.services.calendar.Calendar mService = null;
        private Exception mLastError = null;
        StudyEvent studyEvent;
        Boolean createNewEvent =false;
        Boolean eventCreated = false;

        syncWithGoogleAPI(GoogleAccountCredential credential, StudyEvent newStudyEvent) {

            if(newStudyEvent!=null) {
                this.studyEvent = newStudyEvent;

            }


            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Lambda Organizer")
                    .build();
        }

        syncWithGoogleAPI(GoogleAccountCredential credential) {

            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Lambda Organizer")
                    .build();
        }

        /**
         * Background task to call Google Calendar API.
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {

            if(studyEvent!=null){
                try {
                    createGoogleEvent();
                    studyEvent =null;
                    return null;
                }catch (Exception e) {
                    mLastError = e;
                    cancel(true);
                    studyEvent =null;
                    return null;
                }

            }else if(studyEvent==null){

                try {
                    return getEventList();
                } catch (Exception e) {
                    mLastError = e;
                    cancel(true);
                    return null;
                }

            }else {
                return null;
            }


        }

        /**
         * Fetch a list of the next 10 events from the primary calendar.
         * @return List of Strings describing returned events.
         * @throws IOException
         */
        private List<String> getEventList() throws IOException {
            // List the next 10 events from the primary calendar.

            DateTime now = new DateTime(System.currentTimeMillis());
            List<String> eventStrings = new ArrayList<String>();
            Events events = mService.events().list("primary")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();

            for (Event event : items) {
                DateTime startTime = event.getStart().getDateTime();
                DateTime endTime = event.getEnd().getDateTime();

                if (startTime == null) {
                    // All-day events don't have start times, so just use
                    // the start date.
                    startTime = event.getStart().getDate();
                    DateTime startDT = DateTime.parseRfc3339(startTime.toString());
                    java.sql.Date date = new java.sql.Date(startDT.getValue());

                    eventStrings.add(
                            String.format("%s (%s) (%s) %s", event.getSummary(), date, "All Day Event" , event.getId()));

                }else{
                    DateTime startDT = DateTime.parseRfc3339(startTime.toString());
                    DateTime endDT = DateTime.parseRfc3339(endTime.toString());
                    java.sql.Timestamp starTimeStamp = new java.sql.Timestamp(startDT.getValue());
                    java.sql.Timestamp endTimeStamp = new java.sql.Timestamp(endDT.getValue());
                    eventStrings.add(
                            String.format("%s (%s) (%s) %s", event.getSummary(), starTimeStamp,endTimeStamp , event.getId()));
                }

            }
            return eventStrings;
        }



/**

         Refer to the Java quickstart on how to setup the environment:
         https://developers.google.com/google-apps/calendar/quickstart/java
         Change the scope to CalendarScopes.CALENDAR and delete any stored
         credentials.
 **/

    private void createGoogleEvent() {


        String timezoneID = TimeZone.getDefault().getID();


        Event event = new Event()
                .setDescription(studyEvent.getEventDescription())
                .setLocation(studyEvent.getEventLocation())
                .setSummary(studyEvent.getEventSummary());


        //DateTime startDateTime = new DateTime(eventStartTime);
        //DateTime startDateTime = new DateTime("2017-12-06T07:50:00-06:00");
        DateTime startDateTime = new DateTime(studyEvent.getEventStartDate().toString()+"T"+studyEvent.getEventStarTime()+studyEvent.getEventTimeZone().toString());
        Log.d(TAG, "createGoogleEvent: startDateTime: "+startDateTime.toString());
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone(timezoneID);
        event.setStart(start);

        //DateTime endDateTime = new DateTime(eventEndTime);
        //DateTime endDateTime = new DateTime("2017-11-13T20:55:00-06:00");
        DateTime endDateTime = new DateTime(studyEvent.getEventEndDate().toString()+"T"+studyEvent.getEventEndTime()+studyEvent.getEventTimeZone().toString());
        Log.d(TAG, "createGoogleEvent: endDateTime"+endDateTime.toString());
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone(timezoneID);
        event.setEnd(end);

        //String[] recurrence = new String[]{"RRULE:FREQ=DAILY;COUNT=2"};
        if(studyEvent.getEventRecurrenceFrequency() !=null && studyEvent.getEventRecurrenceCount()>0) {
            String[] recurrence = new String[]{"RRULE:FREQ="+studyEvent.getEventRecurrenceFrequency()+";COUNT="+studyEvent.getEventRecurrenceCount()};
            event.setRecurrence(Arrays.asList(recurrence));
        }else{
            String[] recurrence = new String[]{"RRULE:FREQ=DAILY;COUNT=2"};
            event.setRecurrence(Arrays.asList(recurrence));

        }
        int count=0;
        try{
            count = studyEvent.getAttendees().size();
        }catch (Exception e){
            Log.d(TAG, "studyEvent.getAttendees is Zero. "+e.getMessage());
        }

        if(count>0) {

            EventAttendee[] attendees = new EventAttendee[]{};



            for (int i = 0; i < studyEvent.getAttendees().size(); i++) {
                attendees[i].setEmail(studyEvent.getAttendees().get(i).toString());
            }
            event.setAttendees(Arrays.asList(attendees));
        }

        EventReminder[] reminderOverrides = new EventReminder[]{
                new EventReminder().setMethod("email").setMinutes(24 * 60),//send reminder email 24 hr before the event
                new EventReminder().setMethod("popup").setMinutes(10),//send reminder popup 10 min before the event
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String calendarId = "primary";
        try {
            event = mService.events().insert(calendarId, event).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        studyEvent.setGoogleEventID(event.getId());
        Log.d(TAG, "Event created: "+studyEvent.getGoogleEventID()+"\n"+ event.getHtmlLink());

        eventCreated = true;


    }




        @Override
        protected void onPreExecute() {
            Log.d(TAG, "Executed: onPreExecute.....................");
            googleResult.setText("");
            mProgress.show();
        }

        @Override
        protected void onPostExecute(List<String> output) {
            Log.d(TAG, "Executed: onPostExecute.....................");
           // mOutputText.add("Executed: onPostExecute ");
            mProgress.hide();
            if (output == null || output.size() == 0) {
                //mOutputText.setText("No results returned.");
                mOutputText.clear();
                googleResult.setText("No results returned.");

            } else if(eventCreated){
                mOutputText.clear();
                googleResult.setText("New Event Created. Please Click on google button to see the change.");
                super.cancel(true);

            } else {
                googleResult.setText("Data retrieved using the Google Calendar API.");
                //output.add(0, "Data retrieved using the Google Calendar API:");
                //mOutputText.setText(TextUtils.join("\n", output));
                mOutputText.clear();
                // mOutputText.add(TextUtils.join("\n", output));
                for(int i=0; i<output.size(); i++) {
                    mOutputText.add(output.get(i));
                }

            }
        }

        @Override
        protected void onCancelled() {
            mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            REQUEST_AUTHORIZATION);
                } else {
                    //mOutputText.setText("The following error occurred:\n" + mLastError.getMessage());
                    mOutputText.clear();
                    googleResult.setText("The following error occurred:\n" + mLastError.getMessage());
                }
            } else {
                // mOutputText.setText("Request cancelled.");
                mOutputText.clear();
                googleResult.setText("Request cancelled.");
            }
        }



    }

}