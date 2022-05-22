package com.example.daftarpencarianorang.Constants;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.daftarpencarianorang.Remote.Data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class SharedPrefsHelper {
    private static final String MY_PREFS = "MY_PREFS";

    private static final String USER = "user";

    private static final String EXPIRED_TIME = "EXPIRED_TIME";

    private SharedPreferences mSharedPreferences;
    private Gson gson;

    public SharedPrefsHelper(Context context) {
        gson = new GsonBuilder().create();
        mSharedPreferences = context.getSharedPreferences(MY_PREFS, MODE_PRIVATE);
    }

    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }

    public void putUser(Data user) {
        String jsonUser = gson.toJson(user);
        mSharedPreferences.edit().putString(USER, jsonUser).apply();
    }

    public Data getUser() {
        gson = new GsonBuilder().create();
        if (mSharedPreferences.getString(USER, null)!=null){
            return gson.fromJson(mSharedPreferences.getString(USER, null),Data.class);
        }else{
            return null;
        }
    }

    public boolean getLoggedInMode() {
        return mSharedPreferences.getBoolean("IS_LOGGED_IN", false);
    }

    public void setLoggedInMode(boolean loggedIn) {
        mSharedPreferences.edit().putBoolean("IS_LOGGED_IN", loggedIn).apply();
    }

    public void setSession(int time){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, time);
        Date expiryTime = calendar.getTime();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong(EXPIRED_TIME,expiryTime.getTime());
        editor.apply();
    }

    public boolean isSessionActive(Context context, Date currentTime){
        Date sessionExpiresAt = new Date(getExpiryDateFromPreference(context));
        return currentTime.after(sessionExpiresAt);
    }

    private long getExpiryDateFromPreference(Context context){
        return mSharedPreferences.getLong(EXPIRED_TIME, 0);
    }

}
