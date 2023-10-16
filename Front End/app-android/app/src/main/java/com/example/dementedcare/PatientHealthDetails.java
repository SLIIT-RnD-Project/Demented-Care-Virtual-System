package com.example.dementedcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;

import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.Button;
import android.view.View;



public class PatientHealthDetails extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView tempTextView;
    private TextView bloodPressureTextView;
    private TextView heartRateTextView;
    private TextView bloodO2TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_health_details);

        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("health_device");

        // Initialize TextView elements
        tempTextView = findViewById(R.id.temp);
        bloodPressureTextView = findViewById(R.id.bloodox);
        heartRateTextView = findViewById(R.id.heartrate);
        bloodO2TextView = findViewById(R.id.bloodox2);
        Button aiPredictionButton = findViewById(R.id.aibtn);

        // Add ValueEventListener to listen for data changes

        aiPredictionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to open AIPredictionActivity
                Intent intent = new Intent(PatientHealthDetails.this, AIPredictionActivity.class);
                startActivity(intent);
            }
        });

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Retrieve data for each health parameter
                Double bodyTemperatureDouble = dataSnapshot.child("body_temperature").getValue(Double.class);
                Double bloodPressureDouble = dataSnapshot.child("blood_pressure").getValue(Double.class);
                Double heartRateDouble = dataSnapshot.child("heart_rate").getValue(Double.class);
                Double bloodO2Double = dataSnapshot.child("blood_oxygen_level").getValue(Double.class);

                // Convert Double values to String
                String bodyTemperature = String.valueOf(bodyTemperatureDouble);
                String bloodPressure = String.valueOf(bloodPressureDouble);
                String heartRate = String.valueOf(heartRateDouble);
                String bloodO2 = String.valueOf(bloodO2Double);

                // Update TextView elements with retrieved data
                tempTextView.setText(bodyTemperature);
                bloodPressureTextView.setText(bloodPressure);
                heartRateTextView.setText(heartRate);
                bloodO2TextView.setText(bloodO2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors, if any
            }
        });
    }
}