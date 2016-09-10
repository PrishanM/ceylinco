package com.ceylinco.ceylincocustomerapp.existingPolicy;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ceylinco.ceylincocustomerapp.R;
import com.ceylinco.ceylincocustomerapp.models.LoginResponse;
import com.ceylinco.ceylincocustomerapp.util.DetectNetwork;
import com.ceylinco.ceylincocustomerapp.util.JsonRequestManager;
import com.ceylinco.ceylincocustomerapp.util.Notifications;
import com.ceylinco.ceylincocustomerapp.util.Validator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Prishan Maduka on 9/10/2016.
 */
public class UserRegistrationActivity extends AppCompatActivity {

    private Button btnSubmit;
    private EditText name,contactNo,email,address,nic,username,password,conPassword,policyNo;
    private Context context;
    private final Notifications notifications = new Notifications();
    private ProgressDialog progress;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText(getResources().getString(R.string.user_reg_title));
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);
        
        initialize();
    }

    private void initialize() {
        context = UserRegistrationActivity.this;
        DetectNetwork.setmContext(context);

        name = (EditText)findViewById(R.id.idName);
        contactNo = (EditText)findViewById(R.id.contactNumber);
        email = (EditText)findViewById(R.id.email);
        address = (EditText)findViewById(R.id.address);
        nic = (EditText)findViewById(R.id.idNic);
        password = (EditText)findViewById(R.id.password);
        username = (EditText)findViewById(R.id.userName);
        conPassword = (EditText)findViewById(R.id.conpassword);
        policyNo = (EditText)findViewById(R.id.policyNo);

        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DetectNetwork.isConnected()){
                    if(isValid()){
                        ArrayList<String> params = new ArrayList<>();
                        params.add(name.getText().toString());
                        params.add(address.getText().toString());
                        params.add(nic.getText().toString());
                        params.add(contactNo.getText().toString());
                        params.add(email.getText().toString());
                        params.add(username.getText().toString());
                        params.add(password.getText().toString());
                        params.add(policyNo.getText().toString());

                        progress = new ProgressDialog(context);
                        progress.setMessage("Creating user...");
                        progress.show();
                        progress.setCanceledOnTouchOutside(true);

                        JsonRequestManager.getInstance(context).createUser(getResources().getString(R.string.base_url) + getResources().getString(R.string.create_user_url), params, callback);

                    }
                }else {
                    alertDialog = notifications.showNetworkNotification(context);
                    alertDialog.show();
                }
            }
        });

    }

    private boolean isValid() {
        boolean isValidInput = true;
        Validator validator = new Validator();

        if(name.getText()==null || name.getText().toString().isEmpty()){
            isValidInput = false;
            name.setError(getString(R.string.required_name));
        }

        if(address.getText()==null || address.getText().toString().isEmpty()){
            isValidInput = false;
            address.setError(getString(R.string.required_address));
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

        if(nic.getText()==null || nic.getText().toString().isEmpty()){
            isValidInput = false;
            nic.setError(getString(R.string.required_nic));
        }else if(!validator.isValidNic(nic.getText().toString())){
            isValidInput = false;
            nic.setError("Invalid NIC");
        }

        if(username.getText()==null || username.getText().toString().isEmpty()){
            isValidInput = false;
            username.setError(getString(R.string.required_username));
        }

        if(password.getText()==null || password.getText().toString().isEmpty()){
            isValidInput = false;
            password.setError(getString(R.string.required_password));
        }

        if(conPassword.getText()==null || conPassword.getText().toString().isEmpty() || !conPassword.getText().toString().equals(password.getText().toString())){
            isValidInput = false;
            conPassword.setError(getString(R.string.required_password));
        }

        if(policyNo.getText()==null || policyNo.getText().toString().isEmpty()){
            isValidInput = false;
            policyNo.setError(getString(R.string.required_policy_no));
        }

        return isValidInput;
    }

    /**
     * Callback to handle create user request
     */
    private final JsonRequestManager.createUserRequest callback = new JsonRequestManager.createUserRequest() {
        @Override
        public void onSuccess(String response) {
            if(progress!=null){
                progress.dismiss();
            }

            String APPLICATION_TAG = "CREATING USER";
            try {

                ObjectMapper mapper = new ObjectMapper();

                LoginResponse loginResponse = mapper.readValue(response, LoginResponse.class);

                if(loginResponse.getResults().getStatus().equalsIgnoreCase("0")){
                    alertDialog = notifications.showGeneralDialog(context,loginResponse.getResults().getError().getText());
                    alertDialog.show();
                }else{
                    alertDialog = notifications.createSuccess(context);
                    alertDialog.show();
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
