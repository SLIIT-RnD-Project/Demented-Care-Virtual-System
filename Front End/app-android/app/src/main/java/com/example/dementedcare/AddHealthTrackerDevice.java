package com.example.dementedcare;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.Nullable;



import android.app.AlertDialog;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AddHealthTrackerDevice extends AppCompatActivity {

    Button scan_btn;
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_health_tracker_device);
        scan_btn = findViewById(R.id.scanner);
        textview = findViewById(R.id.text);

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(AddHealthTrackerDevice.this);
                intentIntegrator.setPrompt("");
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.initiateScan();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(resultCode, data);
        if (intentResult != null) {
            String contents = intentResult.getContents();
            if (contents != null) {
                textview.setText(contents);

                // Initialize Firebase Realtime Database reference
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("scanned_qr_codes");

                // Push the scanned content to the database
                String key = databaseReference.push().getKey();
                databaseReference.child(key).setValue(contents);



                showPopupMessage("QR Code Scanned and Stored", "Scanned content: " + contents);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
    private void showPopupMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

}