package com.example.weatherapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PlansPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public PlansPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        return new DynamicFragment();
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
