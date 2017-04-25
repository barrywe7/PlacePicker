package com.barryirvine.tide.placepicker.prefs;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

public class SharedPrefs {

    private static final String KEY_PERMISSION_PREFIX = " com.barryirvine.tide.placepicker.KEY_PERMISSION_";

    public static boolean isPermissionRequested(final Context context, final String permission) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(KEY_PERMISSION_PREFIX + permission, false);
    }

    public static void setPermissionRequested(final Context context, final String permission, final boolean requested) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(KEY_PERMISSION_PREFIX + permission, requested).apply();
    }
}
