package com.ceylinco.ceylincocustomerapp.inquiries;

import android.content.Intent;
import android.net.Uri;
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
public class CallUsFragment extends Fragment implements View.OnClickListener {

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_call_us, container, false);

        initialize();

        return rootView;
    }

    private void initialize() {
        LinearLayout hotLine = (LinearLayout) rootView.findViewById(R.id.idHotLine);
        LinearLayout headOffice = (LinearLayout) rootView.findViewById(R.id.idHeadOffice);
        LinearLayout vipCenter = (LinearLayout) rootView.findViewById(R.id.idVIP);

        hotLine.setOnClickListener(this);
        headOffice.setOnClickListener(this);
        vipCenter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.idHotLine){
            callUs("tel:+94112393939");
        }else if(v.getId()==R.id.idHeadOffice){
            callUs("tel:+94115702702");
        }else if(v.getId()==R.id.idVIP){
            callUs("tel:+94114705705");
        }

    }

    private void callUs(String number){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(number));
        startActivity(callIntent);
    }



}
