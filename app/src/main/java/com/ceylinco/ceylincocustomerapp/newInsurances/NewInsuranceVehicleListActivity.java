package com.ceylinco.ceylincocustomerapp.newInsurances;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.ceylinco.ceylincocustomerapp.models.VehicleMakeModelResponse;
import com.ceylinco.ceylincocustomerapp.util.AppController;
import com.ceylinco.ceylincocustomerapp.util.JsonRequestManager;
import com.ceylinco.ceylincocustomerapp.util.Notifications;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Prishan Maduka on 7/19/2016.
 */
public class NewInsuranceVehicleListActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgCar;
    private ImageView imgVan;
    private ImageView imgBus;
    private ImageView imgLorry;
    private ImageView imgBike;
    private ImageView imgThreeWheeler;
    private ImageView imgJeep;
    private ImageView imgDoubleCab;
    private AlertDialog alertDialog;
    private final Notifications notifications = new Notifications();
    private ProgressDialog progress;
    private Context context;
    private boolean status = false;

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

        context = NewInsuranceVehicleListActivity.this;

        imgCar = (ImageView)findViewById(R.id.idCar);
        imgVan = (ImageView)findViewById(R.id.idVan);
        imgBus = (ImageView)findViewById(R.id.idBus);
        imgLorry = (ImageView)findViewById(R.id.idLorry);
        imgBike = (ImageView)findViewById(R.id.idBike);
        imgThreeWheeler = (ImageView)findViewById(R.id.idWheeler);
        imgJeep = (ImageView)findViewById(R.id.idJeep);
        imgDoubleCab = (ImageView)findViewById(R.id.idCab);

        imgCar.setOnClickListener(this);
        imgVan.setOnClickListener(this);
        imgBus.setOnClickListener(this);
        imgLorry.setOnClickListener(this);
        imgBike.setOnClickListener(this);
        imgThreeWheeler.setOnClickListener(this);
        imgJeep.setOnClickListener(this);
        imgDoubleCab.setOnClickListener(this);

        getVehicleMakeModel();
    }

    private void getVehicleMakeModel() {
        progress = new ProgressDialog(context);
        progress.setMessage("Loading Data...");
        progress.show();
        JsonRequestManager.getInstance(this).getMakeModeltList(getResources().getString(R.string.base_url) + getResources().getString(R.string.vehicle_make_model), callback);
    }

    /**
     * Callback to handle thirdparty form data submit request
     */
    private final JsonRequestManager.getMakeModeltRequest callback = new JsonRequestManager.getMakeModeltRequest() {
        @Override
        public void onSuccess(VehicleMakeModelResponse response) {
            if(progress!=null){
                progress.dismiss();
            }

            HashMap<String,ArrayList<String>> listHashMap = new HashMap<>();
            ArrayList<String> vehicleMakeList = new ArrayList<>();

            for(int i=0;i<response.getResults().getType().size();i++){
                if(!listHashMap.containsKey(response.getResults().getType().get(i).getMake())) {
                    vehicleMakeList.add(response.getResults().getType().get(i).getMake());
                    listHashMap.put(response.getResults().getType().get(i).getMake(),new ArrayList<String>());
                }

                listHashMap.get(response.getResults().getType().get(i).getMake()).add(response.getResults().getType().get(i).getModel());
            }

            AppController.setListHashMap(listHashMap);
            AppController.setVehicleMakeList(vehicleMakeList);

            status = true;


        }

        @Override
        public void onError(String response) {
            if(progress!=null){
                progress.dismiss();
            }
            alertDialog = notifications.showGeneralDialog(context,response);
            alertDialog.show();

            status = false;
        }
    };

    @Override
    public void onClick(View v) {
        if(status){
            if(v.getId()==R.id.idCar){
                Intent intent = new Intent(NewInsuranceVehicleListActivity.this,InsuranceTypesActivity.class);
                intent.putExtra("vtype","CAR");
                startActivity(intent);
            }else if(v.getId()==R.id.idVan){
                Intent intent = new Intent(NewInsuranceVehicleListActivity.this,InsuranceTypesActivity.class);
                intent.putExtra("vtype","VAN");
                startActivity(intent);
            }else if(v.getId()==R.id.idBus){
                Intent intent = new Intent(NewInsuranceVehicleListActivity.this,InsuranceTypesActivity.class);
                intent.putExtra("vtype","BUS");
                startActivity(intent);
            }else if(v.getId()==R.id.idLorry){
                Intent intent = new Intent(NewInsuranceVehicleListActivity.this,InsuranceTypesActivity.class);
                intent.putExtra("vtype","LORRY");
                startActivity(intent);
            }else if(v.getId()==R.id.idBike){
                Intent intent = new Intent(NewInsuranceVehicleListActivity.this,InsuranceTypesActivity.class);
                intent.putExtra("vtype","BIKE");
                startActivity(intent);
            }else if(v.getId()==R.id.idWheeler){
                Intent intent = new Intent(NewInsuranceVehicleListActivity.this,InsuranceTypesActivity.class);
                intent.putExtra("vtype","3_WHEEL");
                startActivity(intent);
            }else if(v.getId()==R.id.idJeep){
                Intent intent = new Intent(NewInsuranceVehicleListActivity.this,InsuranceTypesActivity.class);
                intent.putExtra("vtype","JEEP");
                startActivity(intent);
            }else if(v.getId()==R.id.idCab){
                Intent intent = new Intent(NewInsuranceVehicleListActivity.this,InsuranceTypesActivity.class);
                intent.putExtra("vtype","D_CAB");
                startActivity(intent);
            }
        }

    }
}
