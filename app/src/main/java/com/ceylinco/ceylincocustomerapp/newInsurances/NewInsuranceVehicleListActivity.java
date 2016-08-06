package com.ceylinco.ceylincocustomerapp.newInsurances;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceylinco.ceylincocustomerapp.R;

/**
 * Created by Prishan Maduka on 7/19/2016.
 */
public class NewInsuranceVehicleListActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgCar,imgVan,imgBus,imgLorry,imgBike,imgThreewheeler,imgJeep,imgDoubleCab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_insurance_vehicle_list);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText(getResources().getString(R.string.new_insurance_vehicle_list_title));
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);

        initialize();
    }

    private void initialize() {
        imgCar = (ImageView)findViewById(R.id.idCar);
        imgVan = (ImageView)findViewById(R.id.idVan);
        imgBus = (ImageView)findViewById(R.id.idBus);
        imgLorry = (ImageView)findViewById(R.id.idLorry);
        imgBike = (ImageView)findViewById(R.id.idBike);
        imgThreewheeler = (ImageView)findViewById(R.id.idWheeler);
        imgJeep = (ImageView)findViewById(R.id.idJeep);
        imgDoubleCab = (ImageView)findViewById(R.id.idCab);

        imgCar.setOnClickListener(this);
        imgVan.setOnClickListener(this);
        imgBus.setOnClickListener(this);
        imgLorry.setOnClickListener(this);
        imgBike.setOnClickListener(this);
        imgThreewheeler.setOnClickListener(this);
        imgJeep.setOnClickListener(this);
        imgDoubleCab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.idCar){
            Intent carInsurance = new Intent(NewInsuranceVehicleListActivity.this,InsuranceTypesActivity.class);
            startActivity(carInsurance);
        }else if(v.getId()==R.id.idVan){
            Intent carInsurance = new Intent(NewInsuranceVehicleListActivity.this,InsuranceTypesActivity.class);
            startActivity(carInsurance);
        }else if(v.getId()==R.id.idBus){
            Intent carInsurance = new Intent(NewInsuranceVehicleListActivity.this,InsuranceTypesActivity.class);
            startActivity(carInsurance);
        }else if(v.getId()==R.id.idLorry){
            Intent carInsurance = new Intent(NewInsuranceVehicleListActivity.this,InsuranceTypesActivity.class);
            startActivity(carInsurance);
        }else if(v.getId()==R.id.idBike){
            Intent carInsurance = new Intent(NewInsuranceVehicleListActivity.this,InsuranceTypesActivity.class);
            startActivity(carInsurance);
        }else if(v.getId()==R.id.idWheeler){
            Intent carInsurance = new Intent(NewInsuranceVehicleListActivity.this,InsuranceTypesActivity.class);
            startActivity(carInsurance);
        }else if(v.getId()==R.id.idJeep){
            Intent carInsurance = new Intent(NewInsuranceVehicleListActivity.this,InsuranceTypesActivity.class);
            startActivity(carInsurance);
        }else if(v.getId()==R.id.idCab){
            Intent carInsurance = new Intent(NewInsuranceVehicleListActivity.this,InsuranceTypesActivity.class);
            startActivity(carInsurance);
        }

    }
}
