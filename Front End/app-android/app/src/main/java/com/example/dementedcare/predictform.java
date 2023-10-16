package com.example.dementedcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;
import android.content.Intent;

import android.view.View;
import android.widget.Button;

import android.widget.ArrayAdapter;


public class predictform extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predictform);

        //Sprinters
        Spinner genderSpinner = findViewById(R.id.etGender);
        Spinner chestSpinner  = findViewById(R.id.etCps);
        Spinner bloodsugar = findViewById(R.id.etFbs);
        Spinner ecgSpprinter = findViewById(R.id.etEcg);
        Spinner eiaSpprinter = findViewById(R.id.etEia);
        Spinner pealexSpprinter = findViewById(R.id.etPeakEX);
        Spinner fluSpprinter = findViewById(R.id.etFlu);
        Spinner thresultSpprinter = findViewById(R.id.etthreresult);

        //Buttons
        Button btnSubmit = findViewById(R.id.btnSubmit);
        Button btncansel = findViewById(R.id.btncansel);




        // Define an array of gender options
        String[] genderOptions = {"Male", "Female", "Other"};
        String[] chestOptions  = {"0","1","2","3"};
        String[] bloodSugarOptions = {"0 for <=120 mg/dl","1 for >120 mg/dl"};
        String[] ecfOptions = {"0","1","2"};
        String[] eiaOptions = {"Yes","No"};
        String[] peakEXOptions = {"0","1","2"};
        String[] FluOptions = {"0","1","2","3"};
        String[] thresultOptions = {"0","1","2","3"};

        // Create an ArrayAdapter and set it on the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        ArrayAdapter<String> Chestadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, chestOptions);
        Chestadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chestSpinner.setAdapter(Chestadapter);

        ArrayAdapter<String>BloodSugaradapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bloodSugarOptions);
        BloodSugaradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodsugar.setAdapter(BloodSugaradapter);

        ArrayAdapter<String>Ecgadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ecfOptions);
        Ecgadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ecgSpprinter.setAdapter(Ecgadapter);

        ArrayAdapter<String>Eiaadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eiaOptions);
        Eiaadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eiaSpprinter.setAdapter(Eiaadapter);

        ArrayAdapter<String>peakEXadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, peakEXOptions);
        peakEXadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pealexSpprinter.setAdapter(peakEXadapter);

        ArrayAdapter<String>Fluadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, FluOptions);
        Fluadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fluSpprinter.setAdapter(Fluadapter);

        ArrayAdapter<String>threresultadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, thresultOptions);
        threresultadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        thresultSpprinter.setAdapter(threresultadapter);


        //onclick
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the new activity
                Intent intent = new Intent(predictform.this, AIPredictionActivity.class);


                // Add any data you want to pass to the new activity using intent extras
                // intent.putExtra("key", "value");

                // Start the new activity
                startActivity(intent);
            }
        });
        btncansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the new activity
                Intent intent = new Intent(predictform.this, PatientHealthDetails.class);


                // Add any data you want to pass to the new activity using intent extras
                // intent.putExtra("key", "value");

                // Start the new activity
                startActivity(intent);
            }
        });
    }
}