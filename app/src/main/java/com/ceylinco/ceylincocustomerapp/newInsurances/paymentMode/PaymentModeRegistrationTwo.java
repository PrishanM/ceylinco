package com.ceylinco.ceylincocustomerapp.newInsurances.paymentMode;

import android.app.AlertDialog;
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
import com.ceylinco.ceylincocustomerapp.util.JsonRequestManager;
import com.ceylinco.ceylincocustomerapp.util.Notifications;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Prishan Maduka on 7/23/2016.
 */
public class PaymentModeRegistrationTwo extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private ImageView imgYearOfMake,imgMake,imgModel;
    private static TextView yearOfMake,txtMake,txtModel;
    private EditText branch,location;
    private AlertDialog alertDialog;
    private Context context;
    private NewInsuranceFormModel formModel;
    private Notifications notifications = new Notifications();

    private final String APPLICATION_TAG = "THIRD PARTY POLICY";

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
        Log.d("xxxxx",formModel.getAddress());

        initialize();
    }

    private void initialize() {
        context = PaymentModeRegistrationTwo.this;
        btnLogin = (Button)findViewById(R.id.btnLogin);

        imgYearOfMake = (ImageView)findViewById(R.id.imgYear);
        imgMake = (ImageView)findViewById(R.id.imgMake);
        imgModel = (ImageView)findViewById(R.id.imgModel);

        yearOfMake = (TextView)findViewById(R.id.yearTextview);
        txtMake = (TextView)findViewById(R.id.makeTextView);
        txtModel = (TextView)findViewById(R.id.modelTextview);

        branch = (EditText)findViewById(R.id.branch);
        location = (EditText)findViewById(R.id.location);

        btnLogin.setOnClickListener(this);
        imgYearOfMake.setOnClickListener(this);
        imgMake.setOnClickListener(this);
        imgModel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnLogin){
            if(isValid()){
                formModel.setMakeYear(yearOfMake.getText().toString());
                formModel.setBranch(branch.getText().toString());
                formModel.setLocation(location.getText().toString());
                formModel.setMake(txtMake.getText().toString());
                formModel.setModel(txtModel.getText().toString());

                JsonRequestManager.getInstance(this).thirdPartyInsurance(getResources().getString(R.string.base_url) + getResources().getString(R.string.third_party_submit_url), formModel, callback);
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


            try {

                ObjectMapper mapper = new ObjectMapper();

                FormSubmitResponse formSubmitResponse = mapper.readValue(response, FormSubmitResponse.class);

                if(formSubmitResponse.getResults().getStatus().equalsIgnoreCase("0")){
                    alertDialog = notifications.showGeneralDialog(context,formSubmitResponse.getResults().getError().getText());
                }else{
                    alertDialog = notifications.showGeneralDialog(context,"New insurance policy added successfully.\nYour reference number is "+formSubmitResponse.getResults().getReference());
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
            alertDialog = notifications.showGeneralDialog(context,response);
            alertDialog.show();
        }
    };

    private AlertDialog showCustomDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if(id == R.id.imgYear){
            builder.setTitle("Select Year of Make")
                    .setItems(R.array.make_year_array, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            yearOfMake.setTextColor(AppController.getColor(context,R.color.colorPrimaryDark));
                            String[] yearArray = context.getResources().getStringArray(R.array.make_year_array);
                            yearOfMake.setText(yearArray[which]);
                            yearOfMake.setError(null);
                        }
                    });
        }else if(id==R.id.imgMake){
            builder.setTitle("Select Vehicle Make")
                    .setItems(R.array.make_array, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            txtMake.setTextColor(AppController.getColor(context,R.color.colorPrimaryDark));
                            String[] makeArray = context.getResources().getStringArray(R.array.make_array);
                            txtMake.setText(makeArray[which]);
                            txtMake.setError(null);
                        }
                    });
        }else if(id==R.id.imgModel){
            builder.setTitle("Select Vehicle Model")
                    .setItems(R.array.model_array, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            txtModel.setTextColor(AppController.getColor(context,R.color.colorPrimaryDark));
                            String[] modelArray = context.getResources().getStringArray(R.array.model_array);
                            txtModel.setText(modelArray[which]);
                            txtModel.setError(null);
                        }
                    });
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
