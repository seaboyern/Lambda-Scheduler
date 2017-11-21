package com.example.lambda.lambdaorganizer.GradeCalculator;

/**
 * Created by Nicholas on 2017-10-15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
                Intent editCourseWorkIntent = new Intent(getApplicationContext(), EditCourseWork.class);
                editCourseWorkIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(editCourseWorkIntent, 0);
                //setContentView(R.layout.grade_calc_edit_course_work);
                return true;
            case R.id.edit_courses:
                Intent editCoursesIntent = new Intent(getApplicationContext(), EditCourses.class);
                editCoursesIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(editCoursesIntent, 0);
                //setContentView(R.layout.grade_calc_edit_courses);
                return true;
            case R.id.edit_terms:
                Intent editTermsIntent = new Intent(getApplicationContext(), EditTerms.class);
                editTermsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(editTermsIntent, 0);
                //setContentView(R.layout.grade_calc_edit_terms);
                return true;
            case R.id.Grades:
                Intent gradesIntent = new Intent(getApplicationContext(), GradeCalculatorMain.class);
                gradesIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(gradesIntent, 0);
                //setContentView(R.layout.grade_calc_main);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
