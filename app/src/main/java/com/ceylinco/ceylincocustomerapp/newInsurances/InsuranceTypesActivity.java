package com.ceylinco.ceylincocustomerapp.newInsurances;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceylinco.ceylincocustomerapp.R;
import com.ceylinco.ceylincocustomerapp.newInsurances.comprehensive.ComprehensiveRegistrationOne;
import com.ceylinco.ceylincocustomerapp.newInsurances.paymentMode.PaymentModeRegistrationOne;

/**
 * Created by Prishan Maduka on 7/19/2016.
 */
public class InsuranceTypesActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView paymentMode,comprehensiveMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insurance_types);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText(getResources().getString(R.string.insurance_types_title));
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);

        initialize();
    }

    private void initialize() {

        paymentMode = (ImageView)findViewById(R.id.idPaymentMode);
        comprehensiveMode = (ImageView)findViewById(R.id.idComprehensive);

        paymentMode.setOnClickListener(this);
        comprehensiveMode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()== R.id.idPaymentMode){
            Intent intent = new Intent(InsuranceTypesActivity.this,PaymentModeRegistrationOne.class);
            startActivity(intent);

        }else if(v.getId()== R.id.idComprehensive){
            Intent intent = new Intent(InsuranceTypesActivity.this,ComprehensiveRegistrationOne.class);
            startActivity(intent);

        }

    }
}
