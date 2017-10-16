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


    public boolean onOptionItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.edit_course_work:
                //go to edit course work view
                return true;
            case R.id.edit_courses:
                //go to edit course view
                return true;
            case R.id.edit_terms:
                //go to edit terms view
                return true;
            case R.id.change_term:
                //interface to select terms
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
