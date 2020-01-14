package com.example.weatherapp;

import android.content.Intent;
import android.graphics.Color;
import android.icu.math.BigDecimal;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class DynamicFragment extends Fragment {

    RequestQueue queue;
    private HashMap<String, Integer> iconMapping = new HashMap<String, Integer>() {{
        put("clear-day", R.drawable.clear_day);
        put("clear-night", R.drawable.clear_night);
        put("rain", R.drawable.rain);
        put("sleet", R.drawable.sleet);
        put("snow", R.drawable.snow);
        put("wind", R.drawable.snow);
        put("fog", R.drawable.fog);
        put("cloudy", R.drawable.cloudy);
        put("partly-cloudy-night", R.drawable.partly_cloudy_night);
        put("partly-cloudy-day", R.drawable.partly_cloudy_day);
    }};

//    public static DynamicFragment newInstance(int val) {
//        DynamicFragment fragment = new DynamicFragment();
//        Bundle args = new Bundle();
//        args.putInt("someInt", val);
//        fragment.setArguments(args);
//        return fragment;
//    }

    //int val;
    //TextView c;
    TextView textView;
    String addressText;
    SimpleDateFormat simpleDateFormat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container,
                false);
        //c = view.findViewById(R.id.c);

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(getActivity());

        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

        getCurrentLoc(view);

        return view;


