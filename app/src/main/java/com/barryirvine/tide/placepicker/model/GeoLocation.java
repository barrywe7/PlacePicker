package com.barryirvine.tide.placepicker.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

public class GeoLocation implements Parcelable {

    public static final Parcelable.Creator<GeoLocation> CREATOR = new Parcelable.Creator<GeoLocation>() {
        @Override
        public GeoLocation createFromParcel(Parcel source) {
            return new GeoLocation(source);
        }

        @Override
        public GeoLocation[] newArray(int size) {
            return new GeoLocation[size];
        }
    };
    @SerializedName("lat")
    private double mLatitude;
    @SerializedName("lng")
    private double mLongitude;

    public GeoLocation() {
    }

    protected GeoLocation(Parcel in) {
        this.mLatitude = in.readDouble();
        this.mLongitude = in.readDouble();
    }

    protected Location getLocation() {
        final Location location = new Location("");
        location.setLatitude(mLatitude);
        location.setLongitude(mLongitude);
        return location;
    }

    protected LatLng getLatLng() {
        return new LatLng(mLatitude, mLongitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeDouble(mLatitude);
        dest.writeDouble(mLongitude);
    }
}
