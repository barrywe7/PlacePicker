package com.barryirvine.tide.placepicker.dagger.component;


import com.barryirvine.tide.placepicker.dagger.module.AppModule;
import com.barryirvine.tide.placepicker.dagger.module.NetModule;
import com.barryirvine.tide.placepicker.ui.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(final MainActivity activity);
}
