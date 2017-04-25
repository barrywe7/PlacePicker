package com.barryirvine.tide.placepicker.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlaceResponse {

    @SerializedName("results")
    private ArrayList<Place> mPlaces;

    public ArrayList<Place> getPlaces() {
        return mPlaces;
    }
}
