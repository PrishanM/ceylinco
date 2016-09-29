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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceylinco.ceylincocustomerapp.MainActivity;
import com.ceylinco.ceylincocustomerapp.R;
import com.ceylinco.ceylincocustomerapp.models.FormSubmitResponse;
import com.ceylinco.ceylincocustomerapp.models.Policy;
import com.ceylinco.ceylincocustomerapp.util.AppController;
import com.ceylinco.ceylincocustomerapp.util.DetectNetwork;
import com.ceylinco.ceylincocustomerapp.util.JsonRequestManager;
import com.ceylinco.ceylincocustomerapp.util.Notifications;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prishan Maduka on 7/24/2016.
 */
public class SelectVehicleActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnNext;
    private ImageView imageView;
    private final Notifications notifications = new Notifications();
    private ProgressDialog progress;
    private AlertDialog alertDialog;
    private Context context;
    private TextView policyNumber,vehicleNumber;
    private ArrayList<String> policyList = new ArrayList<>();
    private List<Policy> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_vehicle_layout);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText(getResources().getString(R.string.select_vehicle_title));
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);

        initialize();
    }

    private void initialize() {
        context = SelectVehicleActivity.this;
        DetectNetwork.setmContext(context);

        policyNumber = (TextView)findViewById(R.id.policyNumber);
        vehicleNumber = (TextView)findViewById(R.id.vehicleNumber);
        imageView = (ImageView)findViewById(R.id.imgPolicyNo);

        btnNext = (Button)findViewById(R.id.btnNext);

        btnNext.setOnClickListener(this);
        imageView.setOnClickListener(this);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
        String userName = sharedPref.getString(getString(R.string.user_name), "");

        if(DetectNetwork.isConnected()){
            if(userName!=null && !userName.isEmpty()){
                progress = new ProgressDialog(context);
                progress.setMessage("Loading Policies...");
                progress.show();
                progress.setCanceledOnTouchOutside(true);

                JsonRequestManager.getInstance(this).getPolicies(getResources().getString(R.string.base_url) + getResources().getString(R.string.get_user_policies_url), userName, callback);
            }
        }else{
            alertDialog = notifications.showNetworkNotification(this);
            alertDialog.show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnNext){
            if(isValid()){
                Intent intent = new Intent(SelectVehicleActivity.this,SelectedVehicleActivity.class);
                intent.putExtra("VEHICLE",vehicleNumber.getText().toString());
                intent.putExtra("POLICY",policyNumber.getText().toString());
                startActivity(intent);
            }
        }else if(v.getId()==R.id.imgPolicyNo){
            alertDialog = showCustomDialog(R.id.imgPolicyNo);
            alertDialog.show();
        }
    }

    /**
     * Callback to handle user policies request
     */
    private final JsonRequestManager.getPoliciesRequest callback = new JsonRequestManager.getPoliciesRequest() {
        @Override
        public void onSuccess(String response) {
            if(progress!=null){
                progress.dismiss();
            }

            String APPLICATION_TAG = "SELECT POLICY";
            try {

                ObjectMapper mapper = new ObjectMapper();

                FormSubmitResponse policies = mapper.readValue(response, FormSubmitResponse.class);

                if(policies.getResults().getStatus().equalsIgnoreCase("0")){
                    alertDialog = notifications.showGeneralDialog(context,policies.getResults().getError().getText());
                    alertDialog.show();
                }else if(policies.getResults().getStatus().equalsIgnoreCase("1")){
                    list = policies.getResults().getPolicy();
                    for (int i=0;i<list.size();i++){
                        policyList.add(list.get(i).getText());
                    }
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

        if(policyNumber.getText().toString().equalsIgnoreCase("Policy Number")){
            policyNumber.setError("Error");
            isValidInput = false;
        }

        if(vehicleNumber.getText().toString().equalsIgnoreCase("Vehicle Number")){
            vehicleNumber.setError("Error");
            isValidInput = false;
        }

        return isValidInput;

    }

    private AlertDialog showCustomDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (id == R.id.imgPolicyNo) {
            final CharSequence[] policyArrayList = policyList.toArray(new CharSequence[policyList.size()]);
            builder.setTitle("Select Policy")
                    .setItems(policyArrayList, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            vehicleNumber.setTextColor(AppController.getColor(context, com.ceylinco.ceylincocustomerapp.R.color.colorPrimaryDark));
                            policyNumber.setTextColor(AppController.getColor(context, com.ceylinco.ceylincocustomerapp.R.color.colorPrimaryDark));
                            policyNumber.setText(policyList.get(which));
                            vehicleNumber.setText(list.get(which).getVehicle());
                        }
                    });
        }

        return builder.create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_logout){
            alertDialog = notifications.logoutDialog(context);
            alertDialog.show();
            return true;
        }else if(item.getItemId()==android.R.id.home){
            backKeyPressed();
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        backKeyPressed();
    }

    private void backKeyPressed(){
        Intent mainActivity = new Intent(context, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }
}
