package com.ceylinco.ceylincocustomerapp.util;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author prishanm 06/01/2016
 *
 */
public class AppController extends Application{
    private static final String TAG = AppController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    public static HashMap<String,ArrayList<String>> listHashMap = null;
    public static HashMap<Integer,Boolean> integerHashMap = new HashMap<>();
    public static ArrayList<String> vehicleMakeList = null;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static ArrayList<String> getVehicleMakeList() {
        return vehicleMakeList;
    }

    public static void setVehicleMakeList(ArrayList<String> vehicleMakeList) {
        AppController.vehicleMakeList = vehicleMakeList;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {

            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new BitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public static int getColor(Context context,int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static HashMap<String, ArrayList<String>> getListHashMap() {
        return listHashMap;
    }

    public static void setListHashMap(HashMap<String, ArrayList<String>> listHashMap) {
        AppController.listHashMap = listHashMap;
    }

    public static HashMap<Integer, Boolean> getIntegerHashMap() {
        return integerHashMap;
    }

    public static void setIntegerHashMap(HashMap<Integer, Boolean> integerHashMap) {
        AppController.integerHashMap = integerHashMap;
    }
}
