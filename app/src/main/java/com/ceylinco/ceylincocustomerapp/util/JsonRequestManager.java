package com.ceylinco.ceylincocustomerapp.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ceylinco.ceylincocustomerapp.models.LocationModel;
import com.ceylinco.ceylincocustomerapp.models.NewInsuranceFormModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author prishanm 06/01/2016
 *
 */
public class JsonRequestManager {

	private static JsonRequestManager mInstance;
	private RequestQueue mRequestQueue;
	private static Context mCtx;

	/**
	 * Log or request TAG
	 */
	public static final String TAG = "VolleyInstance";

	/* Volley */
	public static synchronized JsonRequestManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new JsonRequestManager(context);
		}
		return mInstance;
	}

	private JsonRequestManager(Context context) {
		mCtx = context;
		mRequestQueue = getRequestQueue();
	}

	private RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley
					.newRequestQueue(mCtx.getApplicationContext());
		}
		return mRequestQueue;
	}

	/******************************************************************************************************************************************/

	/*
	 * Location Data
	 * */

	public interface getLocationDetailsRequest {
		void onSuccess(LocationModel s);

		void onError();
	}

	public void getLocationDetails(String url,
								   final getLocationDetailsRequest callback) {


		JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						ObjectMapper mapper = new ObjectMapper();
						try {
							LocationModel locationModel = mapper.readValue(response.toString(), LocationModel.class);
							callback.onSuccess(locationModel);
						} catch (Exception e) {
							callback.onError();
						}
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				callback.onError();
			}
		});


		req.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		String tag_json_arry = "json_array_req";
		AppController.getInstance().addToRequestQueue(req,
				tag_json_arry);

	}

	/******************************************************************************************************************************************/

	/*
	 * Third party new insurance
	 * */

	public interface thirdPartyInsuranceRequest {
		void onSuccess(String s);

		void onError(String status);
	}

	public void thirdPartyInsurance(String url, final NewInsuranceFormModel model, final thirdPartyInsuranceRequest callback) {


		StringRequest req = new StringRequest(Request.Method.POST, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d("xxxxyy",response.toString());
						ObjectMapper mapper = new ObjectMapper();
						try {
							callback.onSuccess(response.toString());
						} catch (Exception e) {
							Log.d("xxxxyy",e.getMessage());
							callback.onError("Error occurred");
						}
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				callback.onError(VolleyErrorHelper.getMessage(volleyError,
						mCtx));
			}
		}){

			@Override
			public String getBodyContentType() {
				return "application/x-www-form-urlencoded; charset=UTF-8";
			}

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<>();
				params.put("vtype", model.getvType());
				params.put("vno", model.getvNo());
				params.put("rtype", model.getRegType());
				params.put("name", model.getName());
				params.put("contact_no", model.getContactNo());
				params.put("email", model.getEmail());
				params.put("address", model.getAddress());
				params.put("nic", model.getNic());
				params.put("ch_no", model.getChasisNo());
				params.put("en_no", model.getEngineNo());
				params.put("make_year", model.getMakeYear());
				params.put("branch", model.getBranch());
				params.put("location", model.getLocation());
				params.put("make", model.getMake());
				params.put("model", model.getModel());
				return params;
			}

		};


		req.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		String tag_json_arry = "json_array_req";
		AppController.getInstance().addToRequestQueue(req,
				tag_json_arry);

	}


}
