package com.example.daftarpencarianorang.Constants;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.daftarpencarianorang.Remote.Data;


import static android.content.Context.MODE_PRIVATE;

public class DataManager {
    SharedPrefsHelper mSharedPrefsHelper;
    private SharedPreferences mSharedPreferencesReferrer;
    public static final String MY_PREFS_REFERRER = "MY_PREFS_REFERRER";
    public static final String NOTIF = "NOTIF";

    public DataManager(Context context) {
        mSharedPrefsHelper = new SharedPrefsHelper(context);
        mSharedPreferencesReferrer = context.getSharedPreferences(MY_PREFS_REFERRER, MODE_PRIVATE);
    }

    public void clear() {
        mSharedPrefsHelper.clear();
    }

    public void putUser(Data user) {
        mSharedPrefsHelper.putUser(user);
    }

    public Data getUser() {
        return mSharedPrefsHelper.getUser();
    }

    public void setLoggedIn() {
        mSharedPrefsHelper.setLoggedInMode(true);
    }

    public Boolean getLoggedInMode() {
        return mSharedPrefsHelper.getLoggedInMode();
    }

    public void setNotif(int notif){
        mSharedPreferencesReferrer.edit().putInt(NOTIF, notif).apply();
    }

    public int getNotif(){
        return mSharedPreferencesReferrer.getInt(NOTIF,0);
    }


}
