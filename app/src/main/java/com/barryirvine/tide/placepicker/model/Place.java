package com.barryirvine.tide.placepicker.model;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.barryirvine.tide.placepicker.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class Place implements Parcelable {

    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
        @Override
        public Place createFromParcel(final Parcel source) {
            return new Place(source);
        }

        @Override
        public Place[] newArray(final int size) {
            return new Place[size];
        }
    };
    private String icon;
    private String name;
    private float rating;
    private String vicinity;
    private Geometry geometry;
    private List<Photo> photos;
    private String placeId;

    public Place() {
    }

    protected Place(final Parcel in) {
        this.icon = in.readString();
        this.name = in.readString();
        this.rating = in.readFloat();
        this.vicinity = in.readString();
        this.geometry = in.readParcelable(Geometry.class.getClassLoader());
        this.photos = new ArrayList<>();
        in.readList(this.photos, Photo.class.getClassLoader());
        this.placeId = in.readString();
    }

    public String getPlaceId() {
        return placeId;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public float getRating() {
        return rating;
    }

    public String getVicinity() {
        return vicinity;
    }

    public Location getLocation() {
        return geometry.getLocation();
    }

    public LatLng getLatLng() {
        return geometry.getLatLng();
    }

    public Uri getLocationUri() {
        // TODO: Not super happy with this URL. It doesn't show the place name or allow it to be clicked
        return Uri.parse("http://maps.google.com/maps?daddr=" + getLocation().getLatitude() + "," + getLocation().getLongitude());
        //return Uri.parse(String.format(Locale.ENGLISH, "geo:0:0?q=", location.getLatitude(), location.getLongitude()) + Uri.encode(String.format(Locale.ENGLISH, "%s@%f,%f", name, getLocation().getLatitude(), getLocation().getLongitude()), "UTF-8"));
    }

    public String getDistanceAsString(final Context context, final Location location) {
        final float dist = location.distanceTo(getLocation());
        return dist >= 1000 ? context.getString(R.string.f_distance_km, dist / 1000) : context.getString(R.string.f_distance_m, dist);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.icon);
        dest.writeString(this.name);
        dest.writeFloat(this.rating);
        dest.writeString(this.vicinity);
        dest.writeParcelable(this.geometry, flags);
        dest.writeList(this.photos);
        dest.writeString(this.placeId);
    }
}
