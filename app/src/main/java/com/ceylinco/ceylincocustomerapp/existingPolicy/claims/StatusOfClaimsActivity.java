package com.ceylinco.ceylincocustomerapp.existingPolicy.claims;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ceylinco.ceylincocustomerapp.R;
import com.ceylinco.ceylincocustomerapp.models.Claim;
import com.ceylinco.ceylincocustomerapp.models.FormSubmitResponse;
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
public class StatusOfClaimsActivity extends AppCompatActivity {

    private ClaimsAdapter claimsAdapter;
    private ListView claimsList;
    private TextView headerText;

    private ProgressDialog progress;
    private AlertDialog alertDialog;
    private final Notifications notifications = new Notifications();
    private Context context;
    private String policyNumber,vehicleNumber;
    private int clickedPosition = 0;
    private List<Claim> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.claims_status_layout);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText(getResources().getString(R.string.claims_status_title));
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);

        initialize();
    }

    private void initialize() {
        context = StatusOfClaimsActivity.this;
        DetectNetwork.setmContext(context);

        policyNumber = getIntent().getStringExtra("POLICY");
        vehicleNumber = getIntent().getStringExtra("VEHICLE");

        headerText = (TextView)findViewById(R.id.selectTextview);
        claimsList = (ListView)findViewById(R.id.claims_list_view);

        headerText.setText("List of Accident Details | "+policyNumber+" | "+vehicleNumber);

        if(DetectNetwork.isConnected()){
            progress = new ProgressDialog(context);
            progress.setMessage("Loading Claim Details...");
            progress.show();
            progress.setCanceledOnTouchOutside(true);
            JsonRequestManager.getInstance(this).getClaims(getResources().getString(R.string.base_url) + getResources().getString(R.string.get_claims_url), policyNumber,vehicleNumber, callback);
        }else {
            alertDialog = notifications.showNetworkNotification(this);
            alertDialog.show();
        }

        claimsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                clickedPosition = position;
                progress = new ProgressDialog(context);
                progress.setMessage("Loading Claim Details...");
                progress.show();
                progress.setCanceledOnTouchOutside(true);

                JsonRequestManager.getInstance(context).getSingleClaim(getResources().getString(R.string.base_url) + getResources().getString(R.string.get_single_claim_url), list.get(position).getRef(), singleClaimCallback);

            }
        });

    }

    /**
     * Callback to handle claims request
     */
    private final JsonRequestManager.getClaimsRequest callback = new JsonRequestManager.getClaimsRequest() {
        @Override
        public void onSuccess(String response) {
            if(progress!=null){
                progress.dismiss();
            }

            String APPLICATION_TAG = "CLAIMS";
            try {

                ObjectMapper mapper = new ObjectMapper();

                FormSubmitResponse claims = mapper.readValue(response, FormSubmitResponse.class);

                if(claims.getResults().getStatus().equalsIgnoreCase("0")){
                    alertDialog = notifications.showGeneralDialog(context,claims.getResults().getError().getText());
                    alertDialog.show();
                }else if(claims.getResults().getStatus().equalsIgnoreCase("1")){
                    if(claims.getResults().getClaim().size()>0){
                        claimsAdapter = new ClaimsAdapter(context,claims.getResults().getClaim());
                        claimsList.setAdapter(claimsAdapter);
                        list = claims.getResults().getClaim();
                    }else {
                        alertDialog = notifications.showGeneralDialog(context,"No data found");
                        alertDialog.show();
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

    /**
     * Callback to handle single claim request
     */
    private final JsonRequestManager.getSingleClaimRequest singleClaimCallback = new JsonRequestManager.getSingleClaimRequest() {
        @Override
        public void onSuccess(String response) {
            if(progress!=null){
                progress.dismiss();
            }

            String APPLICATION_TAG = "CLAIMS";
            try {

                ObjectMapper mapper = new ObjectMapper();

                FormSubmitResponse claim = mapper.readValue(response, FormSubmitResponse.class);

                if(claim.getResults().getStatus().equalsIgnoreCase("3") && claim.getResults().getData()!=null){
                    final Dialog dialog = new Dialog(StatusOfClaimsActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.custom_dialog);

                    TextView claimDate = (TextView)dialog.findViewById(R.id.txtClaimDate);
                    TextView approvedState = (TextView)dialog.findViewById(R.id.txtApproved);
                    TextView remark = (TextView)dialog.findViewById(R.id.txtRemark);
                    TextView contactTel = (TextView)dialog.findViewById(R.id.txtTel);
                    ImageView imgStatus = (ImageView)dialog.findViewById(R.id.imgApprovedStatus);
                    Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);

                    claimDate.setText(list.get(clickedPosition).getDate());
                    approvedState.setText(claim.getResults().getData().getRemark().getHighlight()+" | "+claim.getResults().getData().getAmount());
                    remark.setText(claim.getResults().getData().getRemark().getText());
                    contactTel.setText("For more details contact the branch - "+claim.getResults().getData().getBrtel());
                    if(claim.getResults().getData().getStatus().equalsIgnoreCase("APPROVED")){
                        imgStatus.setImageResource(R.drawable.ic_right_mark);
                    }else {
                        imgStatus.setImageResource(R.drawable.ic_wrong_mark);
                    }
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }else if(claim.getResults().getError().getText()!=null){
                    alertDialog = notifications.showGeneralDialog(context,claim.getResults().getError().getText());
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
