package com.ceylinco.ceylincocustomerapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Prishan Maduka on 8/5/2016.
 */
public class CallUsFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private LinearLayout hotLine,headOffice,vipCenter;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_call_us, container, false);

        initialize();

        return rootView;
    }

    private void initialize() {
        hotLine = (LinearLayout)rootView.findViewById(R.id.idHotLine);
        headOffice = (LinearLayout)rootView.findViewById(R.id.idHeadOffice);
        vipCenter = (LinearLayout)rootView.findViewById(R.id.idVIP);

        hotLine.setOnClickListener(this);
        headOffice.setOnClickListener(this);
        vipCenter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.idHotLine){
            callUs("011256895");
        }else if(v.getId()==R.id.idHeadOffice){
            callUs("011256895");
        }else if(v.getId()==R.id.idVIP){
            callUs("0112897653");
        }

    }

    private void callUs(String number){

    }



}
