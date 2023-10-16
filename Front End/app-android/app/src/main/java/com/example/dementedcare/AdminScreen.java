package com.example.dementedcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AdminScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        ImageButton manageDoctorsButton = findViewById(R.id.button);  // doctor button
        ImageButton manageNurseButton = findViewById(R.id.button2);  // nurse button

        manageDoctorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddDoctorScreen();
            }
        });

        manageNurseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openManageNurseScreen();
            }
        });
    }
    private void openAddDoctorScreen() {
        Intent intent = new Intent(this, AddDoctorScreen.class);
        startActivity(intent);
    }

    private void openManageNurseScreen() {
        Intent intent = new Intent(this,AddDoctorScreen.class);
        startActivity(intent);
    }
}