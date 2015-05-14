
package com.wudebin.bicyclerental.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtil {

    public static String readPreference(Context context, String key, String defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String refreshTime = sharedPreferences.getString(key, defaultValue);
        // if (key.equals(ConstantPref.USERPWD)) {
        // try {
        // refreshTime = Crypt.decrypt("key", refreshTime);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }
        return refreshTime;
    }

    public static void writePreference(Context context, String key, String value) {
        // if (key.equals(ConstantPref.USERPWD)) {
        // try {
        // value = Crypt.encrypt("key", value);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static int readPreferenceInt(Context context, String key, int defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        int refreshTime = sharedPreferences.getInt(key, defaultValue);
        return refreshTime;
    }

    public static void writePreferenceInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static boolean readPreferenceBool(Context context, String key, boolean defaultValue) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        boolean refreshTime = sharedPreferences.getBoolean(key, defaultValue);
        return refreshTime;
    }

    public static void writePreferenceBool(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String encrypt(String value) {
        try {
            value = Crypt.encrypt("key", value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String decrypt(String value) {
        try {
            value = Crypt.decrypt("key", value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
