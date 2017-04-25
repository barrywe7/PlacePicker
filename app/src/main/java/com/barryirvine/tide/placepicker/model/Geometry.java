package com.barryirvine.tide.placepicker.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class Geometry implements Parcelable {
    public static final Parcelable.Creator<Geometry> CREATOR = new Parcelable.Creator<Geometry>() {
        @Override
        public Geometry createFromParcel(final Parcel source) {
            return new Geometry(source);
        }

        @Override
        public Geometry[] newArray(final int size) {
            return new Geometry[size];
        }
    };
    private GeoLocation location;

    public Geometry() {
    }

    protected Geometry(final Parcel in) {
        this.location = in.readParcelable(GeoLocation.class.getClassLoader());
    }

    protected Location getLocation() {
        return location.getLocation();
    }

    protected LatLng getLatLng() {
        return location.getLatLng();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeParcelable(location, flags);
    }
}
