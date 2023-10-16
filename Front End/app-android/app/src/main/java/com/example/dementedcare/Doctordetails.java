package com.example.dementedcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;

import android.util.Log;
import android.view.View;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Doctordetails extends AppCompatActivity {

    RecyclerView recyclerView;  // RecyclerView
    ArrayList<data> userArrayList;   // Data java class
    MyAdapter myAdapter;    // MyAdapter class
    FirebaseFirestore db;    // Database
    ProgressDialog progressDialog; //progress bar
    String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctordetails);

//       start code
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data....");
        progressDialog.show();

        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firebase Firestore database
        db = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<>();
        myAdapter = new MyAdapter(this, userArrayList);
        Email = getIntent().getStringExtra("email");
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(data doctor) {
                openDoctorDetailsActivity(doctor);
            }

            @Override
            public void onItemClick(String userId) {

            }
        });

        recyclerView.setAdapter(myAdapter);

        // Getting the data from Firestore
        EventChangeListener();
    }

    private void openDoctorDetailsActivity(data doctor) {
        Log.d("listView", "Opening DoctorDetailsActivity");
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("selected_doctor", doctor);
        startActivity(intent);
    }

    private void EventChangeListener() {
        // Referring to the collection
        db.collection("doctors").orderBy("email", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        // Callback method
                        if (error != null) {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                            Log.e("Firebase error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                userArrayList.add(dc.getDocument().toObject(data.class));
                            }
                        }
                        myAdapter.notifyDataSetChanged();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });
    }
    public void openViewMorePage(View view) {
        Intent intent = new Intent(this, ViewMoreActivity.class);
        intent.putExtra("email", Email); // Pass the userId to ViewMoreActivity
        startActivity(intent);
    }


    //        end code
}





