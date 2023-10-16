package com.example.dementedcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.util.Log; // Import Log for logging
import android.widget.Toast; // Import Toast for displaying error messages

public class predictform extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predictform);

        // Spinners
        Spinner genderSpinner = findViewById(R.id.etGender);
        Spinner chestSpinner  = findViewById(R.id.etCps);
        Spinner bloodsugar = findViewById(R.id.etFbs);
        Spinner ecgSpprinter = findViewById(R.id.etEcg);
        Spinner eiaSpprinter = findViewById(R.id.etEia);
        Spinner pealexSpprinter = findViewById(R.id.etPeakEX);
        Spinner fluSpprinter = findViewById(R.id.etFlu);
        Spinner thresultSpprinter = findViewById(R.id.etthreresult);

        // Buttons
        Button btnSubmit = findViewById(R.id.btnSubmit);
        Button btncansel = findViewById(R.id.btncansel);

        // User input values
        EditText etAge = findViewById(R.id.etAge);
        EditText etRbp = findViewById(R.id.etRbp);
        EditText etChl = findViewById(R.id.etChl);

        // Define spinner options
        String[] genderOptions = {"Male", "Female", "Other"};
        String[] chestOptions  = {"0","1","2","3"};
        String[] bloodSugarOptions = {"0 for <=120 mg/dl","1 for >120 mg/dl"};
        String[] ecfOptions = {"0","1","2"};
        String[] eiaOptions = {"Yes","No"};
        String[] peakEXOptions = {"0","1","2"};
        String[] FluOptions = {"0","1","2","3"};
        String[] thresultOptions = {"0","1","2","3"};

        // Create ArrayAdapter and set it on Spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        ArrayAdapter<String> Chestadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, chestOptions);
        Chestadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chestSpinner.setAdapter(Chestadapter);

        ArrayAdapter<String> BloodSugaradapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bloodSugarOptions);
        BloodSugaradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodsugar.setAdapter(BloodSugaradapter);

        ArrayAdapter<String> Ecgadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ecfOptions);
        Ecgadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ecgSpprinter.setAdapter(Ecgadapter);

        ArrayAdapter<String> Eiaadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eiaOptions);
        Eiaadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eiaSpprinter.setAdapter(Eiaadapter);

        ArrayAdapter<String> peakEXadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, peakEXOptions);
        peakEXadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pealexSpprinter.setAdapter(peakEXadapter);

        ArrayAdapter<String> Fluadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, FluOptions);
        Fluadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fluSpprinter.setAdapter(Fluadapter);

        ArrayAdapter<String> threresultadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, thresultOptions);
        threresultadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        thresultSpprinter.setAdapter(threresultadapter);

        // OnClickListener for the Submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user input values
                String age = etAge.getText().toString();
                String rbp = etRbp.getText().toString();
                String chl = etChl.getText().toString();

                // Retrieve selected spinner values
                String selectedGender = genderSpinner.getSelectedItem().toString();
                String selectedChest = chestSpinner.getSelectedItem().toString();
                String selectedBloodSugar = bloodsugar.getSelectedItem().toString();
                String selectedEcg = ecgSpprinter.getSelectedItem().toString();
                String selectedEia = eiaSpprinter.getSelectedItem().toString();
                String selectedPeakEX = pealexSpprinter.getSelectedItem().toString();
                String selectedFlu = fluSpprinter.getSelectedItem().toString();
                String selectedThresult = thresultSpprinter.getSelectedItem().toString();

                // Perform backend validation checks
                if (isInputValid(age, rbp, chl, selectedGender, selectedChest, selectedBloodSugar, selectedEcg, selectedEia, selectedPeakEX, selectedFlu, selectedThresult)) {
                    // Log user input values
                    Log.d("User Input", "Age: " + age);
                    Log.d("User Input", "Rbp: " + rbp);
                    Log.d("User Input", "Chl: " + chl);
                    // ... Log other input data

                    // Create an intent to start the new activity
                    Intent intent = new Intent(predictform.this, AIPredictionActivity.class);

                    // Add the user input data as extras to the intent
                    intent.putExtra("Age", age);
                    intent.putExtra("Rbp", rbp);
                    intent.putExtra("Chl", chl);
                    // ... Add other input data as extras

                    // Start the new activity
                    startActivity(intent);
                } else {
                    // Display an error message to the user if validation fails
                    // For example, you can show a Toast message or set an error message on the EditText fields.
                    Toast.makeText(predictform.this, "Invalid input. Please check your input values.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // OnClickListener for the Cancel button
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

    // Backend validation function
    private boolean isInputValid(String age, String rbp, String chl, String selectedGender, String selectedChest, String selectedBloodSugar, String selectedEcg, String selectedEia, String selectedPeakEX, String selectedFlu, String selectedThresult) {
        // Add your validation logic here
        // Return true if input is valid, false otherwise
        // Example: Check if age, rbp, and chl are not empty and meet certain criteria
        // You can customize this logic based on your requirements.

        if (age.isEmpty() || rbp.isEmpty() || chl.isEmpty()) {
            return false; // Input fields should not be empty
        }

        // Check if a spinner value is not selected
        if (selectedGender.equals("Select Gender") || selectedChest.equals("Select Chest Pain Type") || selectedBloodSugar.equals("Select Blood Sugar Level") || selectedEcg.equals("Select ECG Result") || selectedEia.equals("Select Exercise Induced Angina") || selectedPeakEX.equals("Select Peak Exercise ST Segment") || selectedFlu.equals("Select Fluoroscopy Result") || selectedThresult.equals("Select Thalassemia Result")) {
            return false; // Spinner values should be selected
        }

        // Add more validation checks here as needed
        try {
            int ageValue = Integer.parseInt(age);
            int rbpValue = Integer.parseInt(rbp);
            int chlValue = Integer.parseInt(chl);

            if (ageValue <= 0 || rbpValue <= 0 || chlValue <= 0) {
                return false; // Values should be greater than 0
            }

            // Add more specific validation checks here as needed

        } catch (NumberFormatException e) {
            return false; // Input values should be valid integers
        }

        return true; // Input is valid
    }
}
