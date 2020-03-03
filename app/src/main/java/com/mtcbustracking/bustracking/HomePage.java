package com.mtcbustracking.bustracking;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

public class HomePage extends AppCompatActivity implements OnMapReadyCallback{


    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    LocationManager locationManager;
    GoogleMap map;
    FloatingActionButton gps, seats;
    AppLocationService appLocationService;
    LatLng latLng1;
    DatabaseReference df;
    int tot, availabel, occupied;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        appLocationService = new AppLocationService(
                HomePage.this);

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        gps = findViewById(R.id.gps);
        seats = findViewById(R.id.total_seat);

        getBusLocation();
        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapFragment.getMapAsync(HomePage.this);
            }
        });

        seats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startActivity(new Intent(HomePage.this, ViewBusCapacity.class));
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        try {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    LocationListener locationListener = new LocationListener() {
        @RequiresApi(Build.VERSION_CODES.M)
        public void onLocationChanged(Location location) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            //LatLng latLng1 = new LatLng(13.050428, 80.234941);

            //Getting the address from lat and lan coordinates


            map.clear();
            map.addCircle(new CircleOptions()
            .strokeWidth(0.1f)
            .center(latLng)
            .fillColor(0x40ff0000)
            .radius(50));

            map.addMarker(new MarkerOptions()
            .position(latLng));

            Marker marker = map.addMarker(new MarkerOptions()
            .position(latLng1)
            .title("Est Time")
            .snippet("17min"));
            marker.showInfoWindow();

            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            //map.animateCamera(CameraUpdateFactory.zoomIn());
            map.animateCamera(CameraUpdateFactory.zoomTo(18), 10000, null);

            distance((float)location.getLatitude(),(float)location.getLongitude(), (float) 13.050428, (float) 80.234941);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    public float distance(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);
        Toast.makeText(HomePage.this, "The dist :"+dist, Toast.LENGTH_SHORT).show();
        return dist;
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            Toast.makeText(HomePage.this, "Address :"+locationAddress, Toast.LENGTH_SHORT).show();
        }
    }

    public void getBusLocation(){
        //Intent i = getIntent();
        df = FirebaseDatabase.getInstance().getReference().child("bustrack");
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //double lat = Long.parseLong(dataSnapshot.child("latitude").getValue().toString());
                //double lan = Long.parseLong(dataSnapshot.child("longitude").getValue().toString());
                //latLng1 = new LatLng(lat, lan);
                occupied = Integer.parseInt(dataSnapshot.child("count").getValue().toString());
                availabel = 50-occupied;


                LocationAddress locationAddress = new LocationAddress();
                //locationAddress.getAddressFromLocation(lat, lan, getApplicationContext(), new GeocoderHandler());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
