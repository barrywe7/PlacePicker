package com.barryirvine.tide.placepicker.ui.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.barryirvine.tide.placepicker.R;
import com.barryirvine.tide.placepicker.model.Place;
import com.barryirvine.tide.placepicker.ui.adapter.BarsAdapter;

import java.util.ArrayList;
import java.util.List;

public class BarListFragment extends Fragment {

    private List<Place> mBars;
    private Location mLocation;

    public BarListFragment() {
    }

    public static BarListFragment newInstance(@NonNull final ArrayList<Place> bars, @NonNull final Location location) {
        final BarListFragment fragment = new BarListFragment();
        final Bundle args = new Bundle();
        args.putParcelableArrayList(Args.BARS, bars);
        args.putParcelable(Args.LOCATION, location);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bar_list, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new BarsAdapter(mBars, mLocation));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        if (((GridLayoutManager) recyclerView.getLayoutManager()).getSpanCount() > 1) {
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        }
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBars = getArguments().getParcelableArrayList(Args.BARS);
        mLocation = getArguments().getParcelable(Args.LOCATION);
    }

    private class Args {
        private static final String BARS = "BARS";
        private static final String LOCATION = "LOCATION";
    }
}
