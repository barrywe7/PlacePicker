package com.barryirvine.tide.placepicker.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.barryirvine.tide.placepicker.R;
import com.barryirvine.tide.placepicker.model.Photo;
import com.barryirvine.tide.placepicker.model.Place;

/**
 * View Model for {@link Place} Remember to use the {@link Bindable} annotation for all getters and to do
 * {@link #notifyPropertyChanged(int)} after a value is set in a setter.
 */
public class PlaceViewModel extends BaseObservable {

    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/photo";
    private final Place mPlace;
    private final Context mContext;
    private final Location mLocation;


    public PlaceViewModel(@NonNull final Context context, @NonNull final Place place, @NonNull final Location location) {
        mContext = context;
        mPlace = place;
        mLocation = location;
    }

    @Bindable
    public String getIcon() {
        return mPlace.getIcon();
    }

    @Bindable
    public String getName() {
        return mPlace.getName();
    }

    @Bindable
    public String getVicinity() {
        return mPlace.getVicinity();
    }

    @Bindable
    public float getRating() {
        return mPlace.getRating();
    }

    @Bindable
    public String getRatingAsString() {
        return String.valueOf(mPlace.getRating());
    }

    @Bindable
    public String getFirstPhoto() {
        final Photo photo = mPlace.getPhotos() != null && mPlace.getPhotos().size() > 0 ? mPlace.getPhotos().get(0) : null;
        return photo == null ? "" : Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("key", mContext.getString(R.string.web_api_key))
                .appendQueryParameter("photoreference", photo.getPhotoReference())
                .appendQueryParameter("maxwidth", String.valueOf(mContext.getResources().getDimensionPixelSize(R.dimen.thumbnail_size)))
                .build().toString();
    }

    @Bindable
    public String getDistanceAsString() {
        final float dist = mLocation.distanceTo(mPlace.getLocation());
        return "  \u2022 " + (dist >= 1000 ? mContext.getString(R.string.f_distance_km, dist / 1000) : mContext.getString(R.string.f_distance_m, dist));
    }

    public void onClick() {
        final Intent intent = new Intent(Intent.ACTION_VIEW, mPlace.getLocationUri());
        intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
        }
    }


}
