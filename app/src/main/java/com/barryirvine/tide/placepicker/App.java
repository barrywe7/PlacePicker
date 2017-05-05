package com.barryirvine.tide.placepicker;

import android.app.Application;

import com.barryirvine.tide.placepicker.api.BarsAPI;
import com.barryirvine.tide.placepicker.dagger.component.DaggerNetComponent;
import com.barryirvine.tide.placepicker.dagger.component.NetComponent;
import com.barryirvine.tide.placepicker.dagger.module.AppModule;
import com.barryirvine.tide.placepicker.dagger.module.NetModule;

public class App extends Application {

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(BarsAPI.BASE_URL))
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
