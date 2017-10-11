package com.example.lambda.lambdaorganizer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;

//public class MainActivity extends Activity implements EasyPermissions.PermissionCallbacks {
public class MainActivity extends AppCompatActivity  {
    private Button mCallApiButton;
    GoogleCalAPI newCalender;
    private static final String BUTTON_TEXT = "Call Google Calendar API";


    /**
     * Create the main activity.
     * @param savedInstanceState previously saved instance data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout activityLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        activityLayout.setLayoutParams(lp);
        activityLayout.setOrientation(LinearLayout.VERTICAL);
        activityLayout.setPadding(16, 16, 16, 16);

        mCallApiButton = new Button(this);
        mCallApiButton.setText(BUTTON_TEXT);

        newCalender = new GoogleCalAPI(this);

        mCallApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallApiButton.setEnabled(false);

                newCalender.getmOutputText().setText("");
                newCalender.getResultsFromApi();
                mCallApiButton.setEnabled(true);
            }
        });
        activityLayout.addView(mCallApiButton);

        activityLayout.addView(newCalender.getmOutputText());

        setContentView(activityLayout);

    }

}