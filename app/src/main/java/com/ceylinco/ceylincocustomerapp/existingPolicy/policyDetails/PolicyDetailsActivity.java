package com.ceylinco.ceylincocustomerapp.existingPolicy.policyDetails;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.ceylinco.ceylincocustomerapp.R;
import com.ceylinco.ceylincocustomerapp.models.PolicyData;
import com.ceylinco.ceylincocustomerapp.models.PolicyDetails;
import com.ceylinco.ceylincocustomerapp.util.DetectNetwork;
import com.ceylinco.ceylincocustomerapp.util.JsonRequestManager;
import com.ceylinco.ceylincocustomerapp.util.Notifications;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Prishan Maduka on 7/28/2016.
 */
public class PolicyDetailsActivity extends AppCompatActivity {

    private PolicyDetailsListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    private ProgressDialog progress;
    private AlertDialog alertDialog;
    private final Notifications notifications = new Notifications();
    private Context context;
    private String policyNumber,vehicleNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.policy_details_layout);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText(getResources().getString(R.string.policy_details));
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);

        initialize();
    }

    private void initialize() {
        context = PolicyDetailsActivity.this;
        DetectNetwork.setmContext(context);

        policyNumber = getIntent().getStringExtra("POLICY");
        vehicleNumber = getIntent().getStringExtra("VEHICLE");

        expListView = (ExpandableListView)findViewById(R.id.policyDetailsList);

        if(DetectNetwork.isConnected()){
            progress = new ProgressDialog(context);
            progress.setMessage("Loading Claim Details...");
            progress.show();
            progress.setCanceledOnTouchOutside(true);
            JsonRequestManager.getInstance(this).getPolicyDetails(getResources().getString(R.string.base_url) + getResources().getString(R.string.get_policy_details_url), policyNumber,vehicleNumber, callback);
        }else {
            alertDialog = notifications.showNetworkNotification(this);
            alertDialog.show();
        }
    }

    /**
     * Callback to handle policy details request
     */
    private final JsonRequestManager.getPolicyDetailsRequest callback = new JsonRequestManager.getPolicyDetailsRequest() {
        @Override
        public void onSuccess(String response) {
            if(progress!=null){
                progress.dismiss();
            }

            String APPLICATION_TAG = "CLAIMS";
            try {

                ObjectMapper mapper = new ObjectMapper();

                PolicyDetails policy = mapper.readValue(response, PolicyDetails.class);

                if(policy.getResults().getData()!=null){
                    prepareListData(policy.getResults().getData());
                    listAdapter = new PolicyDetailsListAdapter(context,listDataHeader,listDataChild);

                    // setting list adapter
                    expListView.setAdapter(listAdapter);
                }else{
                    alertDialog = notifications.showGeneralDialog(context,"No Data found");
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

    private void prepareListData(PolicyData policyData) {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        listDataHeader.add("Vehicle Number");
        listDataHeader.add("Name of Insured");
        listDataHeader.add("Policy Period");
        listDataHeader.add("Policy Status");
        listDataHeader.add("Branch");
        listDataHeader.add("Branch Contact");

        List<String> vehNo = new ArrayList<>();
        vehNo.add(vehicleNumber);

        List<String> insurer = new ArrayList<>();
        insurer.add(policyData.getNameofinsured());

        List<String> period = new ArrayList<>();
        period.add(policyData.getPeriod());

        List<String> status = new ArrayList<>();
        if(policyData.getStatus().equalsIgnoreCase("1"))
            status.add("Active");
        else
            status.add("Inactive");

        List<String> branch = new ArrayList<>();
        branch.add(policyData.getBranch());

        List<String> contact = new ArrayList<String>();
        contact.add(policyData.getBrtel());

        listDataChild.put(listDataHeader.get(0), vehNo);
        listDataChild.put(listDataHeader.get(1), insurer);
        listDataChild.put(listDataHeader.get(2), period);
        listDataChild.put(listDataHeader.get(3), status);
        listDataChild.put(listDataHeader.get(4), branch);
        listDataChild.put(listDataHeader.get(5), contact);
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
        }else{
            return super.onOptionsItemSelected(item);
        }
    }
}
