package com.ceylinco.ceylincocustomerapp.inquiries.locationServices;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ceylinco.ceylincocustomerapp.R;

/**
 * Created by Prishan Maduka on 8/5/2016.
 */
public class LocationServices extends Fragment implements View.OnClickListener {

    View rootView;
    LinearLayout policeStations,banks,hospitals;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.inquiry_layout, container, false);

        policeStations = (LinearLayout)rootView.findViewById(R.id.idPoliceStations);
        banks = (LinearLayout)rootView.findViewById(R.id.idBanks);
        hospitals = (LinearLayout)rootView.findViewById(R.id.idHospitals);

        policeStations.setOnClickListener(this);
        banks.setOnClickListener(this);
        hospitals.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(),ValueAddedServicesActivity.class);
        if(v.getId()==R.id.idPoliceStations){
            intent.putExtra("TYPE","police");
        }else if(v.getId()==R.id.idBanks){
            intent.putExtra("TYPE","bank");
        }else if(v.getId()==R.id.idHospitals){
            intent.putExtra("TYPE","hospital");
        }
        startActivity(intent);
    }
}
