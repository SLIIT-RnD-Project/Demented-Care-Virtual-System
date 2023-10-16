package com.example.dementedcare;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Transaction;

public class ViewMoreActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText contactNumberEditText;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_more);


        // Initialize EditText views and buttons
        firstNameEditText = findViewById(R.id.etfirstname);
        lastNameEditText = findViewById(R.id.etlastname);
        contactNumberEditText = findViewById(R.id.etconnum);

        Button updateButton = findViewById(R.id.btnSubmit);
        Button deleteButton = findViewById(R.id.btndelete);

        firebaseAuth = FirebaseAuth.getInstance();

        // Load user data and populate the EditText fields
        loadUserData();

        // Set an OnClickListener for the update button
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        // Set an OnClickListener for the delete button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmProfileDeletion();
            }
        });
    }

    private void loadUserData() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DocumentReference userDocRef = FirebaseFirestore.getInstance().collection("doctors").document(userId);

            userDocRef.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                // Populate the EditText fields with user data
                                String firstName = documentSnapshot.getString("firstName");
                                String lastName = documentSnapshot.getString("lastName");
                                String contactNumber = documentSnapshot.getString("contactNumber");

                                firstNameEditText.setText(firstName);
                                lastNameEditText.setText(lastName);
                                contactNumberEditText.setText(contactNumber);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle the error
                            Toast.makeText(ViewMoreActivity.this, "Error loading profile data", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void updateProfile() {
        // Retrieve updated data from the EditText fields
        String newFirstName = firstNameEditText.getText().toString();
        String newLastName = lastNameEditText.getText().toString();
        String newContactNumber = contactNumberEditText.getText().toString();

        // Update the Firestore document with the new data
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DocumentReference userDocRef = FirebaseFirestore.getInstance().collection("doctors").document(userId);

            userDocRef.update("firstName", newFirstName, "lastName", newLastName, "contactNumber", newContactNumber)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Update successful
                            Toast.makeText(ViewMoreActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle the error
                            Toast.makeText(ViewMoreActivity.this, "Error updating profile", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void confirmProfileDeletion() {
        // Prompt the user for confirmation to delete their profile
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete your profile?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User confirmed, proceed with deletion
                        deleteProfile();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteProfile() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DocumentReference userDocRef = FirebaseFirestore.getInstance().collection("doctors").document(userId);

            // Delete Firestore document
            userDocRef.delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Firestore document deleted successfully
                            // Now, delete the Firebase Authentication user account
                            currentUser.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // User account deleted successfully
                                                Toast.makeText(ViewMoreActivity.this, "Profile deleted successfully", Toast.LENGTH_SHORT).show();
                                                // Redirect the user to the login screen or perform any other necessary actions.
                                            } else {
                                                // Handle the error
                                                Toast.makeText(ViewMoreActivity.this, "Error deleting profile", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle the error
                            Toast.makeText(ViewMoreActivity.this, "Error deleting profile", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}