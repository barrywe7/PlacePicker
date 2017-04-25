package com.barryirvine.tide.placepicker.ui.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.barryirvine.tide.placepicker.R;
import com.barryirvine.tide.placepicker.api.BarsService;
import com.barryirvine.tide.placepicker.model.Place;
import com.barryirvine.tide.placepicker.model.PlaceResponse;
import com.barryirvine.tide.placepicker.ui.activity.MainActivity;
import com.barryirvine.tide.placepicker.ui.adapter.BarsPagerAdapter;
import com.barryirvine.tide.placepicker.viewmodel.PlaceViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class BarsPagerFragment extends Fragment{

    private ViewPager mViewPager;
    private ArrayList<Place> mBars;
    private Location mLocation;


    public BarsPagerFragment() {
    }

    public static BarsPagerFragment newInstance(final ArrayList<Place> bars, final Location location) {
        final BarsPagerFragment fragment = new BarsPagerFragment();
        final Bundle args = new Bundle();
        args.putParcelableArrayList(Args.BARS, bars);
        args.putParcelable(Args.LOCATION, location);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBars = getArguments().getParcelableArrayList(Args.BARS);
        mLocation = getArguments().getParcelable(Args.LOCATION);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bars_pager, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new BarsPagerAdapter(getContext(), getChildFragmentManager(), mBars, mLocation));
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private class Args {
        private static final String BARS = "BARS";
        private static final String LOCATION = "LOCATION";
    }
}

