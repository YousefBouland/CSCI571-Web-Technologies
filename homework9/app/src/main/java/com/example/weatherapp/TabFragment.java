package com.example.weatherapp;

import android.graphics.Color;
import android.icu.math.BigDecimal;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {

    int position;
    private TextView textView;
    private static JSONObject currentlyObj;
    private static JSONObject dailyObj;
    private static String address;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
//    private LayoutInflater inflater;


    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);

//        String jsonObjString=jsonObj.toString();
//        bundle.putString("jsonObj", jsonObjString);

        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    public static void setJsonObjs(JSONObject jsonObjCurrently, JSONObject jsonObjdaily) {
        currentlyObj = jsonObjCurrently;
        dailyObj = jsonObjdaily;
    }

    public static void setAddress(String inputAddress) {
        address = inputAddress;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        this.inflater = inflater;
        final View view = inflater.inflate(R.layout.fragment_tab, container, false);
        switch (position) {
            case 0:
                addTodayLayout(view);
                break;
            case 1:
                addWeeklyLayout(view);
                break;
            case 2:
                try {
                    addPhotosLayout(view);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
        }
        return view;
    }


    private void addTodayLayout(View view) {
        View todayView = getLayoutInflater().inflate(R.layout.fragment_tab_today, null);
        LinearLayout tabContentLayout = (LinearLayout) view.findViewById(R.id.tab_content_id);

        float windSpeed = 0;
        float pressure = 0;

        float precipIntensity = 0;
        String iconText = "clear-day";
        String summaryText = "N/A";
        int humidityPercentValue = 0;

        float visibility = 0;

        int cloudCoverPercentValue = 0;
        float ozone = 0;

        try {
            DecimalFormat decimalFormatter = new DecimalFormat("0.00");
            decimalFormatter.setRoundingMode(RoundingMode.HALF_UP);

            int temperature = Math.round(BigDecimal.valueOf(currentlyObj.getDouble("temperature")).floatValue());
            TextView temperatureTextView = todayView.findViewById(R.id.dailyTemperatureId);
            temperatureTextView.setText(temperature + "°F");


            // If windSpeed value is present change from default and set it
            if (currentlyObj.has("windSpeed")) {
                windSpeed = BigDecimal.valueOf(currentlyObj.getDouble("windSpeed")).floatValue();
//                windSpeed = Math.round(windSpeed * 100) / 100;
            }
            TextView windSpeedTextView = todayView.findViewById(R.id.dailyWindSpeedId);
            windSpeedTextView.setText(decimalFormatter.format(windSpeed) + " mph");


            // If pressure value is present change from default and set it
            if (currentlyObj.has("pressure")) {
                pressure = BigDecimal.valueOf(currentlyObj.getDouble("pressure")).floatValue();
//                pressure = Math.round(pressure * 100) / 100;
            }
            TextView pressureTextView = todayView.findViewById(R.id.dailyPressureId);
            pressureTextView.setText(decimalFormatter.format(pressure) + " mb");


            // If precipitation value is present change from default and set it
            if (currentlyObj.has("precipIntensity")) {
                precipIntensity = BigDecimal.valueOf(currentlyObj.getDouble("precipIntensity")).floatValue();
//                precipIntensity = Math.round(precipIntensity * 100) / 100;
            }
            TextView precipitationTextView = todayView.findViewById(R.id.dailyPrecipitationId);
            precipitationTextView.setText(decimalFormatter.format(precipIntensity) + " mmph");

            // If icon image value is present change from default and set it
            if (currentlyObj.has("icon")) {
                iconText = currentlyObj.getString("icon");
                summaryText = iconText.replaceAll("[-]", " ");

            }
            DynamicFragment dynamicFragment = new DynamicFragment();
            HashMap<String, Integer> iconMapping = dynamicFragment.getIconMapping();
            ImageView iconBox = todayView.findViewById(R.id.dailySummaryCenterImageId);
            iconBox.setImageResource(iconMapping.get(iconText));

            TextView summaryTextView = todayView.findViewById(R.id.dailySummaryCenterTextId);
            summaryTextView.setText(summaryText);


            // If humidity value is present change from default and set it
            if (currentlyObj.has("humidity")) {
                float humidityFloatValue = BigDecimal.valueOf(currentlyObj.getDouble("humidity")).floatValue();
                humidityPercentValue = Math.round(humidityFloatValue * 100);
            }
            TextView humidityTextView = todayView.findViewById(R.id.dailyHumidityId);
            humidityTextView.setText(humidityPercentValue + "%");


            // If visibility value is present change from default and set it
            if (currentlyObj.has("visibility")) {
                visibility = BigDecimal.valueOf(currentlyObj.getDouble("visibility")).floatValue();
//                visibility = Math.round(visibility * 100) / 100;
            }
            TextView visibilityTextView = todayView.findViewById(R.id.dailyVisibilityId);
            visibilityTextView.setText(decimalFormatter.format(visibility) + " km");


            // If cloudCover value is present change from default and set it
            if (currentlyObj.has("cloudCover")) {
                float cloudCoverValue = BigDecimal.valueOf(currentlyObj.getDouble("cloudCover")).floatValue();
                cloudCoverPercentValue = Math.round(cloudCoverValue * 100);
            }
            TextView cloudCoverTextView = todayView.findViewById(R.id.dailyCloudCoverId);
            cloudCoverTextView.setText(cloudCoverPercentValue + "%");


            // If ozone value is present change from default and set it
            if (currentlyObj.has("ozone")) {
                ozone = BigDecimal.valueOf(currentlyObj.getDouble("ozone")).floatValue();
//                ozone = Math.round(ozone * 100) / 100;
            }
            TextView ozoneTextView = todayView.findViewById(R.id.dailyOzoneId);
            ozoneTextView.setText(decimalFormatter.format(ozone) + " DU");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        tabContentLayout.addView(todayView);
    }

    private void addWeeklyLayout(View view) {
        View weeklyView = getLayoutInflater().inflate(R.layout.fragment_tab_weekly, null);
        LinearLayout tabContentLayout = (LinearLayout) view.findViewById(R.id.tab_content_id);


        String iconText = "clear-day";
        String summaryText = "N/A";


        // If icon image value is present change from default and set it
        try {
            iconText = dailyObj.getString("icon");
            summaryText = dailyObj.getString("summary");

            //        summaryText = iconText.replaceAll("[-]", " ");

            DynamicFragment dynamicFragment = new DynamicFragment();
            HashMap<String, Integer> iconMapping = dynamicFragment.getIconMapping();
            ImageView iconBox = weeklyView.findViewById(R.id.weeklySummaryCenterImageId);
            iconBox.setImageResource(iconMapping.get(iconText));

            TextView summaryTextView = weeklyView.findViewById(R.id.weeklySummary);
            summaryTextView.setText(summaryText);

            JSONArray dailyData = dailyObj.getJSONArray("data");
            List<Entry> valsTempLow = new ArrayList<>();
            List<Entry> valsTempHigh = new ArrayList<>();

            for (int i = 0; i < dailyData.length(); i++) {
                //JSONObject currentRowObj = ;
                int currentTemperatureLow = Math.round(BigDecimal.valueOf(dailyData.getJSONObject(i).getDouble("temperatureLow")).floatValue());
                int currentTemperatureHigh = Math.round(BigDecimal.valueOf(dailyData.getJSONObject(i).getDouble("temperatureHigh")).floatValue());

                valsTempLow.add(new Entry(i, currentTemperatureLow));
                valsTempHigh.add(new Entry(i, currentTemperatureHigh));
            }

            LineDataSet setTempLow = new LineDataSet(valsTempLow, "Minimum Temperature");
            setTempLow.setAxisDependency(YAxis.AxisDependency.LEFT);
            setTempLow.setColor(ContextCompat.getColor(getContext(), R.color.chartPurple));
            LineDataSet setTempHigh = new LineDataSet(valsTempHigh, "Maximum Temperature");
            setTempHigh.setAxisDependency(YAxis.AxisDependency.LEFT);
            setTempHigh.setColor(ContextCompat.getColor(getContext(), R.color.chartOrange));


            List<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(setTempLow);
            dataSets.add(setTempHigh);

            LineData data = new LineData(dataSets);
            LineChart mLineChart = (LineChart) weeklyView.findViewById(R.id.chart);

            mLineChart.getAxisLeft().setTextColor(Color.WHITE); // left y-axis
            mLineChart.getAxisRight().setTextColor(Color.WHITE);
            mLineChart.getXAxis().setTextColor(Color.WHITE);
            mLineChart.getXAxis().setDrawGridLines(false);
//            mLineChart.getY().setDrawGridLines(false);


            Legend l = mLineChart.getLegend();
            l.setEnabled(true);
            l.setFormSize(13f); // set the size of the legend forms/shapes
            l.setTextSize(14f);
            l.setXEntrySpace(18f);
            l.setTextColor(Color.WHITE);
//            l.setForm(Legend.LegendForm.); // set what type of form/shape should be used
            l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

            mLineChart.setData(data);
            mLineChart.invalidate(); // refresh


        } catch (JSONException e) {
            e.printStackTrace();
        }

        tabContentLayout.addView(weeklyView);
    }

    private void addPhotosLayout(View view) throws UnsupportedEncodingException {
//        RecyclerView mRecyclerView;
//        RecyclerView.Adapter mAdapter;
//        RecyclerView.LayoutManager mLayoutManager

        final View PhotosView = getLayoutInflater().inflate(R.layout.fragment_tab_photos, null);
        final LinearLayout tabContentLayout = (LinearLayout) view.findViewById(R.id.tab_content_id);

        final ArrayList<String> photoLinksList = new ArrayList<>();

        ApiCall.makePhotosSearchApiCall(getContext(), address, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseObject = new JSONObject(response);
                    JSONArray array = responseObject.getJSONArray("items");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject row = array.getJSONObject(i);
                        photoLinksList.add(row.getString("link"));
                        Log.d(i + "", photoLinksList.get(i));
                    }

                    mRecyclerView = PhotosView.findViewById(R.id.recyclerView);
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(getContext());
                    mAdapter = new PhotosAdapter(getContext(), photoLinksList);


                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);

                    tabContentLayout.addView(PhotosView);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });


    }


//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        textView = (TextView) view.findViewById(R.id.textView);
//
//        textView.setText("Fragment " + (position + 1));
//    }

}
