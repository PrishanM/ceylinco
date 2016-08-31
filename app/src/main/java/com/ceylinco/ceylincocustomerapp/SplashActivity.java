package com.ceylinco.ceylincocustomerapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prishan Maduka on 7/11/2016.
 */
public class SplashActivity extends Activity {
    private final List<String> permissionList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        //Check for Runtime Permissions
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            startHandler();
        }else{
            int cameraState = checkSelfPermission(Manifest.permission.CAMERA);
            int locationServicesState = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            int locationCoarseState = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            int storageState = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if(cameraState != PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.CAMERA);
            }
            if(locationServicesState !=PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if(locationCoarseState !=PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if(storageState !=PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (permissionList.size()>0){
                String[] permissionArray = new String[permissionList.size()];
                permissionList.toArray(permissionArray);
                int REQUEST_CODE = 1100;
                requestPermissions(permissionArray, REQUEST_CODE);
            }else{
                startHandler();
            }
        }
    }

    //Calling Handler
    private void startHandler() {
        int SPLASH_TIMEOUT_TIME = 3000;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                //Start next activity
                Intent mainActivity = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        }, SPLASH_TIMEOUT_TIME);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1100:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startHandler();
                } else {
                    // Permission Denied
                    System.exit(1);
                }
                break;
            default:
                System.exit(1);
                break;
        }
    }


}
