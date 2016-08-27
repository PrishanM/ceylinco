package com.ceylinco.ceylincocustomerapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

public class DetectNetwork {

	private static Context mContext;
    
    public DetectNetwork(Context context){
        setmContext(context);
    }
    
	public static Context getmContext() {
		return mContext;
	}

	public static void setmContext(Context mContext) {
		DetectNetwork.mContext = mContext;
	}
	
	public static boolean isConnected(){
        ConnectivityManager conManager = (ConnectivityManager) getmContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		return conManager.getActiveNetworkInfo() != null && conManager.getActiveNetworkInfo().isAvailable() && conManager.getActiveNetworkInfo().isConnected();
    }

	public static boolean isLocationEnabled(Context context) {
		int locationMode = 0;
		String locationProviders;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
			try {
				locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

			} catch (Settings.SettingNotFoundException e) {
				e.printStackTrace();
			}

			return locationMode != Settings.Secure.LOCATION_MODE_OFF;

		}else{
			locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
			return !TextUtils.isEmpty(locationProviders);
		}


	}
	
}
