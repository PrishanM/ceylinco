package com.ceylinco.ceylincocustomerapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceylinco.ceylincocustomerapp.existingPolicy.ExistingPolicyActivity;
import com.ceylinco.ceylincocustomerapp.existingPolicy.SelectVehicleActivity;
import com.ceylinco.ceylincocustomerapp.inquiries.InquiryActivity;
import com.ceylinco.ceylincocustomerapp.newInsurances.NewInsuranceVehicleListActivity;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgNew;
    private ImageView imgExisting;
    private ImageView imgInquiries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText(getResources().getString(R.string.option_menu_message));
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);

        initialize();
    }

    private void initialize() {
        imgNew = (ImageView)findViewById(R.id.idNewInsurance);
        imgExisting = (ImageView)findViewById(R.id.idExistingInsurance);
        imgInquiries = (ImageView)findViewById(R.id.idInquiries);

        imgNew.setOnClickListener(this);
        imgExisting.setOnClickListener(this);
        imgInquiries.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.idNewInsurance){
            Intent newInsurance = new Intent(MainActivity.this,NewInsuranceVehicleListActivity.class);
            startActivity(newInsurance);
        }else if(v.getId()==R.id.idExistingInsurance){

            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
            String userName = sharedPref.getString(getString(R.string.user_name), "");
            String password = sharedPref.getString(getString(R.string.user_password), "");

            if(userName!=null && !userName.isEmpty() && password!=null && !password.isEmpty()){
                Intent existingInsurance = new Intent(MainActivity.this,SelectVehicleActivity.class);
                startActivity(existingInsurance);
            }else {
                Intent existingInsurance = new Intent(MainActivity.this,ExistingPolicyActivity.class);
                startActivity(existingInsurance);
            }

        }else if(v.getId()==R.id.idInquiries){
            Intent inquiryInsurance = new Intent(MainActivity.this,InquiryActivity.class);
            startActivity(inquiryInsurance);
        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to exit the application?");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finishAffinity();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
