package com.example.user2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private String provider;
    private TextView latTextView;
    private TextView longTextView;
    private LocationManager locationManager;
    private int REQUEST_LOCATION = 19;
    String link;

    private DatabaseReference dRef;
    private LocationListener locationListener;
    HashMap<String, Double> latlong = new HashMap<>();

    MediaRecorder recorder;
    String fileName = null;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private DatabaseReference linkReference;
    ProgressDialog progressBar;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                //User denied Permission.
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dRef = FirebaseDatabase.getInstance().getReference("Loaction");
        linkReference = FirebaseDatabase.getInstance().getReference("link");


        setContentView(R.layout.activity_main);
        latTextView = findViewById(R.id.showLat);
        longTextView = findViewById(R.id.showLong);
        LocationManager locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enableGPS = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        progressBar = new ProgressDialog(this);

        fileName = getExternalFilesDir(null).getAbsolutePath();
        fileName += "/recorded_audio.3gp";



        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("flag");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String flag = (String) snapshot.getValue();

                if(flag.equals("true")){
                    Toast.makeText(MainActivity.this, "Message", Toast.LENGTH_SHORT).show();
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    link = (String) snapshot.child("link").child("link").getValue();





                    try {
                        mediaPlayer.reset();
                        if(link != null){
                            mediaPlayer.setDataSource(link);
                            mediaPlayer.prepareAsync();
                        }


                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();
                            }
                        });
                        //mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                databaseReference.setValue("false");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (!enableGPS) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        if(checkPermissions()) {
            @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(provider);

            if(location!=null) {
                onLocationChanged(location);
            }

        }

    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        return true;
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        if(checkPermissions()) {
            locationManager.requestLocationUpdates(provider, 300, 0, this);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double lat = location.getLatitude();
        double log = location.getLongitude();
        updateDatabase(lat, log);
        latlong.put("latitude", lat);
        latlong.put("longitude", log);
        for(Map.Entry ele : latlong.entrySet()) {
            Log.d("device_log", ele.getKey() + " " + ele.getValue());

        }
        //latTextView.setText(String.valueOf(lat));
        //longTextView.setText(String.valueOf(log));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public void updateDatabase(double lat, double lon){

        Map<String, Double> users = new HashMap<>();
        users.put("Latitude", lat);
        users.put("Longitude", lon);
        dRef.setValue(users);

        dRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double latitude1 = (double) dataSnapshot.child("Latitude").getValue();
                double longitude1 = (double) dataSnapshot.child("Longitude").getValue();

                Log.i("qwerty", String.valueOf(latitude1));
                Log.i("qwerty", String.valueOf(longitude1));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        //dRef.child("Latitude").push().setValue(lat);
        //dRef.child("Longitude").push().setValue(lon);
    }
}