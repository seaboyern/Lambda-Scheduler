package com.example.lambda.lambdaorganizer.GradeCalculator;

/**
 * Created by Nicholas on 2017-10-15.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lambda.lambdaorganizer.R;

import java.util.ArrayList;
import java.util.List;

import GradeCalculatorObjects.Course;
import GradeCalculatorObjects.Storage;
import GradeCalculatorObjects.Term;

import static com.example.lambda.lambdaorganizer.R.id.editText_goal;
import static com.example.lambda.lambdaorganizer.R.id.editText_number;
import static com.example.lambda.lambdaorganizer.R.id.editText_prof;
import static com.example.lambda.lambdaorganizer.R.id.editText_type;

public class EditCourses extends AppCompatActivity{
    private Spinner selectTerm;
    private Spinner selectCourse;
    private Button submitBtn;

    private Storage storage;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade_calc_edit_courses);
        storage = Storage.getInstance();

        addSelectCourseItems();
        addSelectTermItems();
        addListenerOnBtn();
    }

    public void addSelectTermItems(){
        selectTerm = (Spinner)findViewById(R.id.spinner_TermSelect);
        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("<Please Select Term>");

        for(int i = 0; i < storage.numOfTerms() - 1; i++){
            spinnerItems.add(storage.terms.get(i).getName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerItems);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectTerm.setAdapter(dataAdapter);
    }

    public void addSelectCourseItems(){
        selectCourse = (Spinner)findViewById(R.id.spinner_CourseSelect);
        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("<Please Select Course>");

        for(int i = 0; i < storage.terms.element().courses.size() - 1; i++){
            spinnerItems.add(storage.terms.element().courses.get(i).toString());
        }

        spinnerItems.add("<New Course>");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerItems);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCourse.setAdapter(dataAdapter);
    }

    public void addListenerOnBtn(){

        submitBtn = (Button) findViewById(R.id.Submit_courses);

        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String termChosenStr = String.valueOf(selectTerm);
                String courseChosenStr = String.valueOf(selectCourse);

                Term termChosen = storage.findTerm(termChosenStr);
                Course courseChosen = termChosen.findCourse(courseChosenStr);

                if (termChosenStr.compareTo("<Please Select Term>") == 0) {
                    toast("Select a Term");
                }
                if (courseChosenStr.compareTo("<Please Select Course>") == 0) {
                    toast("Select a Course");
                }
                if (    findViewById(editText_type).toString().compareTo("") == 0 &&
                        findViewById(editText_number).toString().compareTo("") == 0 &&
                        findViewById(editText_prof).toString().compareTo("") == 0 &&
                        findViewById(editText_goal).toString().compareTo("") == 0) {
                    toast("Please input data to edit");
                }
                else{
                    if(findViewById(editText_type).toString().compareTo("") != 0){
                        courseChosen.setCourseType(String.valueOf(editText_type));
                    }
                    if(findViewById(editText_number).toString().compareTo("") != 0){
                        courseChosen.setCourseNumber(Integer.valueOf(editText_number));
                    }
                    if(findViewById(editText_prof).toString().compareTo("") != 0){
                        courseChosen.setProfessorName(String.valueOf(editText_prof));
                    }
                    if(findViewById(editText_goal).toString().compareTo("") != 0){
                        courseChosen.setGoalFinalGrade(Float.valueOf(editText_goal));
                    }
                    toast(courseChosenStr + " was updated.");
                }
            }


        });}

    public void toast(final String toastStr) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(EditCourses.this, toastStr, Toast.LENGTH_LONG).show();
            }
        });


    }
}

