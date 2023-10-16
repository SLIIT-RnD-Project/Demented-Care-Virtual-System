package com.example.dementedcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.widget.RadioButton;
import android.widget.Toast; // Import Toast for displaying messages
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddDoctorScreen extends AppCompatActivity {

    // Declare variables-----------------------------------------------------------------------------
    private FirebaseAuth firebaseAuth;

    private EditText emailEditText;
    private EditText passwordEditText;
    private RadioGroup radioGroup;
    private EditText contactNumberEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor_screen);

        // Initialize EditText views and submit button
        emailEditText = findViewById(R.id.etemail);
        passwordEditText = findViewById(R.id.etpassword);
        radioGroup = findViewById(R.id.radioGroupOptions);
        contactNumberEditText = findViewById(R.id.etconnum);
        firstNameEditText = findViewById(R.id.etfirstname);
        lastNameEditText = findViewById(R.id.etlastname);

        submitButton = findViewById(R.id.btnSubmit);

        firebaseAuth = FirebaseAuth.getInstance();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFormSubmission();
            }
        });
    }

    //handleFormSubmition
    private void handleFormSubmission() {
        // Retrieve user input
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String contactNumber = contactNumberEditText.getText().toString();

        // Get the selected radio button id
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

        String selectedRadioButtonName = "";
        // Check which radio button was selected
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            selectedRadioButtonName = selectedRadioButton.getText().toString();
        }
        Log.d("RegistrationActivity", "Selected Role: " + selectedRadioButtonName);

        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || contactNumber.isEmpty()) {
            // Show an error message to the user that fields cannot be empty
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        String finalSelectedRadioButtonName = selectedRadioButtonName;

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registration was successful
                            Log.d("RegistrationActivity", "User registration successful! Thank You!");
                            String selectedRole = finalSelectedRadioButtonName;
                            String userId = firebaseAuth.getCurrentUser().getUid();

                            saveUserDataToFirestore(userId, selectedRole, email, firstName, lastName, contactNumber);
                        } else {
                            // Registration failed
                            Log.e("RegistrationActivity", "User registration failed: " + task.getException());
                            Toast.makeText(AddDoctorScreen.this, "User registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void saveUserDataToFirestore(String userId, String selectedRole, String email, String firstName, String lastName, String contactNumber) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersCollectionRef = db.collection("doctors");

        // Create a new document with the user's UID as the document ID
        DocumentReference userDocRef = usersCollectionRef.document(userId);

        // Create a user object to store in Firestore
        User user = new User(userId, selectedRole, email, firstName, lastName, contactNumber);

        // Save the user object to Firestore
        userDocRef.set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("RegistrationActivity", "User data saved to Firestore successfully!");
                        Toast.makeText(AddDoctorScreen.this, "User data saved successfully", Toast.LENGTH_SHORT).show();
                        // Here, you can add any additional actions after successful user registration and data save.
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("RegistrationActivity", "Error saving user data to Firestore: " + e.getMessage());
                        Toast.makeText(AddDoctorScreen.this, "Error saving user data", Toast.LENGTH_SHORT).show();
                    }
                });
    }





    }
