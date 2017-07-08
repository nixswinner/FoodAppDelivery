package com.tergech.nixon.foodappdeliver;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Tonui on 6/27/2017.
 */

public class SaveSharedPreference {

    static final String PREF_USER_NAME= "username";
    static final String PREF_BMR= "BMR";
    static final String PREF_GENDER= "username";
    static final String PREF_PHONE_NUMBER= "phone_number";


    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static void setBMR(Context ctx, String BMR,String Gender)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_BMR, BMR);
        editor.commit();
    }
    public static void setPhoneNumber(Context ctx, String phone)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_PHONE_NUMBER, phone);
        editor.commit();
    }
    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static String getPhoneNumber(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_PHONE_NUMBER, "");
    }

    public static String getBMR(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_BMR, "");
    }

    public static void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }
}
