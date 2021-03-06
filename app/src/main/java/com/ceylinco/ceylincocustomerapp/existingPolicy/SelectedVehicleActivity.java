package com.ceylinco.ceylincocustomerapp.existingPolicy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceylinco.ceylincocustomerapp.R;
import com.ceylinco.ceylincocustomerapp.existingPolicy.accidents.ReportAccidentsActivity;
import com.ceylinco.ceylincocustomerapp.existingPolicy.claims.StatusOfClaimsActivity;
import com.ceylinco.ceylincocustomerapp.existingPolicy.policyDetails.PolicyDetailsActivity;
import com.ceylinco.ceylincocustomerapp.util.Notifications;

/**
 * Created by Prishan Maduka on 7/24/2016.
 */
public class SelectedVehicleActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView statusOfClaims;
    private ImageView policyDetails;
    private ImageView vvipAccidents;

    private AlertDialog alertDialog;
    private final Notifications notifications = new Notifications();
    private Context context;

    private String policyNumber,vehicleNumber;

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
        textviewTitle.setText(getIntent().getStringExtra("VEHICLE"));
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);

        policyNumber = getIntent().getStringExtra("POLICY");
        vehicleNumber = getIntent().getStringExtra("VEHICLE");

        initialize();
    }

    private void initialize() {

        context = SelectedVehicleActivity.this;

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
            claimsIntent.putExtra("VEHICLE",vehicleNumber);
            claimsIntent.putExtra("POLICY",policyNumber);
            startActivity(claimsIntent);
        }else if(v.getId()==R.id.idPolicyDetails){
            Intent policyDetailsIntent = new Intent(SelectedVehicleActivity.this,PolicyDetailsActivity.class);
            policyDetailsIntent.putExtra("VEHICLE",vehicleNumber);
            policyDetailsIntent.putExtra("POLICY",policyNumber);
            startActivity(policyDetailsIntent);
        }else if(v.getId()==R.id.idVVIPAccidents){
            Intent accidentsIntent = new Intent(SelectedVehicleActivity.this,ReportAccidentsActivity.class);
            accidentsIntent.putExtra("VEHICLE",vehicleNumber);
            accidentsIntent.putExtra("POLICY",policyNumber);
            startActivity(accidentsIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_logout){
            alertDialog = notifications.logoutDialog(context);
            alertDialog.show();
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }
}
