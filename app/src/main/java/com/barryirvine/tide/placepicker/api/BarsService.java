package com.barryirvine.tide.placepicker.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BarsService {

    private static volatile BarsAPI sBarsAPI;

    public static BarsAPI get() {
        if (sBarsAPI == null) {
            synchronized (BarsService.class) {
                if (sBarsAPI == null) {
                    final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    final OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
                    sBarsAPI = new Retrofit.Builder()
                            .client(client)
                            .baseUrl(BarsAPI.BASE_URL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()))
                            .build()
                            .create(BarsAPI.class);


                }
            }
        }
        return sBarsAPI;
    }
}
