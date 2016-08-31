package com.ceylinco.ceylincocustomerapp.newInsurances.comprehensive;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ceylinco.ceylincocustomerapp.R;

import java.util.Calendar;

/**
 * Created by Prishan Maduka on 7/23/2016.
 */
public class ComprehensiveRegistrationTwo extends AppCompatActivity implements View.OnClickListener {

    private Button btnNext;
    private ImageView imgYearOfMake;
    private static TextView yearOfMake;
    private LinearLayout meterReadingLayout;
    private LinearLayout priceLayout;
    private boolean isBranNew = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comprehensive_registration_two);

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

        btnNext = (Button)findViewById(R.id.btnNext);
        imgYearOfMake = (ImageView)findViewById(R.id.imgYear);
        yearOfMake = (TextView)findViewById(R.id.yearTextview);
        meterReadingLayout = (LinearLayout)findViewById(R.id.meterReadingLayout);
        priceLayout = (LinearLayout)findViewById(R.id.purchasePriceLayout);

        btnNext.setOnClickListener(this);
        imgYearOfMake.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnNext){
            Intent intent = new Intent(ComprehensiveRegistrationTwo.this,ComprehensiveRegistrationThree.class);
            intent.putExtra("IS_BRAND_NEW",isBranNew);
            startActivity(intent);
        }else if(v.getId()==R.id.imgYear){
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getSupportFragmentManager(), "datePicker");
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //dateSelected = new StringBuilder().append(monthOfYear + 1).append("-").append(dayOfMonth).append("-").append(year);
            yearOfMake.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        }


    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioBrandNew:
                if (checked){
                    isBranNew = true;
                    priceLayout.setVisibility(View.GONE);
                    meterReadingLayout.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.radioReCondition:
                if (checked){
                    isBranNew = false;
                    priceLayout.setVisibility(View.VISIBLE);
                    meterReadingLayout.setVisibility(View.GONE);
                }
                break;
        }
    }
}
