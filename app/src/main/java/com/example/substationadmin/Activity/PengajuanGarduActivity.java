package com.example.substationadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.substationadmin.Helper.StringHelper;
import com.example.substationadmin.Model.Gardu;
import com.example.substationadmin.Model.Pengaduan;
import com.example.substationadmin.Model.User;
import com.example.substationadmin.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PengajuanGarduActivity extends AppCompatActivity implements LocationListener {

    ProgressDialog progressDialog;
    SupportMapFragment supportMapFragment;
    LocationManager locationManager;
    GoogleMap map;
    CheckBox cbVerify;

    DatabaseReference rootRef;
    FirebaseAuth mAuth;
    HashMap<String, Gardu> listGardu;
    User user;

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "SUBSTATION";
    private final static String DATA_KEY = "DATA_KEY";
    private final static String DATA_KEY1 = "DATA_KEY1";

    StringHelper helper = new StringHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengajuan_gardu);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        rootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        cbVerify = findViewById(R.id.cbVerify);
        cbVerify.setChecked(false);
        grantPermission();
        checkLocationIsEnableOrNot();
        getLocation();
    }

    private void getLocation() {
        try{
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, this);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    private void checkLocationIsEnableOrNot() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        try{
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(!gpsEnabled && !networkEnabled){
            new AlertDialog.Builder(PengajuanGarduActivity.this)
                    .setTitle("Enable GPS Service")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).setNegativeButton("Cancel", null)
                    .show();
        }
    }

    private void grantPermission() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        final List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    map = googleMap;
                    LatLng latLng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                    MarkerOptions options = new MarkerOptions().position(latLng).title("I am here").icon(BitmapDescriptorFactory.fromResource(R.drawable.signs));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                    map.addMarker(options);
                    rootRef.child("Gardu").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            googleMap.clear();
                            map = googleMap;
                            LatLng latLng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                            MarkerOptions options = new MarkerOptions().position(latLng).title("I am here").icon(BitmapDescriptorFactory.fromResource(R.drawable.signs));
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                            map.addMarker(options);
                            listGardu = new HashMap<>();
                            for(DataSnapshot child : dataSnapshot.getChildren()){
                                Gardu gardu = child.getValue(Gardu.class);
                                if(gardu.getDiacc().equals("0")){
                                    listGardu.put(gardu.getId(), gardu);
                                }

                            }

                            for (Map.Entry<String, Gardu> entry : listGardu.entrySet()) {
                                String key = entry.getKey();
                                Gardu gardu = entry.getValue();
                                double longitude = Double.parseDouble(gardu.getLongitude());
                                double latitude = Double.parseDouble(gardu.getLatitude());
                                if(gardu.getDiacc().equals("1")){
                                    if(gardu.getStatus().equals("0")){
                                        map.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(gardu.getId()).icon(BitmapDescriptorFactory.fromResource(R.drawable.gardu_aman)));
                                    }
                                    else if(gardu.getStatus().equals("1")){
                                        map.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(gardu.getId()).icon(BitmapDescriptorFactory.fromResource(R.drawable.gardu_maintenance)));
                                    }
                                    else if(gardu.getStatus().equals("2")){
                                        map.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(gardu.getId()).icon(BitmapDescriptorFactory.fromResource(R.drawable.gardu_mati)));
                                    }
                                }
                                else if(gardu.getDiacc().equals("0")){
                                    map.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(gardu.getId()));
                                }


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            if(marker.getTitle().equals("I am here")) {
                                final AlertDialog alertDialog = new AlertDialog.Builder(PengajuanGarduActivity.this).create();
                                LayoutInflater inflater = getLayoutInflater();
                                View dialogView = inflater.inflate(R.layout.alert_dialog_acc_gardu, null);
                                alertDialog.setView(dialogView);
                                alertDialog.setCancelable(true);
                                alertDialog.setIcon(R.drawable.gardu_fix);
                                alertDialog.setTitle("Form Maintenance Gardu");
                                alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);

                                final TextView tvID = dialogView.findViewById(R.id.tvId);
                                final TextView tvAlamat = dialogView.findViewById(R.id.tvAlamat);
                                final TextView tvStatus = dialogView.findViewById(R.id.tvStatus);
                                final TextView tvDiajukanOleh = dialogView.findViewById(R.id.tvDiajukan);
                                final TextView tvTanggal = dialogView.findViewById(R.id.tvTanggal);
                                final Button btnYa = dialogView.findViewById(R.id.btnYa);
                                final Button btnTidak = dialogView.findViewById(R.id.btnTidak);

                                btnYa.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                });

                                btnTidak.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                });

                                alertDialog.show();
                            }
                            else{
                                Toast.makeText(PengajuanGarduActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        }
                    });
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
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
}
