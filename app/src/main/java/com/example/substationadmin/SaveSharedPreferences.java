package com.example.substationadmin;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreferences {
    //    static final String PREF_USER_NAME= "username";
    static final String PREF_EMAIL= "email";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }

    public  void setEmail(Context ctx, String email)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_EMAIL, email);
        editor.commit();
    }

    public String getEmail(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_EMAIL, "");
    }
}
