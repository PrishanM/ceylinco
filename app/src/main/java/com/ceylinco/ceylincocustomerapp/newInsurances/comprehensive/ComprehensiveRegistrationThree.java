package com.ceylinco.ceylincocustomerapp.newInsurances.comprehensive;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ceylinco.ceylincocustomerapp.R;
import com.ceylinco.ceylincocustomerapp.models.FormSubmitResponse;
import com.ceylinco.ceylincocustomerapp.models.NewInsuranceFormModel;
import com.ceylinco.ceylincocustomerapp.models.PerilType;
import com.ceylinco.ceylincocustomerapp.util.AppController;
import com.ceylinco.ceylincocustomerapp.util.DatePickerCustom;
import com.ceylinco.ceylincocustomerapp.util.DetectNetwork;
import com.ceylinco.ceylincocustomerapp.util.JsonRequestManager;
import com.ceylinco.ceylincocustomerapp.util.Notifications;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Prishan Maduka on 7/23/2016.
 */
public class ComprehensiveRegistrationThree extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgFirstReg;
    private static TextView firstRegistration;
    private EditText branch,location;
    private NewInsuranceFormModel formModel;
    private final Notifications notifications = new Notifications();
    private ProgressDialog progress;
    private AlertDialog alertDialog;
    private Context context;
    private PerilsAdapter perilsAdapter;
    private ListView listView;
    private List<PerilType> typeList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comprehensive_registration_three);


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

        Log.d("xxxxxxx",formModel.getVehValue()+" ---- "+formModel.getAddress());

        initialize();
    }

    private void initialize() {
        context = ComprehensiveRegistrationThree.this;
        DetectNetwork.setmContext(context);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        imgFirstReg = (ImageView)findViewById(R.id.imgFirstReg);
        branch = (EditText)findViewById(R.id.branch);
        location = (EditText)findViewById(R.id.location);
        firstRegistration = (TextView)findViewById(R.id.firstRegistrationTextview);
        listView = (ListView)findViewById(R.id.listPerils);

        btnLogin.setOnClickListener(this);
        imgFirstReg.setOnClickListener(this);

        progress = new ProgressDialog(context);
        progress.setMessage("Loading Data...");
        progress.show();
        progress.setCanceledOnTouchOutside(true);

        JsonRequestManager.getInstance(this).getPerils(getResources().getString(R.string.base_url) + getResources().getString(R.string.get_perils_url), formModel.getvType(),formModel.getPurpose(), perilsCallback);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnLogin){
            if(DetectNetwork.isConnected()){
                if(isValid()){
                    formModel.setBranch(branch.getText().toString());
                    formModel.setLocation(location.getText().toString());
                    formModel.setFirstRegDate((firstRegistration.getText().toString().equalsIgnoreCase("Date of First Registration"))?"":firstRegistration.getText().toString());

                    HashMap<Integer,Boolean> map = new HashMap<>();
                    map = AppController.getIntegerHashMap();
                    String peril = "";
                    if(map.size()>0){
                        for (int i=0;i<map.size();i++){
                            if (map.get(i)){
                                peril = peril + typeList.get(i).getCode() + "||";
                            }
                        }
                    }

                    if(peril.length()>0){
                        peril = peril.substring(0, peril.length() - 2);
                    }else peril=peril;

                    progress = new ProgressDialog(context);
                    progress.setMessage("Creating Insurance Policy...");
                    progress.show();
                    progress.setCanceledOnTouchOutside(true);
                    JsonRequestManager.getInstance(this).comprehensiveInsurance(getResources().getString(R.string.base_url) + getResources().getString(R.string.comprehensive_submit_url), formModel,peril, callback);
                }
            }else {
                alertDialog = notifications.showNetworkNotification(this);
                alertDialog.show();
            }

        }else if(v.getId()==R.id.imgFirstReg){
            showDatePicker();
        }
    }

    /**
     * Callback to handle comprehensive form data submit request
     */
    private final JsonRequestManager.comprehensiveInsuranceRequest callback = new JsonRequestManager.comprehensiveInsuranceRequest() {
        @Override
        public void onSuccess(String response) {
            if(progress!=null){
                progress.dismiss();
            }

            String APPLICATION_TAG = "COMPREHENSIVE POLICY";
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

    private final DatePickerDialog.OnDateSetListener regDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            firstRegistration.setText(dayOfMonth+"/"+monthOfYear+"/"+year);

        }
    };

    private void showDatePicker() {
        DatePickerCustom date = new DatePickerCustom();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("YEAR", calender.get(Calendar.YEAR));
        args.putInt("MONTH", calender.get(Calendar.MONTH));
        args.putInt("DAY", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(regDateListener);
        date.show(getSupportFragmentManager(), "Date Picker");
    }

    private boolean isValid() {
        boolean isValidInput = true;

        if(branch.getText()==null || branch.getText().toString().isEmpty()){
            isValidInput = false;
            branch.setError(getString(R.string.required_branch));
        }

        if(location.getText()==null || location.getText().toString().isEmpty()){
            isValidInput = false;
            location.setError(getString(R.string.required_location));
        }

        return isValidInput;

    }

    /**
     * Callback to handle policy perils
     */
    private final JsonRequestManager.getPerilsRequest perilsCallback = new JsonRequestManager.getPerilsRequest() {
        @Override
        public void onSuccess(String response) {
            if(progress!=null){
                progress.dismiss();
            }

            String APPLICATION_TAG = "POLICY PERILS";
            try {

                ObjectMapper mapper = new ObjectMapper();

                FormSubmitResponse perilResponse = mapper.readValue(response, FormSubmitResponse.class);
                if(perilResponse.getResults().getStatus().equalsIgnoreCase("5") && perilResponse.getResults().getType()!=null && perilResponse.getResults().getType().size()>0){
                    typeList = perilResponse.getResults().getType();
                    perilsAdapter = new PerilsAdapter(context,typeList);
                    listView.setAdapter(perilsAdapter);
                }
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
}
