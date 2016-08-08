package com.ceylinco.ceylincocustomerapp.inquiries.locationServices;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ceylinco.ceylincocustomerapp.R;
import com.ceylinco.ceylincocustomerapp.models.LocationModel;
import com.ceylinco.ceylincocustomerapp.models.Result;
import com.ceylinco.ceylincocustomerapp.util.DetectNetwork;
import com.ceylinco.ceylincocustomerapp.util.JsonRequestManager;
import com.ceylinco.ceylincocustomerapp.util.Notifications;
import com.ceylinco.ceylincocustomerapp.util.PermissionUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by Prishan Maduka on 8/7/2016.
 */
public class ValueAddedServicesActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private SupportMapFragment mapFragment;
    private double currentLatitude,currentLongitude;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;
    private Notifications notifications;
    private AlertDialog alertDialog;
    private Context context;
    private String type;
    private View loadingSpinner;
    final Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branches_layout);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        loadingSpinner = (View)findViewById(R.id.loadingSpinnerProducts);
        mapFragment.getMapAsync(this);
        notifications = new Notifications();
        context = this;

        type = getIntent().getStringExtra("TYPE");

        DetectNetwork.setmContext(context);

        //Check network connectivity
        if(!DetectNetwork.isConnected()){
            alertDialog = notifications.showNetworkNotification(context);
            alertDialog.show();
        }

        if(!DetectNetwork.isLocationEnabled(context)){
            alertDialog = notifications.showGPSDisabledNotification(context);
            alertDialog.show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        enableLocation();
    }

    private void enableLocation(){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermission(ValueAddedServicesActivity.this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }else if (map != null) {
            map.setMyLocationEnabled(true);
            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            List<String> providers = lm.getProviders(true);
            Location l = null;

            for (int i = 0; i < providers.size(); i++) {
                l = lm.getLastKnownLocation(providers.get(i));
                if (l != null) {
                    currentLatitude = l.getLatitude();
                    currentLongitude = l.getLongitude();
                    break;
                }
            }
            MarkerOptions marker = new MarkerOptions().position(
                    new LatLng(currentLatitude, currentLongitude)).title("My Location").snippet("");
            marker.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(currentLatitude, currentLongitude)).zoom(15).build();

            map.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));

            map.addMarker(marker);
            getLocations();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            PermissionUtils.PermissionDeniedDialog
                    .newInstance(true).show(getSupportFragmentManager(), "dialog");
            mPermissionDenied = false;
        }
    }

    private void getLocations() {
        loadingSpinner.setVisibility(View.VISIBLE);
        JsonRequestManager.getInstance(context).getLocationDetails("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+currentLatitude+","+currentLongitude+"&radius=5000&types="+type+"&sensor=true&key="+getResources().getString(R.string.server_key),callback);
    }

    private void getNextLocations(final String token) {
        runnable = new Runnable() {
            @Override
            public void run() {
                JsonRequestManager.getInstance(context).getLocationDetails("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+currentLatitude+","+currentLongitude+"&radius=5000&types="+type+"&sensor=true&key="+getResources().getString(R.string.server_key)+"&pagetoken="+token,callback);
            }
        };

        handler.postDelayed(runnable, 3000);
    }

    JsonRequestManager.getLocationDetailsRequest callback = new JsonRequestManager.getLocationDetailsRequest(){

        @Override
        public void onSuccess(LocationModel locationModel) {
            loadingSpinner.setVisibility(View.GONE);
            if(locationModel.getResults().size()>0){
                addMarkers(locationModel.getResults());
            }

            try{
                if(locationModel.getNextPageToken()!=null){
                    getNextLocations(locationModel.getNextPageToken());

                }else{
                    Log.d("LOCATION","NO DATA");
                }
            }catch (NullPointerException e){
                Log.d("LOCATION","NO MORE LOCATIONS");
            }

        }

        @Override
        public void onError(String status) {
            loadingSpinner.setVisibility(View.GONE);
        }
    };

    private void addMarkers(List<Result> results){
        for(int i=0;i<results.size();i++){
            Marker locationMarker = map.addMarker(new MarkerOptions()
                    .position(new LatLng(results.get(i).getGeometry().getLocation().getLat(),results.get(i).getGeometry().getLocation().getLng()))
                    .title(results.get(i).getName())
                    .snippet(results.get(i).getVicinity()));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        Log.d("LOCATION","HANDLER CLOSED");
    }
}
