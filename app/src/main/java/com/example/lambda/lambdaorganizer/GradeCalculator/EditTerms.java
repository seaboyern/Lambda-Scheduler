package com.example.lambda.lambdaorganizer.GradeCalculator;

/**
 * Created by Nicholas on 2017-10-15.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lambda.lambdaorganizer.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import GradeCalculatorObjects.Term;

import static com.example.lambda.lambdaorganizer.R.id.Submit_terms;
import static com.example.lambda.lambdaorganizer.R.id.editText;


public class EditTerms extends Activity {

    private Spinner spinnerterm;
    private Button submitBtn;
    protected final LinkedList<Term> terms = new LinkedList<>();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade_calc_edit_terms);

        addSpinnerItems();
        addListenerOnBtn();

    }

    public void addSpinnerItems(){

        spinnerterm = (Spinner)findViewById(R.id.spinner3);

        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("<Please Select Term>");

        for(int i = 0; i < terms.size() - 1; i++){
            spinnerItems.add(terms.get(i).getName());
        }

        spinnerItems.add("<New Term>");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerItems);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerterm.setAdapter(dataAdapter);

        dataAdapter.notifyDataSetChanged();

    }

    public void addListenerOnBtn(){

        submitBtn = (Button) findViewById(Submit_terms);

        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String valueChosen = String.valueOf(spinnerterm);

                if(valueChosen.compareTo("<New Term>") == 0){
                    Term newTerm = new Term(findViewById(editText).toString());
                    terms.add(newTerm);

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