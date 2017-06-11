package com.octabytes.diana;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Tehas on 6/11/2017
 */

public class LocalDatabase extends BaseActivity {
    private SharedPreferences sharedPrefs;

    public static String originLang;
    public static String destLang;
    public static boolean firstTime;

    private boolean infoLoaded = false;

    // attempt to load local info
    public void loadInfo(Activity act) {
        sharedPrefs = act.getPreferences(MODE_PRIVATE);
        originLang = sharedPrefs.getString("origin-lang", "english");
        destLang = sharedPrefs.getString("dest-lang", "english");
        firstTime = sharedPrefs.getBoolean("first-time", true);
        infoLoaded = true;
    }

    public void saveInfo() {
        if (infoLoaded) {
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putBoolean("first-time", firstTime);
            editor.putString("origin-lang", originLang);
            editor.putString("dest-lang", destLang);
            editor.commit();
        }
    }
}
