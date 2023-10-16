package com.example.dementedcare;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;


public class AIPredictionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aiprediction);
        Button aiPredictionButton = findViewById(R.id.btn);

        // Add ValueEventListener to listen for data changes

        aiPredictionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to open AIPredictionActivity
                Intent intent = new Intent(AIPredictionActivity.this, PatientHealthDetails.class);
                startActivity(intent);
            }
        });
    }
}