package com.ceylinco.ceylincocustomerapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Prishan Maduka on 7/23/2016.
 */
public class DatePickerCustom extends DialogFragment {

    DatePickerDialog.OnDateSetListener onDateSet;

    public DatePickerCustom(){

    }

    public void setCallBack(DatePickerDialog.OnDateSetListener onDate) {
        onDateSet = onDate;
    }

    private int year, month, day;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("YEAR");
        month = args.getInt("MONTH");
        day = args.getInt("DAY");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), onDateSet, year, month, day);
    }


}
