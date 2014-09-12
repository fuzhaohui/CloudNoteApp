package com.ces.cloudnote.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 管理SharedPreferences的类，但key固定了不能修改，所以后来写了一个PrefController
 * Created by remex on 14-7-16.
 */
public class PrefUtils {

    private static SharedPreferences spf;

    public static final String APP_PREF_KEY = "app_pref_data";

    /**
     * get SharedPreferences
     * @param context
     * @return
     */
    public static SharedPreferences getSpf(Context context) {
        if (spf == null) {
            spf = context.getSharedPreferences(APP_PREF_KEY,
                    Activity.MODE_PRIVATE);
        }
        return spf;
    }

    public static boolean getBoolean(String key,boolean defValue,Context ctx){
        return getSpf(ctx).getBoolean(key, defValue);
    }

    /**
     * put boolean value into SharedPreferences
     * @param key
     * @param value
     * @param ctx
     * @return true if success
     */
    public static boolean putBoolean(String key, boolean value, Context ctx){
        return getSpf(ctx).edit().putBoolean(key,value).commit();
    }

    public static boolean putString(String key,String value,Context ctx){
        return getSpf(ctx).edit().putString(key,value).commit();
    }

    public static String getString(String key,Context ctx){
        return getSpf(ctx).getString(key,null);
    }

}