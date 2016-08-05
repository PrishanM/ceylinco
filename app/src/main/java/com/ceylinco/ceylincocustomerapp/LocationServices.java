package com.ceylinco.ceylincocustomerapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Prishan Maduka on 8/5/2016.
 */
public class LocationServices extends Fragment {

    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.inquiry_layout, container, false);

        return rootView;
    }
}
