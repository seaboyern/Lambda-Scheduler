package com.example.lambda.lambdaorganizer.GradeCalculator;

/**
 * Created by Nicholas on 2017-10-15.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lambda.lambdaorganizer.R;

import java.util.ArrayList;
import java.util.List;

import GradeCalculatorObjects.Course;
import GradeCalculatorObjects.Storage;
import GradeCalculatorObjects.Term;

public class EditCourses extends AppCompatActivity{
    private Spinner selectTerm;
    private Spinner selectCourse;
    private Button submitBtn;
    private EditText type;
    private EditText number;
    private EditText prof;
    private EditText goal;
    private String termSelected;
    private String courseSelected;
    private String typeStr;
    private int numberInt;
    private String profStr;
    private int goalInt;

    private Storage storage;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade_calc_edit_courses);

        type = (EditText)findViewById(R.id.editText_type);
        number = (EditText) findViewById(R.id.editText_number);
        prof = (EditText) findViewById(R.id.editText_prof);
        goal = (EditText) findViewById(R.id.editText_goal);

        storage = Storage.getInstance();

        addSelectTermItems();
        addListenerOnBtn();
    }

    public void addSelectTermItems(){
        selectTerm = (Spinner)findViewById(R.id.spinner_TermSelect);
        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("<Please Select Term>");

        for(int i = 0; i < storage.numOfTerms(); i++){
            spinnerItems.add(storage.terms.get(i).getName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerItems);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectTerm.setAdapter(dataAdapter);

        selectTerm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> dataAdapter, View v, int position, long id) {
                if(dataAdapter.getItemAtPosition(position).toString().compareTo("<Please Select Term>") != 0){
                    termSelected = dataAdapter.getItemAtPosition(position).toString();
                    addSelectCourseItems();
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
    });
    }

    public void addSelectCourseItems(){
        selectCourse = (Spinner)findViewById(R.id.spinner_CourseSelect);
        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("<Please Select Course>");

        try {
            for (int i = 0; i < storage.findTerm(termSelected).courses.size(); i++) {
                spinnerItems.add(storage.findTerm(termSelected).courses.get(i).toString());
            }
        }
        catch(NullPointerException e){
            toast("Please add a course.");
        }

        spinnerItems.add("<New Course>");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerItems);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCourse.setAdapter(dataAdapter);

        selectCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> dataAdapter, View v, int position, long id) {
                    if(dataAdapter.getItemAtPosition(position).toString().compareTo("<Please Select Course>") != 0){
                        courseSelected = dataAdapter.getItemAtPosition(position).toString();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {

                }
    });}

    public void addListenerOnBtn(){

        submitBtn = (Button) findViewById(R.id.Submit_courses);

        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Term termChosen = storage.findTerm(termSelected);
                Course courseChosen;
                if (termSelected.compareTo("<Please Select Term>") == 0 || termSelected == null) {
                    toast("Select a Term");
                }
                if (courseSelected.compareTo("<Please Select Course>") == 0 || courseSelected == null) {
                    toast("Select a Course");
                }

                if (    type.getText().toString().compareTo("") == 0 &&
                        number.getText().toString().compareTo("") == 0 &&
                        prof.getText().toString().compareTo("") == 0 &&
                        goal.getText().toString().compareTo("") == 0) {
                    toast("Please input data to edit");
                }
                else{
                    if (courseSelected.compareTo("<New Course>") == 0) {

                        if(type.getText().toString().compareTo("") == 0){
                            toast("Please enter course type");
                        }
                        else{
                            typeStr = type.getText().toString();
                        }
                        if(number.getText().toString().compareTo("") == 0){
                            toast("Please enter course number");
                        }
                        else{
                            numberInt = Integer.parseInt(number.getText().toString());
                        }
                        if(prof.getText().toString().compareTo("") == 0){
                            toast("Please enter professor name");
                        }
                        else{
                            profStr = prof.getText().toString();
                        }
                        if(goal.getText().toString().compareTo("") == 0){
                            toast("Please enter a goal grade");
                        }
                        else{
                            goalInt = Integer.parseInt(goal.getText().toString());
                        }

                        if( typeStr != null && numberInt != 0 &&
                            profStr != null && goalInt != 0    ){
                            courseChosen = termChosen.newCourse(profStr, typeStr, numberInt, goalInt);
                            toast("Course " + courseChosen.toString() + " was added!" );
                        }
                    }
                    if (courseSelected.compareTo("<New Course>") != 0) {
                        courseChosen = termChosen.findCourse(courseSelected);

                        if (type.toString().compareTo("") != 0) {
                            courseChosen.setCourseType(type.getText().toString());
                        }
                        if (number.toString().compareTo("") != 0) {
                            courseChosen.setCourseNumber(Integer.parseInt(number.getText().toString()));
                        }
                        if (prof.toString().compareTo("") != 0) {
                            courseChosen.setProfessorName(prof.getText().toString());
                        }
                        if (goal.toString().compareTo("") != 0) {
                            courseChosen.setGoalFinalGrade(Float.parseFloat(goal.getText().toString()));
                        }
                        toast(courseSelected + " was updated.");
                    }
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

