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
import com.ceylinco.ceylincocustomerapp.models.VehicleMakeModelResponse;
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
	private final String tag_json_arry = "json_array_req";

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

	/******************************************************************************************************************************************/

	/*
	* Get Vehicle Make Model List
	* */

	public interface getMakeModeltRequest {
		void onSuccess(VehicleMakeModelResponse response);

		void onError(String status);
	}

	public void getMakeModeltList(String url, final getMakeModeltRequest callback) {

		JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						ObjectMapper mapper = new ObjectMapper();
						try {
							if(response!=null) {
								VehicleMakeModelResponse vehicleMakeModelList = mapper.readValue(response.toString(), VehicleMakeModelResponse.class);
								if(vehicleMakeModelList.getResults().getType().size()!=0){
									callback.onSuccess(vehicleMakeModelList);
								}else{
									callback.onError("No Vehicle Make Model Data");
								}
							}else{
								callback.onError("No Vehicle Make Model Data");
							}

						} catch (Exception e) {
							callback.onError("Error occurred");
						}

					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				callback.onError(VolleyErrorHelper.getMessage(volleyError,
						mCtx));
			}
		});


		req.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(req,
				tag_json_arry);

	}

	/******************************************************************************************************************************************/

	/*
	 * Comprehensive new insurance
	 * */

	public interface comprehensiveInsuranceRequest {
		void onSuccess(String s);

		void onError(String status);
	}

	public void comprehensiveInsurance(String url, final NewInsuranceFormModel model, final comprehensiveInsuranceRequest callback) {


		StringRequest req = new StringRequest(Request.Method.POST, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d("xxxxyy",response.toString());
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
				params.put("val_veh", model.getVehValue());
				params.put("leasing_company", model.getLeasingCompany());
				params.put("v_condition", model.getVehCondition());
				params.put("purpose_of_use", model.getPurpose());
				params.put("cur_meter_reading", model.getCurrMeter());
				params.put("first_reg_date", model.getFirstRegDate());
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

	/******************************************************************************************************************************************/

	/*
	 * User Login
	 * */

	public interface loginRequest {
		void onSuccess(String s);

		void onError(String status);
	}

	public void userLogin(String url, final String userName, final String password, final loginRequest callback) {


		StringRequest req = new StringRequest(Request.Method.POST, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d("xxxxyy",response.toString());
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
				params.put("uname", userName);
				params.put("pass", password);
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

	/******************************************************************************************************************************************/

	/*
	 * Get User Policies
	 * */

	public interface getPoliciesRequest {
		void onSuccess(String s);

		void onError(String status);
	}

	public void getPolicies(String url, final String userName, final getPoliciesRequest callback) {


		StringRequest req = new StringRequest(Request.Method.POST, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d("xxxxyy",response.toString());
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
				params.put("uname", userName);
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

	/******************************************************************************************************************************************/

	/*
	 * Get Claims
	 * */

	public interface getClaimsRequest {
		void onSuccess(String s);

		void onError(String status);
	}

	public void getClaims(String url, final String policy,final String vehicleNumber, final getClaimsRequest callback) {


		StringRequest req = new StringRequest(Request.Method.POST, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d("xxxxyy",response.toString());
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
				params.put("pol", policy);
				params.put("veh", vehicleNumber);
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

	/******************************************************************************************************************************************/

	/*
	 * Get Claims
	 * */

	public interface getSingleClaimRequest {
		void onSuccess(String s);

		void onError(String status);
	}

	public void getSingleClaim(String url, final String refNo,final getSingleClaimRequest callback) {


		StringRequest req = new StringRequest(Request.Method.POST, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d("xxxxyy",response.toString());
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
				params.put("ref", refNo);
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

	/******************************************************************************************************************************************/

	/*
	 * Get Claims
	 * */

	public interface getPolicyDetailsRequest {
		void onSuccess(String s);

		void onError(String status);
	}

	public void getPolicyDetails(String url, final String policy,final String vehicleNumber, final getPolicyDetailsRequest callback) {


		StringRequest req = new StringRequest(Request.Method.POST, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d("xxxxyy",response.toString());
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
				params.put("pol", policy);
				params.put("veh", vehicleNumber);
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

	/******************************************************************************************************************************************/

}
