package com.ceylinco.ceylincocustomerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Prishan Maduka on 7/11/2016.
 */
public class SplashActivity extends Activity {
    private static int SPLASH_TIMEOUT_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        //Check for Runtime Permissions
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            startHandler();
        }else{
            startHandler();
        }
    }

    //Calling Handler
    private void startHandler() {
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


}
