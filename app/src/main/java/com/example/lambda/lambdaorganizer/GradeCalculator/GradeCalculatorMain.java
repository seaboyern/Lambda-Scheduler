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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lambda.lambdaorganizer.R;

import java.util.ArrayList;
import java.util.List;

import GradeCalculatorObjects.Course;
import GradeCalculatorObjects.CourseWork;
import GradeCalculatorObjects.Storage;
import GradeCalculatorObjects.Term;

public class GradeCalculatorMain extends AppCompatActivity {
    private Spinner selectTerm;
    private Spinner selectCourse;
    private String termSelected;
    private String courseSelected;
    private Storage storage;
    private ArrayList<String> workWithGrades;
    private ArrayAdapter<String> workWithGradesAdapter;
    private ListView gradesListView;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade_calc_main);

        storage = Storage.getInstance();
        addSelectTermItems();

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

    public void addSelectTermItems(){
        selectTerm = (Spinner)findViewById(R.id.spinner_TermSelect);
        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("<Please Select Term>");

        for(int i = 0; i < storage.numOfTerms(); i++){
            spinnerItems.add(storage.terms.get(i).getName());
        }

        ArrayAdapter<String> selectTermAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerItems);
        selectTermAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectTerm.setAdapter(selectTermAdapter);

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

        ArrayAdapter<String> selectCourseAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerItems);
        selectCourseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCourse.setAdapter(selectCourseAdapter);

        selectCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> dataAdapter, View v, int position, long id) {
                if(dataAdapter.getItemAtPosition(position).toString().compareTo("<Please Select Course>") != 0) {
                    courseSelected = dataAdapter.getItemAtPosition(position).toString();
                    addListViewItems();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });}

    public void addListViewItems(){

        Term termSelectedObj = storage.findTerm(termSelected);
        Course courseSelectedObj = termSelectedObj.findCourse(courseSelected);

        gradesListView = (ListView)findViewById(R.id.ListView_Grades);
        workWithGrades = new ArrayList<>();
        workWithGrades.add(getCourseListViewItem(courseSelectedObj));

        for(int i = 0; i < courseSelectedObj.allWork.size(); i++){
            workWithGrades.add(getWorkListViewItem(courseSelectedObj.allWork.get(i), courseSelectedObj));
        }
        workWithGradesAdapter = new ArrayAdapter<>(this, R.layout.simple_row,
                                R.id.rowTextView, workWithGrades);
        gradesListView.setAdapter(workWithGradesAdapter);


    }

    public String getWorkListViewItem(CourseWork work, Course parentCourse){

        return work.getName() + "        Grade: " + work.getFinalGrade() + "        Grade Needed: " + parentCourse.calcGradeNeeded(work);

    }

    public String getCourseListViewItem(Course course){
        return "Current Final Grade: " + course.getCurrentFinalGrade();
    }


    public void toast(final String toastStr) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(GradeCalculatorMain.this, toastStr, Toast.LENGTH_LONG).show();
            }
        });
    }

}
