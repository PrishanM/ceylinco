package com.ceylinco.ceylincocustomerapp.inquiries;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceylinco.ceylincocustomerapp.inquiries.locationServices.LocationServices;
import com.ceylinco.ceylincocustomerapp.R;

/**
 * Created by Prishan Maduka on 7/24/2016.
 */
public class InquiryActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout layoutCall;
    private LinearLayout layoutBranches;
    private LinearLayout layoutLocations;
    private LinearLayout layoutLogo;
    private TextView textviewTitle;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inquiry_base_layout);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);

        initialize();
    }

    private void initialize() {
        layoutCall = (LinearLayout)findViewById(R.id.layoutCallUs);
        layoutBranches = (LinearLayout)findViewById(R.id.layoutBranches);
        layoutLocations = (LinearLayout)findViewById(R.id.layoutLocationServices);
        layoutLogo = (LinearLayout)findViewById(R.id.ceylincoLogo);
        frameLayout = (FrameLayout)findViewById(R.id.inquiriesFrame);
        textviewTitle.setText(getResources().getString(R.string.location_services));

        layoutCall.setOnClickListener(this);
        layoutBranches.setOnClickListener(this);
        layoutLocations.setOnClickListener(this);

        setFragment(new LocationServices(),getResources().getString(R.string.location_services));
        layoutLocations.setBackgroundResource(R.drawable.tab_selected);
    }

    protected void setFragment(Fragment fragment, String title) {
        Bundle args = new Bundle();
        textviewTitle.setText(title);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.inquiriesFrame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.layoutCallUs){
            layoutCall.setBackgroundResource(R.drawable.tab_selected);
            layoutBranches.setBackgroundResource(android.R.color.transparent);
            layoutLocations.setBackgroundResource(android.R.color.transparent);
            layoutLogo.setVisibility(View.VISIBLE);
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,0,6f));
            setFragment(new CallUsFragment(),getResources().getString(R.string.call_us));
        }else if(v.getId()==R.id.layoutBranches){
            layoutCall.setBackgroundResource(android.R.color.transparent);
            layoutBranches.setBackgroundResource(R.drawable.tab_selected);
            layoutLocations.setBackgroundResource(android.R.color.transparent);
            layoutLogo.setVisibility(View.GONE);
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,0,9f));
            setFragment(new BranchLocator(),getResources().getString(R.string.branch_locator));
        }else if(v.getId()==R.id.layoutLocationServices){
            layoutCall.setBackgroundResource(android.R.color.transparent);
            layoutBranches.setBackgroundResource(android.R.color.transparent);
            layoutLocations.setBackgroundResource(R.drawable.tab_selected);
            layoutLogo.setVisibility(View.VISIBLE);
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,0,6f));
            setFragment(new LocationServices(),getResources().getString(R.string.location_services));
        }
    }
}
