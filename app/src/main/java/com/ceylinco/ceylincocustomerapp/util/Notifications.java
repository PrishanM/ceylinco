package com.ceylinco.ceylincocustomerapp.util;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;

import com.ceylinco.ceylincocustomerapp.MainActivity;
import com.ceylinco.ceylincocustomerapp.R;
import com.ceylinco.ceylincocustomerapp.existingPolicy.ExistingPolicyActivity;

/**
 * @author Prishanm
 * Common class used for notification
 * through out the app
 */

public class Notifications {

    //No network error dialog
    public AlertDialog showNetworkNotification(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.no_network_error));
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        return builder.create();
    }

    //General alert dialog
    public AlertDialog showGeneralDialog(Context context,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        return builder.create();
    }

    //No network error dialog
    public AlertDialog showGPSDisabledNotification(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("GPS network not enabled");
        builder.setPositiveButton("Open location settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
                Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(myIntent);
                //get gps
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub

            }
        });

        return builder.create();
    }

    public AlertDialog insurancePolicySuccessAlert(final Context context, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });

        return builder.create();
    }

    public AlertDialog logoutDialog(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure want to logout?");
        builder.setPositiveButton("Log out", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SharedPreferences pref = context.getSharedPreferences(context.getResources().getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent mainActivity = new Intent(context, ExistingPolicyActivity.class);
                context.startActivity(mainActivity);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

}
