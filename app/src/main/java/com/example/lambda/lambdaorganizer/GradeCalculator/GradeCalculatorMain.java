package com.example.lambda.lambdaorganizer.GradeCalculator;

/**
 * Created by Nicholas on 2017-10-15.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.lambda.lambdaorganizer.R;

public class GradeCalculatorMain extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade_calc_main);
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
            case R.id.change_term:
                setContentView(R.layout.grade_calc_switch_terms);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
