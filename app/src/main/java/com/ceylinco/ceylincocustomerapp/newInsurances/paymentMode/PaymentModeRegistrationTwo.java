package com.ceylinco.ceylincocustomerapp.newInsurances.paymentMode;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceylinco.ceylincocustomerapp.R;
import com.ceylinco.ceylincocustomerapp.models.NewInsuranceFormModel;
import com.ceylinco.ceylincocustomerapp.util.AppController;

/**
 * Created by Prishan Maduka on 7/23/2016.
 */
public class PaymentModeRegistrationTwo extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private ImageView imgYearOfMake;
    private static TextView yearOfMake;
    private AlertDialog alertDialog;
    private Context context;

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

        NewInsuranceFormModel formModel = getIntent().getParcelableExtra("DATA");
        Log.d("xxxxx",formModel.getAddress());

        initialize();
    }

    private void initialize() {
        context = PaymentModeRegistrationTwo.this;
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
            alertDialog = showCustomDialog(v.getId());
            alertDialog.show();
        }
    }

    private AlertDialog showCustomDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if(id == R.id.imgYear){
            builder.setTitle("Select Year of Make")
                    .setItems(R.array.make_year_array, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            yearOfMake.setTextColor(AppController.getColor(context,R.color.colorPrimaryDark));
                            String[] yearArray = context.getResources().getStringArray(R.array.make_year_array);
                            yearOfMake.setText(yearArray[which]);
                        }
                    });
        }


        return builder.create();
    }


}
