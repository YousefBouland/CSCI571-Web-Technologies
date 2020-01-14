package com.example.weatherapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ApiCall {
    private static ApiCall mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    public ApiCall(Context ctx) {
        mCtx = ctx;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized ApiCall getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ApiCall(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /// server api call format: /autoComplete?input=##
    public static void makeCityAutoCompleteApiCall(Context ctx, String query, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {
        String url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" + query
                + "&types=(cities)&language=en&key=AIzaSyDXTkcwPIMqavJUEj614GlBoRMbesk1Yss";

        // TODO use hosted server API call before releasing the app
//        String url = "http://responsiveweatherapp-env.m5ggzbfhjc.us-east-2.elasticbeanstalk.com/autoComplete?input=" + query;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(stringRequest);
    }

    // server API call format: /getCityPhotos?address=
    public static void makePhotosSearchApiCall(Context ctx, String query, Response.Listener<String>
            listener, Response.ErrorListener errorListener) throws UnsupportedEncodingException {

        String url = "https://www.googleapis.com/customsearch/v1?q=" + query
                + "&cx=013503531998323216086:a9zxjglx49t&imgSize=xxlarge&num=8&searchType=image&key=AIzaSyD2SLGi5ugEoK0dQWKaRkbV39MslontTo8";
        // TODO use hosted server API call before releasing the app
//        String imageSize= "xxlarge";
//        String url = "http://responsiveweatherapp-env.m5ggzbfhjc.us-east-2.elasticbeanstalk.com/getCityPhotos?address=" + URLEncoder.encode(query, "utf-8") + "&imgSize=" + imageSize;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(stringRequest);
    }
}
