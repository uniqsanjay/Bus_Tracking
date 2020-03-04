package com.mtcbustracking.bustracking;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Driver extends AppCompatActivity {

    ImageView alert;
    DatabaseReference df;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        alert = findViewById(R.id.sos);
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                df = FirebaseDatabase.getInstance().getReference().child("bustrack");
                df.child("Status").setValue( "BrakeDown");
            }
        });
    }
}
