package com.ceylinco.ceylincocustomerapp.newInsurances.comprehensive;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceylinco.ceylincocustomerapp.R;
import com.ceylinco.ceylincocustomerapp.models.NewInsuranceFormModel;
import com.ceylinco.ceylincocustomerapp.util.AppController;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Prishan Maduka on 7/23/2016.
 */
public class ComprehensiveRegistrationTwo extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgYearOfMake,imgMake,imgModel;
    private TextView yearOfMake,vehicleMake,vehicleModel;
    private EditText valOfVehicle,leasingCompany,presentMeterReading;
    private String vehicleCondition = "Brand New";
    private String purposeOfUse = "Private";
    private NewInsuranceFormModel formModel;
    private AlertDialog alertDialog;
    private Context context;
    private int maxYear = 0;
    private ArrayList<String> yearArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comprehensive_registration_two);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText(getResources().getString(R.string.registration_title));
        assert abar != null;
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);

        formModel = getIntent().getParcelableExtra("DATA");

        initialize();
    }

    private void initialize() {
        context = ComprehensiveRegistrationTwo.this;

        Button btnNext = (Button) findViewById(R.id.btnNext);
        imgYearOfMake = (ImageView)findViewById(R.id.imgYear);
        imgMake = (ImageView)findViewById(R.id.imgMake);
        imgModel = (ImageView)findViewById(R.id.imgModel);

        vehicleMake = (TextView)findViewById(R.id.makeTextView);
        vehicleModel = (TextView)findViewById(R.id.modelTextview);
        yearOfMake = (TextView)findViewById(R.id.yearTextview);

        valOfVehicle = (EditText)findViewById(R.id.valOfVehicle);
        leasingCompany = (EditText)findViewById(R.id.txtLeasingCompany);
        presentMeterReading = (EditText)findViewById(R.id.meterReading);

        final Calendar c = Calendar.getInstance();
        maxYear = c.get(Calendar.YEAR);
        int minYear = 1990;
        for(int i = minYear; i<=maxYear; i++){
            yearArray.add(String.valueOf(i));
        }

        btnNext.setOnClickListener(this);
        imgYearOfMake.setOnClickListener(this);
        imgMake.setOnClickListener(this);
        imgModel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnNext){

            if(isValid()){
                formModel.setVehValue(valOfVehicle.getText().toString());
                formModel.setMake(vehicleMake.getText().toString());
                formModel.setModel(vehicleModel.getText().toString());
                formModel.setMakeYear(yearOfMake.getText().toString());
                formModel.setPurpose(purposeOfUse);
                formModel.setVehCondition(vehicleCondition);
                formModel.setLeasingCompany((leasingCompany.getText() == null) || (leasingCompany.getText().toString().isEmpty())?"":leasingCompany.getText().toString());
                formModel.setCurrMeter((presentMeterReading.getText() == null) || (presentMeterReading.getText().toString().isEmpty())?"":presentMeterReading.getText().toString());

                Intent intent = new Intent(ComprehensiveRegistrationTwo.this,ComprehensiveRegistrationThree.class);
                intent.putExtra("DATA",formModel);
                startActivity(intent);
            }

        }else if(v.getId()==R.id.imgYear || v.getId()==R.id.imgMake || v.getId()==R.id.imgModel){
            alertDialog = showCustomDialog(v.getId());
            alertDialog.show();
        }
    }

    private AlertDialog showCustomDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if(id == R.id.imgYear){
            final CharSequence[] yearArrayList = yearArray.toArray(new CharSequence[yearArray.size()]);
            builder.setTitle("Select Year of Make")
                    .setItems(yearArrayList, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            yearOfMake.setTextColor(AppController.getColor(context,com.ceylinco.ceylincocustomerapp.R.color.colorPrimaryDark));
                            yearOfMake.setText(yearArray.get(which));
                            yearOfMake.setError(null);
                        }
                    });
        }else if(id==R.id.imgMake){
            final CharSequence[] vehicleMakeList = AppController.getVehicleMakeList().toArray(new CharSequence[AppController.getVehicleMakeList().size()]);
            builder.setTitle("Select Vehicle Make")
                    .setItems(vehicleMakeList, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            vehicleMake.setTextColor(AppController.getColor(context,com.ceylinco.ceylincocustomerapp.R.color.colorPrimaryDark));
                            vehicleMake.setText(vehicleMakeList[which]);
                            vehicleModel.setTextColor(AppController.getColor(context, R.color.textHintColor));
                            vehicleModel.setText("Model");
                            vehicleMake.setError(null);
                        }
                    });
        }else if(id==R.id.imgModel){
            if(!vehicleMake.getText().toString().equalsIgnoreCase("Make")){
                final CharSequence[] vehicleModelList = AppController.getListHashMap().get(vehicleMake.getText().toString()).toArray(new CharSequence[AppController.getListHashMap().get(vehicleMake.getText().toString()).size()]);
                builder.setTitle("Select Vehicle Model")
                        .setItems(vehicleModelList, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                vehicleModel.setTextColor(AppController.getColor(context,com.ceylinco.ceylincocustomerapp.R.color.colorPrimaryDark));
                                vehicleModel.setText(vehicleModelList[which]);
                                vehicleModel.setError(null);
                            }
                        });
            }

        }


        return builder.create();
    }

    public void onRadioButtonClicked(View view) {
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioBrandNew:
                vehicleCondition = "Brand New";
                break;
            case R.id.radioReCondition:
                vehicleCondition = "Re Condition";
                break;
        }
    }

    public void onPurposeOfUseClicked(View view) {
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioPrivate:
                purposeOfUse = "Private";
                break;
            case R.id.radioHiring:
                purposeOfUse = "Hiring";
                break;
            case R.id.radioRent:
                purposeOfUse = "Rent";
                break;
        }
    }

    private boolean isValid() {
        boolean isValidInput = true;

        if(yearOfMake.getText().toString().equalsIgnoreCase("Year of Make")){
            yearOfMake.setError("Error");
            isValidInput = false;
        }

        if(valOfVehicle.getText()==null || valOfVehicle.getText().toString().isEmpty()){
            isValidInput = false;
            valOfVehicle.setError(getString(R.string.required_val_of_vehicle));
        }

        if(vehicleMake.getText().toString().equalsIgnoreCase("Make")){
            vehicleMake.setError("Error");
            isValidInput = false;
        }

        if(vehicleModel.getText().toString().equalsIgnoreCase("Model")){
            vehicleModel.setError("Error");
            isValidInput = false;
        }

        return isValidInput;

    }
}
