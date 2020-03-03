package com.mtcbustracking.bustracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ViewBusCapacity extends AppCompatActivity {

    ArrayList place, occupied, available;
    RecyclerView bus_capacitylist;
    DatabaseReference df;
    int occup, avail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bus_capacity);


        bus_capacitylist = findViewById(R.id.bus_capacity);

        getBusLocation();
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(this, 2000);

            }
        }, 3000);*/
    }

    public void getBusLocation(){
        //Intent i = getIntent();
        place = new ArrayList();
        occupied = new ArrayList();
        available = new ArrayList();
        place.add("T.nagar");
        place.add("Vadapalani");
        df = FirebaseDatabase.getInstance().getReference().child("bustrack");
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //double lat = Long.parseLong(dataSnapshot.child("latitude").getValue().toString());
                //double lan = Long.parseLong(dataSnapshot.child("longitude").getValue().toString());
                occup = Integer.parseInt(dataSnapshot.child("count").getValue().toString());
                avail = 50-occup;
                occupied.add(String.valueOf(occup));
                available.add(String.valueOf(avail));

                //Getting the address using geocoder
                /*Geocoder geocoder = new Geocoder(ViewBusCapacity.this, Locale.getDefault());
                String result = null;
                try {
                    List<Address> addressList = geocoder.getFromLocation(
                            lat, lan, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)).append("\n");
                        }
                        sb.append(address.getSubLocality());
                        result = sb.toString();
                        place.add(result);
                    }
                } catch (IOException e) {
                    Log.e("Exception", "Unable connect to Geocoder", e);
                }*/

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewBusCapacity.this);
                bus_capacitylist.setLayoutManager(linearLayoutManager);
                Custom_Bus_Capacity bus_adapt = new Custom_Bus_Capacity(ViewBusCapacity.this, place, occupied, available);
                bus_capacitylist.setAdapter(bus_adapt);
                bus_adapt.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
