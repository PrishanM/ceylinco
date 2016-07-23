package com.ceylinco.ceylincocustomerapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Prishan Maduka on 7/23/2016.
 */
public class PaymentModeRegistrationTwo extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    ImageView imgYearOfMake;
    static TextView yearOfMake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_registration_two);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText(getResources().getString(R.string.registration_title));
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);

        initialize();
    }

    private void initialize() {

        btnLogin = (Button)findViewById(R.id.btnLogin);
        imgYearOfMake = (ImageView)findViewById(R.id.imgYear);
        yearOfMake = (TextView)findViewById(R.id.yearTextview);

        btnLogin.setOnClickListener(this);
        imgYearOfMake.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnLogin){

        }else if(v.getId()==R.id.imgYear){
            showDatePicker();
        }
    }

    DatePickerDialog.OnDateSetListener makeYearListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            yearOfMake.setText(dayOfMonth+"/"+monthOfYear+"/"+year);

        }
    };

    private void showDatePicker() {
        DatePickerCustom date = new DatePickerCustom();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("YEAR", calender.get(Calendar.YEAR));
        args.putInt("MONTH", calender.get(Calendar.MONTH));
        args.putInt("DAY", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(makeYearListener);
        date.show(getSupportFragmentManager(), "Date Picker");
    }

}
