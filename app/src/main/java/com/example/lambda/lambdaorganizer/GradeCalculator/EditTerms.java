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

import GradeCalculatorObjects.Storage;
import GradeCalculatorObjects.Term;

import static com.example.lambda.lambdaorganizer.R.id.editText;


public class EditTerms extends AppCompatActivity {

    private Spinner spinnerterm;
    private Button submitBtn;
    private EditText nameInput;
    Storage storage;
    String termSelected;
    ArrayAdapter<String> dataAdapter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade_calc_edit_terms);

        storage = Storage.getInstance();
        submitBtn = (Button) findViewById(R.id.Submit_terms);
        nameInput = (EditText) findViewById(R.id.editText);


        addSpinnerItems();
        addListenerOnBtn();

    }

    public void addSpinnerItems(){


        spinnerterm = (Spinner)findViewById(R.id.editTerms_spin);
        ArrayList<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("<Please Select Term>");
        spinnerItems.add("<New Term>");

        for(int i = 0; i < storage.numOfTerms(); i++){
            spinnerItems.add(storage.terms.get(i).getName());
        }

        String[] items = spinnerItems.toArray(new String[spinnerItems.size()]);

        dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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


    }

    public void addListenerOnBtn(){


        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(termSelected.compareTo("<New Term>") == 0){
                    Term newTerm = new Term(nameInput.getText().toString());
                    toast("Term " + newTerm.getName() + " was added.");
                }
                else if(termSelected.compareTo("<Please Select Term>") == 0){
                    toast("Select a Term");
                }
                else if(findViewById(editText).toString().compareTo("") == 0){
                    toast("Input name");
                }
                else if(termSelected.compareTo("<New Term>") != 0){
                    storage.findTerm(termSelected).setName(nameInput.getText().toString());
                    toast("Term was updated");
                }
                dataAdapter.notifyDataSetChanged();
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