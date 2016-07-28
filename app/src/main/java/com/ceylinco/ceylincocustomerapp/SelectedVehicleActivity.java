package com.ceylinco.ceylincocustomerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Prishan Maduka on 7/24/2016.
 */
public class SelectedVehicleActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView statusOfClaims,policyDetails,vvipAccidents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_vehicle_policy_layout);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText("WPCAC1010");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);

        initialize();
    }

    private void initialize() {

        statusOfClaims = (ImageView)findViewById(R.id.idStatusOfClaims);
        policyDetails = (ImageView)findViewById(R.id.idPolicyDetails);
        vvipAccidents = (ImageView)findViewById(R.id.idVVIPAccidents);

        statusOfClaims.setOnClickListener(this);
        policyDetails.setOnClickListener(this);
        vvipAccidents.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.idStatusOfClaims){
            Intent claimsIntent = new Intent(SelectedVehicleActivity.this,StatusOfClaimsActivity.class);
            startActivity(claimsIntent);
        }else if(v.getId()==R.id.idPolicyDetails){
            Intent policyDetailsIntent = new Intent(SelectedVehicleActivity.this,PolicyDetailsActivity.class);
            startActivity(policyDetailsIntent);
        }else if(v.getId()==R.id.idVVIPAccidents){

        }
    }
}
