package com.example.lambda.lambdaorganizer.GradeCalculator;

/**
 * Created by Nicholas on 2017-10-15.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.lambda.lambdaorganizer.R;

public class GradeCalculatorMain extends AppCompatActivity {
    private Spinner spinnerterm;
    String termSelected;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade_calc_main);

        spinnerterm = (Spinner)findViewById(R.id.spinner_TermSelect);
        String[] items = new String[]{"grade_calc_edit_course_work","b","c"};

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, items);

        spinnerterm.setAdapter(dataAdapter);

        spinnerterm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> dataAdapter, View v, int position, long id) {
                termSelected = dataAdapter.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.grade_calc_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.edit_course_work:
                setContentView(R.layout.grade_calc_edit_course_work);
                return true;
            case R.id.edit_courses:
                setContentView(R.layout.grade_calc_edit_courses);
                return true;
            case R.id.edit_terms:
                setContentView(R.layout.grade_calc_edit_terms);
                return true;
            case R.id.Grades:
                setContentView(R.layout.grade_calc_main);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
