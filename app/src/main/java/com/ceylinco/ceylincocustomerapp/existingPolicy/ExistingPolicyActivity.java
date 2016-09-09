package com.ceylinco.ceylincocustomerapp.existingPolicy;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ceylinco.ceylincocustomerapp.MainActivity;
import com.ceylinco.ceylincocustomerapp.R;
import com.ceylinco.ceylincocustomerapp.models.FormSubmitResponse;
import com.ceylinco.ceylincocustomerapp.models.LoginResponse;
import com.ceylinco.ceylincocustomerapp.util.DetectNetwork;
import com.ceylinco.ceylincocustomerapp.util.JsonRequestManager;
import com.ceylinco.ceylincocustomerapp.util.Notifications;
import com.ceylinco.ceylincocustomerapp.util.Validator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Prishan Maduka on 7/24/2016.
 */
public class ExistingPolicyActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin,btnForgotPassword,btnRegister;
    private EditText userName,password;
    private Context context;
    private final Notifications notifications = new Notifications();
    private ProgressDialog progress;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.existing_policy_layout);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText(getResources().getString(R.string.log_in));
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);

        initialize();
    }

    private void initialize() {
        context = ExistingPolicyActivity.this;
        DetectNetwork.setmContext(context);

        userName = (EditText)findViewById(R.id.userName);
        password = (EditText)findViewById(R.id.password);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnForgotPassword = (Button)findViewById(R.id.forgootPassword);

        btnLogin.setOnClickListener(this);
        btnForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnLogin){
            if(DetectNetwork.isConnected()){
                if(isValid()){
                    progress = new ProgressDialog(context);
                    progress.setMessage("Authenticating user...");
                    progress.show();
                    progress.setCanceledOnTouchOutside(true);

                    JsonRequestManager.getInstance(this).userLogin(getResources().getString(R.string.base_url) + getResources().getString(R.string.login_url), userName.getText().toString(),password.getText().toString(), callback);
                }
            }else {
                alertDialog = notifications.showNetworkNotification(this);
                alertDialog.show();
            }
        }else if(v.getId()==R.id.forgootPassword){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(getString(R.string.forgot_password)+" - Password Reset");
            View viewInflated = LayoutInflater.from(context).inflate(R.layout.forgot_password_layout, null);
            final EditText input = (EditText) viewInflated.findViewById(R.id.input);
            builder.setView(viewInflated);

            // Set up the buttons
            builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Validator validator = new Validator();
                    if(input.getText()!=null && !input.getText().toString().isEmpty()){
                        if(validator.isValidNic(input.getText().toString())) {
                            progress = new ProgressDialog(context);
                            progress.setMessage("Sending message...");
                            progress.show();
                            progress.setCanceledOnTouchOutside(true);
                            JsonRequestManager.getInstance(context).resetPassword(getResources().getString(R.string.base_url) + getResources().getString(R.string.forgot_password_details_url), input.getText().toString(), resetCallback);
                        }else {
                            alertDialog = notifications.showGeneralDialog(context,"Invalid NIC");
                            alertDialog.show();
                        }
                    }else{
                        alertDialog = notifications.showGeneralDialog(context,"Empty NIC value");
                        alertDialog.show();
                    }

                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
    }

    /**
     * Callback to handle login request
     */
    private final JsonRequestManager.loginRequest callback = new JsonRequestManager.loginRequest() {
        @Override
        public void onSuccess(String response) {
            if(progress!=null){
                progress.dismiss();
            }

            String APPLICATION_TAG = "EXSISTING POLICY LOGIN";
            try {

                ObjectMapper mapper = new ObjectMapper();

                LoginResponse loginResponse = mapper.readValue(response, LoginResponse.class);

                if(loginResponse.getResults().getStatus().equalsIgnoreCase("0")){
                    alertDialog = notifications.showGeneralDialog(context,loginResponse.getResults().getError().getText());
                    alertDialog.show();
                }else{
                    //Shared Pref
                    SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.user_name), userName.getText().toString());
                    editor.putString(getString(R.string.user_password), password.getText().toString());
                    editor.commit();

                    Intent selectVehicleIntent = new Intent(ExistingPolicyActivity.this,SelectVehicleActivity.class);
                    startActivity(selectVehicleIntent);
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

    private boolean isValid() {
        boolean isValidInput = true;

        if(userName.getText()==null || userName.getText().toString().isEmpty()){
            isValidInput = false;
            userName.setError(getString(R.string.required_username));
        }

        if(password.getText()==null || password.getText().toString().isEmpty()){
            isValidInput = false;
            password.setError(getString(R.string.required_password));
        }

        return isValidInput;

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    /**
     * Callback to handle resetting password - send nic request
     */
    private final JsonRequestManager.resetPasswordRequest resetCallback = new JsonRequestManager.resetPasswordRequest() {
        @Override
        public void onSuccess(String response) {
            if(progress!=null){
                progress.dismiss();
            }

            String APPLICATION_TAG = "SENDING NIC";
            try {

                ObjectMapper mapper = new ObjectMapper();

                FormSubmitResponse formSubmitResponse = mapper.readValue(response, FormSubmitResponse.class);
                String message;

                if(formSubmitResponse.getResults().getStatus().equalsIgnoreCase("2")){
                    message = "You will receive your user name & password in sms";
                }else{
                    message = formSubmitResponse.getResults().getError().getText();
                }

                alertDialog = notifications.showGeneralDialog(context,message);
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

}