//        val = getArguments().getInt("someInt", 0);
//        c = view.findViewById(R.id.c);
//        c.setText("Content: " + val);
//
//        CardView cv = view.findViewById(R.id.card1);
//        cv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(),
//                        DetailedInfoActivity.class));
//            }
//        });
//
//        return view;
    }

    private void getCurrentLoc(final View view) {
//        //        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=936 S Olive st, L,&key=AIzaSyDXTkcwPIMqavJUEj614GlBoRMbesk1Yss";
        String url = "http://ip-api.com/json";
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject locationResponse) {
                        try {
                            String city = locationResponse.getString("city");
                            String state = locationResponse.getString("region");
                            String country = locationResponse.getString("countryCode");
                            float lat = BigDecimal.valueOf(locationResponse.getDouble("lat")).floatValue();
                            float lon = BigDecimal.valueOf(locationResponse.getDouble("lon")).floatValue();

                            Log.d("GOT HERE", locationResponse.getString("status"));

                            // TODO: need to handle case when city, state, country is inputted through user search as well
                            addressText = city + ", " + state + ", " + country;

//                            c.setText("Mumbai" + ", " + "Maharashtra" + ", " + "India");

//                            c.append("\n lat: " + lat + "\n lon: " + lon);


// use this for searching   String url = "https://maps.googleapis.com/maps/api/geocode/json?address=936 S Olive st, L,&key=AIzaSyDXTkcwPIMqavJUEj614GlBoRMbesk1Yss";
                            //call on get weather summary and grab following piece of code and copy it there

                            getCurrentWeather(view, lat, lon);


                        } catch (JSONException e) {
                            Log.d("ERROR", null);
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("Current Location Response Error", error.getMessage());
                    }
                });

        // Add request to the RequestQueue
        queue.add(request);
    }

    // api server call format: /getCurrentWeather?lat=##&lng=##
    private void getCurrentWeather(final View view, float lat, float lon) {

        String url = "https://api.darksky.net/forecast/7d6cac44d431677acd7199c2285ccdf4/" + lat + "," + lon;
        // TODO use hosted server API call before releasing the app
//        String url = "http://responsiveweatherapp-env.m5ggzbfhjc.us-east-2.elasticbeanstalk.com/getCurrentWeather?lat=" + lat + "&lng=" + lon;
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        try {
                            JSONObject currentlyObj = response.getJSONObject("currently");
                            JSONObject dailyObj = response.getJSONObject("daily");
                            Log.d("DATAA PASSED YO", response.getString("timezone"));

                            populateSummaryCard1(view, currentlyObj);

                            populateSummaryCard2(view, currentlyObj);

                            populateSummaryCard3(view, dailyObj);


                            TableLayout tableLayout = (TableLayout) view.findViewById(R.id.card3Table);

                            CardView cv = view.findViewById(R.id.card1);
                            cv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent newIntent = new Intent(getActivity(), DetailedInfoActivity.class);
                                    // Add entire data object response
                                    newIntent.putExtra("json", response.toString());
                                    newIntent.putExtra("address", addressText);
                                    startActivity(newIntent);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        RelativeLayout spinner = (RelativeLayout) view.findViewById(R.id.progressBar_content);
                        RelativeLayout content = (RelativeLayout) view.findViewById(R.id.main_content);
                        spinner.setVisibility(View.GONE);
                        content.setVisibility(View.VISIBLE);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Current Weather Reponse Error", error.getMessage());
                    }
                });
        // Add request to the RequestQueue
        queue.add(request);

    }


    private void populateSummaryCard1(View view, JSONObject currentlyObj) {
        try {
            int temperature = Math.round(BigDecimal.valueOf(currentlyObj.getDouble("temperature")).floatValue());
            TextView temperatureTextView = view.findViewById(R.id.temperature);
            temperatureTextView.setText(temperature + "°F");

            String summaryText = currentlyObj.getString("summary");
            TextView summaryBox = view.findViewById(R.id.summary);
            summaryBox.setText(summaryText);
//                    summaryBox.setText("This is a long line to test if the ellipses do really work");

            String iconText = currentlyObj.getString("icon");
            ImageView iconBox = view.findViewById(R.id.summaryIcon);

            iconBox.setImageResource(iconMapping.get(iconText));

            TextView locationTextView = view.findViewById(R.id.location);
            locationTextView.setText(addressText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populateSummaryCard2(View view, JSONObject currentlyObj) {
        int humidityPercentValue = 0;
        float windSpeed = 0;
        float visibility = 0;
        float pressure = 0;

        DecimalFormat decimalFormatter = new DecimalFormat("0.00");

        try {
            if (currentlyObj.has("humidity")) {
                float humidityFloatValue = BigDecimal.valueOf(currentlyObj.getDouble("humidity")).floatValue();
                humidityPercentValue = Math.round(humidityFloatValue * 100);
            }
            if (currentlyObj.has("windSpeed")) {
                windSpeed = BigDecimal.valueOf(currentlyObj.getDouble("windSpeed")).floatValue();
//                windSpeed = Math.round(windSpeed * 100) / 100;
            }
            if (currentlyObj.has("visibility")) {
                visibility = BigDecimal.valueOf(currentlyObj.getDouble("visibility")).floatValue();
//                visibility = Math.round(visibility * 100) / 100;
            }
            if (currentlyObj.has("pressure")) {
                pressure = BigDecimal.valueOf(currentlyObj.getDouble("pressure")).floatValue();
//                pressure = Math.round(pressure * 100) / 100;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView humidityTextView = view.findViewById(R.id.humidityCardText);
        TextView windSpeedTextView = view.findViewById(R.id.windSpeedCardText);
        TextView visibilityTextView = view.findViewById(R.id.visibilityCardText);
        TextView pressureTextView = view.findViewById(R.id.pressureCardText);

        //  android:text="79%"
        humidityTextView.setText(humidityPercentValue + "%");
        //  android:text="0.17 mph"
        windSpeedTextView.setText(decimalFormatter.format(windSpeed) + " mph");
        //  android:text="9.33 km"
        visibilityTextView.setText(decimalFormatter.format(visibility) + " km");
        //  android:text="1015.80 mb"
        pressureTextView.setText(decimalFormatter.format(pressure) + " mb");
    }

    private void populateSummaryCard3(View view, JSONObject dailyObj) {
        try {
            JSONArray dailyDataObj = dailyObj.getJSONArray("data");
            Log.d("Printing JSON", dailyDataObj.toString());
            /* Find Tablelayout defined in main.xml */
            TableLayout tl = (TableLayout) view.findViewById(R.id.card3Table);


            for (int i = 0; i < dailyDataObj.length(); i++) {
                JSONObject currentJSONObj = dailyDataObj.getJSONObject(i);
                /* Create a new row to be added. */
                TableRow tr = new TableRow(getContext());

                int paddingTop = 50;
                int paddingBottom = 50;

                //Set date
                Date currentDate = new Date(currentJSONObj.getLong("time") * 1000);
                TextView dateText = new TextView(getContext());
                dateText.setText(simpleDateFormat.format(currentDate));
                dateText.setTextColor(Color.WHITE);
                dateText.setTextSize(20);
                dateText.setPadding(50,paddingTop,240,paddingBottom);

                // Set logo image
                ImageView rowLogo = new ImageView(getContext());

                // Get the mapping from icon name to logo image resource
                DynamicFragment dynamicFragment = new DynamicFragment();
                HashMap<String, Integer> iconMapping = dynamicFragment.getIconMapping();
                String iconText = currentJSONObj.getString("icon");
                rowLogo.setImageResource(iconMapping.get(iconText));
                rowLogo.setPadding(20, 70,90,paddingBottom);

                // Set min and max columns
                TextView minTemp = new TextView(getContext());
                TextView maxTemp = new TextView(getContext());

                minTemp.setTextColor(Color.WHITE);
                minTemp.setTextSize(25);
                minTemp.setPadding(20,paddingTop,140,paddingBottom);


                maxTemp.setTextColor(Color.WHITE);
                maxTemp.setTextSize(25);
                maxTemp.setPadding(10,paddingTop,40,paddingBottom);


//                TextView dateText = new TextView(getContext());
                minTemp.setText(Math.round(BigDecimal.valueOf(currentJSONObj.getDouble("temperatureLow")).floatValue()) + "");
                maxTemp.setText(Math.round(BigDecimal.valueOf(currentJSONObj.getDouble("temperatureHigh")).floatValue()) + "");

                tr.addView(dateText);
                tr.addView(rowLogo);

                tr.addView(minTemp);
                tr.addView(maxTemp);



                tl.addView(tr);

                if (i < 7) {
                    View divider = new View(getContext());
                    divider.setBackgroundColor(getResources().getColor(R.color.dividerGrey));
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 5);
                    divider.setLayoutParams(params);

                    tl.addView(divider);
                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public HashMap<String, Integer> getIconMapping() {
        return iconMapping;
    }
}
