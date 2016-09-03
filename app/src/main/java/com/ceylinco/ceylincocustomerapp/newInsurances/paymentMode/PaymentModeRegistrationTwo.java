package com.ceylinco.ceylincocustomerapp.newInsurances.paymentMode;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceylinco.ceylincocustomerapp.R;
import com.ceylinco.ceylincocustomerapp.models.FormSubmitResponse;
import com.ceylinco.ceylincocustomerapp.models.NewInsuranceFormModel;
import com.ceylinco.ceylincocustomerapp.util.AppController;
import com.ceylinco.ceylincocustomerapp.util.DetectNetwork;
import com.ceylinco.ceylincocustomerapp.util.JsonRequestManager;
import com.ceylinco.ceylincocustomerapp.util.Notifications;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Prishan Maduka on 7/23/2016.
 */
public class PaymentModeRegistrationTwo extends AppCompatActivity implements View.OnClickListener {

    private static TextView yearOfMake,txtMake,txtModel;
    private EditText branch,location;
    private AlertDialog alertDialog;
    private Context context;
    private NewInsuranceFormModel formModel;
    private final Notifications notifications = new Notifications();
    private ProgressDialog progress;
    private int maxYear = 0;
    private int minYear = 1990;
    private ArrayList<String> yearArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_registration_two);

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

        formModel = getIntent().getParcelableExtra("DATA");

        initialize();
    }

    private void initialize() {
        context = PaymentModeRegistrationTwo.this;
        DetectNetwork.setmContext(context);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        ImageView imgYearOfMake = (ImageView) findViewById(R.id.imgYear);
        ImageView imgMake = (ImageView) findViewById(R.id.imgMake);
        ImageView imgModel = (ImageView) findViewById(R.id.imgModel);

        yearOfMake = (TextView)findViewById(R.id.yearTextview);
        txtMake = (TextView)findViewById(R.id.makeTextView);
        txtModel = (TextView)findViewById(R.id.modelTextview);

        branch = (EditText)findViewById(R.id.branch);
        location = (EditText)findViewById(R.id.location);

        final Calendar c = Calendar.getInstance();
        maxYear = c.get(Calendar.YEAR);
        for(int i=minYear;i<=maxYear;i++){
            yearArray.add(String.valueOf(i));
        }

        btnLogin.setOnClickListener(this);
        imgYearOfMake.setOnClickListener(this);
        imgMake.setOnClickListener(this);
        imgModel.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnLogin){

            if(DetectNetwork.isConnected()){
                if(isValid()){
                    formModel.setMakeYear(yearOfMake.getText().toString());
                    formModel.setBranch(branch.getText().toString());
                    formModel.setLocation(location.getText().toString());
                    formModel.setMake(txtMake.getText().toString());
                    formModel.setModel(txtModel.getText().toString());


                    progress = new ProgressDialog(context);
                    progress.setMessage("Creating Insurance Policy...");
                    progress.show();
                    progress.setCanceledOnTouchOutside(true);

                    JsonRequestManager.getInstance(this).thirdPartyInsurance(getResources().getString(R.string.base_url) + getResources().getString(R.string.third_party_submit_url), formModel, callback);
                }
            }else{
                alertDialog = notifications.showNetworkNotification(this);
                alertDialog.show();
            }

        }else if(v.getId()==R.id.imgYear || v.getId()==R.id.imgMake || v.getId()==R.id.imgModel){
            alertDialog = showCustomDialog(v.getId());
            alertDialog.show();
        }
    }

    /**
     * Callback to handle thirdparty form data submit request
     */
    private final JsonRequestManager.thirdPartyInsuranceRequest callback = new JsonRequestManager.thirdPartyInsuranceRequest() {
        @Override
        public void onSuccess(String response) {
            if(progress!=null){
                progress.dismiss();
            }

            String APPLICATION_TAG = "THIRD PARTY POLICY";
            try {

                ObjectMapper mapper = new ObjectMapper();

                FormSubmitResponse formSubmitResponse = mapper.readValue(response, FormSubmitResponse.class);

                if(formSubmitResponse.getResults().getStatus().equalsIgnoreCase("0")){
                    alertDialog = notifications.showGeneralDialog(context,formSubmitResponse.getResults().getError().getText());
                }else{
                    alertDialog = notifications.insurancePolicySuccessAlert(context,"New insurance policy added successfully.\nYour reference number is "+formSubmitResponse.getResults().getReference());
                }

                alertDialog.show();

            }catch (JsonParseException e) {
                e.printStackTrace();
                Log.d(APPLICATION_TAG,e.getMessage());
            } catch (JsonMappingException e) {
                e.printStackTrace();
                Log.d(APPLICATION_TAG,e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(APPLICATION_TAG,e.getMessage());
            }

        }

        @Override
        public void onError(String response) {
            if(progress!=null){
                progress.dismiss();
            }
            alertDialog = notifications.showGeneralDialog(context,response);
            alertDialog.show();
        }
    };

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
                            txtMake.setTextColor(AppController.getColor(context,com.ceylinco.ceylincocustomerapp.R.color.colorPrimaryDark));
                            txtMake.setText(vehicleMakeList[which]);
                            txtModel.setTextColor(AppController.getColor(context, R.color.textHintColor));
                            txtModel.setText("Model");
                            txtMake.setError(null);
                        }
                    });
        }else if(id==R.id.imgModel){
            if(!txtMake.getText().toString().equalsIgnoreCase("Make")){
                final CharSequence[] vehicleModelList = AppController.getListHashMap().get(txtMake.getText().toString()).toArray(new CharSequence[AppController.getListHashMap().get(txtMake.getText().toString()).size()]);
                builder.setTitle("Select Vehicle Model")
                        .setItems(vehicleModelList, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                txtModel.setTextColor(AppController.getColor(context,com.ceylinco.ceylincocustomerapp.R.color.colorPrimaryDark));
                                txtModel.setText(vehicleModelList[which]);
                                txtModel.setError(null);
                            }
                        });
            }

        }


        return builder.create();
    }

    private boolean isValid() {
        boolean isValidInput = true;

        if(yearOfMake.getText().toString().equalsIgnoreCase("Year of Make")){
            yearOfMake.setError("Error");
            isValidInput = false;
        }

        if(branch.getText()==null || branch.getText().toString().isEmpty()){
            isValidInput = false;
            branch.setError(getString(R.string.required_branch));
        }

        if(location.getText()==null || location.getText().toString().isEmpty()){
            isValidInput = false;
            location.setError(getString(R.string.required_location));
        }

        if(txtMake.getText().toString().equalsIgnoreCase("Make")){
            txtMake.setError("Error");
            isValidInput = false;
        }

        if(txtModel.getText().toString().equalsIgnoreCase("Model")){
            txtModel.setError("Error");
            isValidInput = false;
        }

        return isValidInput;

    }


}
