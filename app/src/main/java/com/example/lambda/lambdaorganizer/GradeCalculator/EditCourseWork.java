package com.example.lambda.lambdaorganizer.GradeCalculator;

/**
 * Created by Nicholas on 2017-10-15.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class EditCourseWork extends AppCompatActivity{

    private Spinner selectTerm;
    private Spinner selectCourse;
    private Spinner selectCourseWork;
    private Button submitBtn;
    private EditText name;
    private EditText weight;
    private EditText grade;
    private String termSelected;
    private String courseSelected;
    private String courseWorkSelected;
    private String nameStr;
    private int weightInt;
    private int gradeInt;


    private Storage storage;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade_calc_edit_course_work);

        storage = Storage.getInstance();

        addSelectTermItems();
    }

    public void addSelectTermItems(){
        selectTerm = (Spinner)findViewById(R.id.spinner_TermSelect);
        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("<Please Select Term>");

        for(int i = 0; i < storage.numOfTerms()-1; i++){
            spinnerItems.add(storage.terms.get(i).getName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerItems);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectTerm.setAdapter(dataAdapter);

        selectTerm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> dataAdapter, View v, int position, long id) {
                termSelected = dataAdapter.getItemAtPosition(position).toString();
                addSelectCourseItems();

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
            for (int i = 0; i < storage.findTerm(termSelected).courses.size() - 1; i++) {
                spinnerItems.add(storage.findTerm(termSelected).courses.get(i).toString());
            }
        }
        catch(NullPointerException e){
            toast("Please add a course.");
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerItems);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCourse.setAdapter(dataAdapter);

        selectCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> dataAdapter, View v, int position, long id) {
                courseSelected = dataAdapter.getItemAtPosition(position).toString();
                addSelectCourseWorkItems();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });}

    public void addSelectCourseWorkItems(){
        selectCourseWork = (Spinner)findViewById(R.id.spinner_WorkSelect);
        List<String> spinnerItems = new ArrayList<>();

        spinnerItems.add("<Please Select Course Work>");

        try {
            for (int i = 0; i <
                    storage.findTerm(termSelected).findCourse(courseSelected).allWork.size() - 1;
                    i++) {
                spinnerItems.add(storage.findTerm(termSelected).findCourse(courseSelected).allWork.get(i).toString());
            }
        }
        catch(NullPointerException e){
            toast("Please add course work.");
        }

        spinnerItems.add("<New Course Work>");

        selectCourseWork.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> dataAdapter, View v, int position, long id) {
                courseWorkSelected = dataAdapter.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


    }

    public void addListenerOnBtn(){

        submitBtn = (Button) findViewById(R.id.Submit_course_work);

        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Term termChosen = storage.findTerm(termSelected);
                Course courseChosen = termChosen.findCourse(courseSelected);

                if (termSelected.compareTo("<Please Select Term>") == 0) {
                    toast("Select a Term");
                }
                if (courseSelected.compareTo("<Please Select Course>") == 0) {
                    toast("Select a Course");
                }
                if (courseWorkSelected.compareTo("<Please Select Course Work>") == 0) {
                    toast("Select Course Work");
                }

                if (    name.getText().toString().compareTo("") == 0 &&
                        weight.getText().toString().compareTo("") == 0 ) {
                    toast("Please input data to edit");
                    }

                else {
                    if (courseWorkSelected.compareTo("<New Course>") == 0) {
                        grade.setVisibility(View.GONE);
                        findViewById(R.id.textView_grade).setVisibility(View.GONE);

                        try {
                            nameStr = name.getText().toString();
                            weightInt = Integer.parseInt(weight.getText().toString());

                            courseChosen.newCourseWork(nameStr, weightInt);

                        } catch (NullPointerException e) {
                            toast("Must enter a name and weight for new work.");
                        }
                    } else if (courseSelected.compareTo("<New Course>") != 0) {
                        courseChosen = termChosen.findCourse(courseSelected);

                        if (name.getText().toString().compareTo("") != 0) {
                            courseChosen.setCourseType(name.getText().toString());
                        }
                        if (weight.getText().toString().compareTo("") != 0) {
                            courseChosen.setCourseNumber(Integer.parseInt(weight.getText().toString()));
                        }
                        if (grade.getText().toString().compareTo("") != 0) {
                            courseChosen.setProfessorName(grade.getText().toString());
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
                Toast.makeText(EditCourseWork.this, toastStr, Toast.LENGTH_LONG).show();
            }
        });
}
}
