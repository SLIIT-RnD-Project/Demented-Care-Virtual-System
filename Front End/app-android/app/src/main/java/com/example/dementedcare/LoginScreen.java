package com.example.dementedcare;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginScreen extends AppCompatActivity {

//    Declare in Variables
    private FirebaseAuth firebaseAuth;

    private EditText emailEditText;

    private EditText passwordEditText;

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        //start
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });
    }
    //handleLogin start
    private void handleLogin() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login successful, get the user role and route to corresponding activity
                            String userId = firebaseAuth.getCurrentUser().getUid();
                            getUserRoleAndOpenActivity(userId);
                        } else {
                            // Login failed
                            Toast.makeText(LoginScreen.this, "Invalid email/password", Toast.LENGTH_SHORT).show();
                            Log.e("LoginActivity", "Login failed: " + task.getException());
                        }
                    }
                });

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login successful, get the user role and route to corresponding activity
                            String userId = firebaseAuth.getCurrentUser().getUid();
                            getUserRoleAndOpenActivity(userId);
                        } else {
                            // Login failed
                            Toast.makeText(LoginScreen.this, "Login failed", Toast.LENGTH_SHORT).show();
                            Log.e("LoginActivity", "Login failed: " + task.getException());
                        }
                    }
                });
    }
    //handleLogin End

    private void getUserRoleAndOpenActivity(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("doctors").document(userId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                String userRole = document.getString("selectedRole");
                                openActivityBasedOnRole(userRole);
                            } else {
                                Log.d("LoginActivity", "User document not found");
                            }
                        } else {
                            Log.e("LoginActivity", "Error getting user document: " + task.getException());
                        }
                    }
                });
    }

    private void openActivityBasedOnRole(String userRole) {
        Intent intent;

        // Check the user role and create an Intent for the corresponding activity
        if (userRole.equals(" Doctor")) {
            // If the user is a Doctor, open DoctorHome activity
            intent = new Intent(LoginScreen.this, DoctorScreen.class);
        } else if (userRole.equals(" Nurse")) {
            // If the user is a Nurse, open NurseHome activity
            intent = new Intent(LoginScreen.this, NurseScreen.class);
        } else if (userRole.equals(" Admin")) {
            // If the user is an Admin, open MainActivity (or the appropriate admin activity)
            intent = new Intent(LoginScreen.this, AdminScreen.class);
        } else {
            // If the user role is not recognized or an error occurs, show a toast message and stay on the login screen
            Toast.makeText(this, "Invalid user role", Toast.LENGTH_SHORT).show();
            return;
        }

        // Start the selected activity and finish the login activity to prevent the user from going back to it
        startActivity(intent);
        finish();
    }

}