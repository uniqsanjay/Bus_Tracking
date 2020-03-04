package com.mtcbustracking.bustracking;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Driver extends AppCompatActivity {

    ImageView alert, solved;
    DatabaseReference df;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        alert = findViewById(R.id.sos);
        solved = findViewById(R.id.solved);

        solved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                df = FirebaseDatabase.getInstance().getReference().child("MTC50");
                df.child("Status").setValue( "Solved");
                solved.setVisibility(View.INVISIBLE);
                alert.setVisibility(View.VISIBLE);
            }
        });
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                df = FirebaseDatabase.getInstance().getReference().child("MTC50");
                df.child("Status").setValue( "BrakeDown");
                alert.setVisibility(View.INVISIBLE);
                solved.setVisibility(View.VISIBLE);
            }
        });
    }
}
