package com.mondo.twitterclient.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mahmoud on 11/26/16.
 */

public class PrefsManager {
    private static final String PREFS_NAME = "TwitterClient.prefs";

    private static final String PREF_CACHE_DIRTY = "PREF_CACHE_DIRTY";

    private static PrefsManager INSTANCE = null;

    public static void init(Context context) {
        INSTANCE = new PrefsManager(context);
    }

    public static PrefsManager getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("PrefsManager must be initialized first call " +
                    "PrefsManager.init");
        }
        return INSTANCE;
    }

    private SharedPreferences mPrefs;

    private PrefsManager(Context context) {
        mPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setCacheDirty(boolean dirty) {
        mPrefs.edit().putBoolean(PREF_CACHE_DIRTY, dirty).apply();
    }

    public boolean isCacheDirty() {
        return mPrefs.getBoolean(PREF_CACHE_DIRTY, true);
    }
}
