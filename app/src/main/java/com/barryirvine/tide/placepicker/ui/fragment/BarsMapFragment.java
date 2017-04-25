package com.barryirvine.tide.placepicker.ui.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.barryirvine.tide.placepicker.model.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barryirvine on 25/04/2017.
 */

public class BarsMapFragment extends SupportMapFragment implements OnMapReadyCallback {

    private List<Place> mBars;
    private Location mLocation;
    private GoogleMap mGoogleMap;

    public static BarsMapFragment newInstance(@NonNull final ArrayList<Place> bars, @NonNull final Location location) {
        final BarsMapFragment fragment = new BarsMapFragment();
        final Bundle args = new Bundle();
        args.putParcelableArrayList(Args.BARS, bars);
        args.putParcelable(Args.LOCATION, location);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        mLocation = getArguments().getParcelable(Args.LOCATION);
        mBars = getArguments().getParcelableArrayList(Args.BARS);
        getMapAsync(this);
    }

    @Override
    @SuppressWarnings("MissingPermission")
    public void onMapReady(final GoogleMap googleMap) {
        mGoogleMap = googleMap;
        final LatLng home = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 14.0f));
        for (final Place place: mBars) {
            mGoogleMap.addMarker(new MarkerOptions()
                    .snippet(place.getDistanceAsString(getContext(), mLocation))
                    .position(place.getLatLng())
                    .visible(true)
                    .title(place.getName()));
        }
        mGoogleMap.setMyLocationEnabled(true);
    }

    private class Args {
        private static final String BARS = "BARS";
        private static final String LOCATION = "LOCATION";
    }
}
