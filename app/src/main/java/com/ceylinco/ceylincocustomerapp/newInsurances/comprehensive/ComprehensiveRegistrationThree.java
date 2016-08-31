package com.ceylinco.ceylincocustomerapp.newInsurances.comprehensive;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceylinco.ceylincocustomerapp.util.DatePickerCustom;
import com.ceylinco.ceylincocustomerapp.R;

import java.util.Calendar;

/**
 * Created by Prishan Maduka on 7/23/2016.
 */
public class ComprehensiveRegistrationThree extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private ImageView imgFirstReg;
    private ImageView imgPurchaseDate;
    private static TextView firstRegistration;
    private static TextView purchaseDateTextView;
    private boolean isBrandNew = true;
    private LinearLayout layoutPurchaseDate;
    private LinearLayout layoutFirstRegDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comprehensive_registration_three);

        isBrandNew = getIntent().getBooleanExtra("IS_BRAND_NEW",true);

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
        imgFirstReg = (ImageView)findViewById(R.id.imgFirstReg);
        imgPurchaseDate = (ImageView)findViewById(R.id.imgPurchaseDate);
        firstRegistration = (TextView)findViewById(R.id.firstRegistrationTextview);
        purchaseDateTextView = (TextView)findViewById(R.id.purchaseDateTextview);
        layoutPurchaseDate = (LinearLayout)findViewById(R.id.layoutPurchaseDate);
        layoutFirstRegDate = (LinearLayout)findViewById(R.id.layoutFirstRegDate);

        if (!isBrandNew){
            layoutFirstRegDate.setVisibility(View.VISIBLE);
            layoutPurchaseDate.setVisibility(View.VISIBLE);
        }

        btnLogin.setOnClickListener(this);
        imgFirstReg.setOnClickListener(this);
        imgPurchaseDate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnLogin){

        }else if(v.getId()==R.id.imgFirstReg){
            showDatePicker(false);
        }else if(v.getId()==R.id.imgPurchaseDate){
            showDatePicker(true);
        }
    }

    private final DatePickerDialog.OnDateSetListener purchaseDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            purchaseDateTextView.setText(dayOfMonth+"/"+monthOfYear+"/"+year);

        }
    };

    private final DatePickerDialog.OnDateSetListener regDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            firstRegistration.setText(dayOfMonth+"/"+monthOfYear+"/"+year);

        }
    };

    private void showDatePicker(boolean isPurchaseDate) {
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
        if(isPurchaseDate)
            date.setCallBack(purchaseDateListener);
        else
            date.setCallBack(regDateListener);
        date.show(getSupportFragmentManager(), "Date Picker");
    }
}
