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
import GradeCalculatorObjects.CourseWork;
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

        name = (EditText)findViewById(R.id.editText_name);
        weight = (EditText)findViewById(R.id.editText_weight);
        grade = (EditText)findViewById(R.id.editText_grade);

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
                if(dataAdapter.getItemAtPosition(position).toString().compareTo("<Please Select Term>") != 0) {
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
            for (int i = 0; i < storage.findTerm(termSelected).courses.size() ; i++) {
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
                if(dataAdapter.getItemAtPosition(position).toString().compareTo("<Please Select Course>") != 0) {
                    courseSelected = dataAdapter.getItemAtPosition(position).toString();
                    addSelectCourseWorkItems();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });}

    public void addSelectCourseWorkItems(){
        selectCourseWork = (Spinner)findViewById(R.id.spinner_WorkSelect);
        List<String> spinnerItems = new ArrayList<>();

        spinnerItems.add("<Please Select Course Work>");

        if(!storage.findTerm(termSelected).findCourse(courseSelected).allWork.isEmpty())
            for (int i = 0; i <
                    storage.findTerm(termSelected).findCourse(courseSelected).allWork.size() ;
                    i++) {
                spinnerItems.add(storage.findTerm(termSelected).findCourse(courseSelected).allWork.get(i).getName());
            }



        spinnerItems.add("<New Course Work>");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerItems);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCourseWork.setAdapter(dataAdapter);

        selectCourseWork.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> dataAdapter, View v, int position, long id) {
                if(dataAdapter.getItemAtPosition(position).toString().compareTo("<Please Select Course Work>") != 0) {
                    courseWorkSelected = dataAdapter.getItemAtPosition(position).toString();
                }
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
                CourseWork courseWorkChosen = courseChosen.findCourseWork(courseWorkSelected);

                if (termSelected.compareTo("<Please Select Term>") == 0 || termSelected == null) {
                    toast("Select a Term");
                }
                if (courseSelected.compareTo("<Please Select Course>") == 0 || courseSelected == null) {
                    toast("Select a Course");
                }
                if (courseWorkSelected.compareTo("<Please Select Course Work>") == 0 || courseWorkSelected == null) {
                    toast("Select Course Work");
                }

                if (    name.getText().toString().compareTo("") == 0 &&
                        weight.getText().toString().compareTo("") == 0 &&
                        grade.getText().toString().compareTo("") == 0)
                {
                    toast("Please input data to edit");
                    }

                else {
                    if (courseWorkSelected.compareTo("<New Course Work>") == 0) {
                        if(grade.getText().toString().compareTo("") == 0){
                            try {
                                nameStr = name.getText().toString();
                                weightInt = Integer.parseInt(weight.getText().toString());

                                courseChosen.newCourseWork(nameStr, weightInt);
                                toast("Course Work " + nameStr + " was added!");

                            } catch (NullPointerException e) {
                                toast("Must enter a name and weight for new work.");
                            }
                        }
                        else{
                            try {
                                nameStr = name.getText().toString();
                                weightInt = Integer.parseInt(weight.getText().toString());
                                gradeInt = Integer.parseInt(grade.getText().toString());

                                courseChosen.newCourseWorkWithgrade(nameStr, weightInt, gradeInt);
                                toast("Course Work " + nameStr + " was added!");

                            } catch (NullPointerException e) {
                                toast("Must enter a name and weight for new work.");
                            }
                        }


                    } else if (courseWorkSelected.compareTo("<New Course Work>") != 0) {
                        courseWorkChosen = courseChosen.findCourseWork(courseWorkSelected);

                        if (name.getText().toString().compareTo("") != 0) {
                            courseWorkChosen.setName(name.getText().toString());
                        }
                        if (weight.getText().toString().compareTo("") != 0) {
                            courseWorkChosen.setWeight(Integer.parseInt(weight.getText().toString()));
                        }
                        if (grade.getText().toString().compareTo("") != 0) {
                            courseWorkChosen.setFinalGrade(Integer.parseInt(grade.getText().toString()));
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
