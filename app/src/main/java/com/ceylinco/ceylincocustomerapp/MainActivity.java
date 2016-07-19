package com.ceylinco.ceylincocustomerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgNew,imgExisting,imgInquiries;

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
        }

    }
}
