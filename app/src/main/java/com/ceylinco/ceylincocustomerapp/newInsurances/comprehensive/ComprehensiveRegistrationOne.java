package com.ceylinco.ceylincocustomerapp.newInsurances.comprehensive;

import android.app.AlertDialog;
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

/**
 * Created by Prishan Maduka on 7/23/2016.
 */
public class ComprehensiveRegistrationOne extends AppCompatActivity implements View.OnClickListener {

    private Button btnNext;
    private EditText vehNo,name,contactNo,email,address,nic,chasisNo,engineNo;
    private TextView regType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comprehensive_registration_one);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText(getResources().getString(R.string.registration_title));
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);

        initialize();
    }

    private void initialize() {

        btnNext = (Button)findViewById(R.id.btnNext);
        ImageView imgSpinnerArrow = (ImageView) findViewById(R.id.spinnerArrow);

        vehNo = (EditText)findViewById(R.id.vehicleNoTextview);
        name = (EditText)findViewById(R.id.companyName);
        contactNo = (EditText)findViewById(R.id.contactNumber);
        email = (EditText)findViewById(R.id.email);
        address = (EditText)findViewById(R.id.address);
        nic = (EditText)findViewById(R.id.regNo);
        chasisNo = (EditText)findViewById(R.id.chassisNo);
        engineNo = (EditText)findViewById(R.id.engineNumber);
        regType = (TextView)findViewById(R.id.selectTextview);

        btnNext.setOnClickListener(this);
        imgSpinnerArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnNext){
            if(isValid()){
                NewInsuranceFormModel newInsuranceFormModel = new NewInsuranceFormModel();
                newInsuranceFormModel.setvType(getIntent().getStringExtra("vtype"));
                newInsuranceFormModel.setRegType(regType.getText().toString());
                newInsuranceFormModel.setvNo(vehNo.getText().toString());
                newInsuranceFormModel.setName(name.getText().toString());
                newInsuranceFormModel.setContactNo(contactNo.getText().toString());
                newInsuranceFormModel.setEmail(email.getText().toString());
                newInsuranceFormModel.setAddress(address.getText().toString());
                newInsuranceFormModel.setNic(nic.getText().toString());
                newInsuranceFormModel.setChasisNo(chasisNo.getText().toString());
                newInsuranceFormModel.setEngineNo(engineNo.getText().toString());

                Intent intent = new Intent(ComprehensiveRegistrationOne.this,ComprehensiveRegistrationTwo.class);
                intent.putExtra("DATA",newInsuranceFormModel);
                startActivity(intent);
            }
        }else if(v.getId()==R.id.spinnerArrow){
            AlertDialog.Builder builder = new AlertDialog.Builder(ComprehensiveRegistrationOne.this);
            builder.setTitle("Select Registration Type")
                    .setItems(R.array.reg_types, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            regType.setTextColor(AppController.getColor(ComprehensiveRegistrationOne.this));
                            if(which==0){
                                regType.setText("Registered");
                            }else{
                                regType.setText("Unregistered");
                            }
                            regType.setError(null);
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    private boolean isValid() {
        boolean isValidInput = true;

        if(!regType.getText().toString().equalsIgnoreCase("Registered") && !regType.getText().toString().equalsIgnoreCase("Unregistered")){
            regType.setError("Error");
            isValidInput = false;
        }

        if(vehNo.getText()==null || vehNo.getText().toString().isEmpty()){
            isValidInput = false;
            vehNo.setError(getString(R.string.required_veh_no));
        }

        if(name.getText()==null || name.getText().toString().isEmpty()){
            isValidInput = false;
            name.setError(getString(R.string.required_name));
        }

        if(contactNo.getText()==null || contactNo.getText().toString().isEmpty()){
            isValidInput = false;
            contactNo.setError(getString(R.string.required_contact));
        }

        if(email.getText()==null || email.getText().toString().isEmpty()){
            isValidInput = false;
            email.setError(getString(R.string.required_email));
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            isValidInput = false;
            email.setError(getString(R.string.invalid_email));
        }

        if(address.getText()==null || address.getText().toString().isEmpty()){
            isValidInput = false;
            address.setError(getString(R.string.required_address));
        }

        if(nic.getText()==null || nic.getText().toString().isEmpty()){
            isValidInput = false;
            nic.setError(getString(R.string.required_nic));
        }

        if(chasisNo.getText()==null || chasisNo.getText().toString().isEmpty()){
            isValidInput = false;
            chasisNo.setError(getString(R.string.required_chasis));
        }

        if(engineNo.getText()==null || engineNo.getText().toString().isEmpty()){
            isValidInput = false;
            engineNo.setError(getString(R.string.required_eno));
        }

        return isValidInput;
    }
}
