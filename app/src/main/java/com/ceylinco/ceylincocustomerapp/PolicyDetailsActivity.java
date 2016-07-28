package com.ceylinco.ceylincocustomerapp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Prishan Maduka on 7/28/2016.
 */
public class PolicyDetailsActivity extends AppCompatActivity {

    PolicyDetailsListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

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
        expListView = (ExpandableListView)findViewById(R.id.policyDetailsList);

        prepareListData();

        listAdapter = new PolicyDetailsListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add("Vehicle Number");
        listDataHeader.add("Name of Insured");
        listDataHeader.add("Policy Period");
        listDataHeader.add("Policy Status");
        listDataHeader.add("Branch");
        listDataHeader.add("Branch Contact");

        List<String> vehNo = new ArrayList<String>();
        vehNo.add("WP CAC 0001");

        List<String> insurer = new ArrayList<String>();
        insurer.add("Chandima Dasawatte");

        List<String> period = new ArrayList<String>();
        period.add("June 2017");

        List<String> status = new ArrayList<String>();
        status.add("Active");

        List<String> branch = new ArrayList<String>();
        branch.add("Kollupitiya");

        List<String> contact = new ArrayList<String>();
        contact.add("0112 808080");

        listDataChild.put(listDataHeader.get(0), vehNo);
        listDataChild.put(listDataHeader.get(1), insurer);
        listDataChild.put(listDataHeader.get(2), period);
        listDataChild.put(listDataHeader.get(3), status);
        listDataChild.put(listDataHeader.get(4), branch);
        listDataChild.put(listDataHeader.get(5), contact);
    }
}
