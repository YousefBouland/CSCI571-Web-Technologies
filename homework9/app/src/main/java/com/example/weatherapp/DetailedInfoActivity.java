package com.example.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PorterDuff;
import android.icu.math.BigDecimal;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

//import java.util.ArrayList;
//import java.util.List;


public class DetailedInfoActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_tab_today,
            R.drawable.ic_tab_weekly,
            R.drawable.ic_tab_photos
    };
    private JSONObject currentlyObj;
    private JSONObject dailyObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            setTheme(R.style.AppTheme);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detailed_info);

            JSONObject obj = new JSONObject(getIntent().getStringExtra("json"));

//            JSONObject currentlyObj = obj.getJSONObject("currently");
            currentlyObj = obj.getJSONObject("currently");
            dailyObj = obj.getJSONObject("daily");


/*
detailedWindSpeedId
detailedPressureId
detailedPrecipitationId
detailedTemperatureId

detailedSummaryCenterImageId   // ImageView Special use hashmap for value
detailedSummaryCenterTextId    // Textview Special use hashmap for value

detailedHumidityId
detailedVisibilityId
detailedCloudCoverId
detailedOzoneId
 */

            toolbar = (Toolbar) findViewById(R.id.toolbar);

            String addressText = new String(getIntent().getStringExtra("address"));
            toolbar.setTitle(addressText);


            setSupportActionBar(toolbar);


            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            viewPager = (ViewPager) findViewById(R.id.viewPager);
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);

            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
            setupTabIcons();
            setupTabColors();


            tabLayout.addOnTabSelectedListener(
                    new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            super.onTabSelected(tab);
                            int tabIconColor = ContextCompat.getColor(DetailedInfoActivity.this, R.color.tabSelectedIconColor);
                            tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {
                            super.onTabUnselected(tab);
                            int tabIconColor = ContextCompat.getColor(DetailedInfoActivity.this, R.color.tabUnselectedIconColor);
                            tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {
                            super.onTabReselected(tab);
                        }
                    }
            );

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void setupTabColors() {
        int tabIconColor = ContextCompat.getColor(DetailedInfoActivity.this, R.color.tabUnselectedIconColor);
        tabLayout.getTabAt(1).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private String title[] = {"TODAY", "WEEKLY", "PHOTOS"};

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            TabFragment.setJsonObjs(currentlyObj, dailyObj);
            String addressText = new String(getIntent().getStringExtra("address"));
            TabFragment.setAddress(addressText);
            return TabFragment.getInstance(position);
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_twitter, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.twitter_button:

                String addressText = new String(getIntent().getStringExtra("address"));
                int temperature = 0;
                try {
                    temperature = Math.round(BigDecimal.valueOf(currentlyObj.getDouble("temperature")).floatValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://twitter.com/intent/tweet?text=Check Out " + addressText + "\'s Weather! It is " + temperature + "°F! &hashtags=CSCI571WeatherSearch"));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "Twitter app isn't found", Toast.LENGTH_LONG).show();
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}







