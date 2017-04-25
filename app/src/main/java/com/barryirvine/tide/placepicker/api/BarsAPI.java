package com.barryirvine.tide.placepicker.api;


import com.barryirvine.tide.placepicker.model.PlaceResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BarsAPI {

    String BASE_URL = "https://maps.googleapis.com";

    @GET("/maps/api/place/nearbysearch/json?type=bar")
    Observable<PlaceResponse> getNearbyBars(@Query("location") final String location, @Query("radius") final Integer radius, @Query("key") final String key);

}
