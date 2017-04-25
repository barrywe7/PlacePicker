package com.barryirvine.tide.placepicker.model;


import android.support.annotation.StringRes;

import com.barryirvine.tide.placepicker.R;

public enum BarTab {
    BARS(R.string.bars),
    MAP(R.string.map);

    @StringRes
    private final int mDescription;

    BarTab(@StringRes final int description) {
        mDescription = description;
    }

    @StringRes
    public int getDescription() {
        return mDescription;
    }
}
