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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lambda.lambdaorganizer.R;

import java.util.ArrayList;

import GradeCalculatorObjects.Storage;
import GradeCalculatorObjects.Term;

import static com.example.lambda.lambdaorganizer.R.id.Submit_terms;
import static com.example.lambda.lambdaorganizer.R.id.editText;


public class EditTerms extends AppCompatActivity {

    private Spinner spinnerterm;
    private Button submitBtn;
    Storage storage;
    String termSelected;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade_calc_edit_terms);

        storage = Storage.getInstance();

        spinnerterm = (Spinner)findViewById(R.id.editTerms_spin);
        ArrayList<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("<Please Select Term>");
        spinnerItems.add("<New Term>");

        for(int i = 0; i < storage.numOfTerms(); i++){
            spinnerItems.add(storage.terms.get(i).getName());
        }

        //String[] items = spinnerItems.toArray(new String[spinnerItems.size()]);
        String[] items = new String[]{"a","b","c"};

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, items);
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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




        addListenerOnBtn();

    }

    /*public void addSpinnerItems(){


        spinnerterm = (Spinner)findViewById(R.id.editTerms_spin);
        ArrayList<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("<Please Select Term>");
        spinnerItems.add("<New Term>");

        for(int i = 0; i < storage.numOfTerms(); i++){
            spinnerItems.add(storage.terms.get(i).getName());
        }

        //String[] items = spinnerItems.toArray(new String[spinnerItems.size()]);
        String[] items = new String[]{"a","b","c"};

        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerterm.setAdapter(dataAdapter);

        spinnerterm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> dataAdapter, View v, int position, long id) {
                termSelected = dataAdapter.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


    }*/

    public void addListenerOnBtn(){

        submitBtn = (Button) findViewById(Submit_terms);

        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String valueChosen = String.valueOf(spinnerterm);

                if(valueChosen.compareTo("<New Term>") == 0){
                    Term newTerm = new Term(findViewById(editText).toString());
                    storage.insert(newTerm);
                }
                else if(valueChosen.compareTo("<Please Select Term>") == 0){
                    toast("Select a Term");
                }
                else if(findViewById(editText).toString().compareTo("") == 0){
                    toast("Input name");
                }
            }
        });}

    public void toast(final String toastStr) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(EditTerms.this, toastStr, Toast.LENGTH_LONG).show();
            }
        });


    }








}