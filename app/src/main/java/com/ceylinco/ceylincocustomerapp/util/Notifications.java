package com.ceylinco.ceylincocustomerapp.util;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.ceylinco.ceylincocustomerapp.R;

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
        AlertDialog dialog = builder.create();

        return dialog;
    }

    //Http request-response  error dialog
    public AlertDialog showHttpErrorDialog(Context context,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog dialog = builder.create();

        return dialog;
    }
}
