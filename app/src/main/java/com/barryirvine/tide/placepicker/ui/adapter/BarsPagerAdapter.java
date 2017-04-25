package com.barryirvine.tide.placepicker.ui.adapter;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.barryirvine.tide.placepicker.model.BarTab;
import com.barryirvine.tide.placepicker.model.Place;
import com.barryirvine.tide.placepicker.ui.fragment.BarListFragment;
import com.barryirvine.tide.placepicker.ui.fragment.BarsMapFragment;

import java.util.ArrayList;


public class BarsPagerAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private ArrayList<Place> mBars;
    private Location mLocation;


    public BarsPagerAdapter(@NonNull final Context context, final FragmentManager fm, final ArrayList<Place> bars, final Location location) {
        super(fm);
        mContext = context;
        mLocation = location;
        mBars = bars;
    }

    @Override
    public final Fragment getItem(final int position) {
        switch (position) {
            case 0:
                return BarListFragment.newInstance(mBars, mLocation);
            case 1:
            default:
                return BarsMapFragment.newInstance(mBars, mLocation);
        }
    }

    @Override
    public final int getCount() {
        return BarTab.values().length;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return mContext.getString(BarTab.values()[position].getDescription());
    }

}