package com.example.daftarpencarianorang.Constants;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.daftarpencarianorang.Activity.MainActivity;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE=0;

    private static String ISLOGIN = "islogin";
    private static String PREFNAME = "prefname";

    public static String USERNAME= "username";
    public static String PASSWORD= "password";

    public SessionManagement(SharedPreferences sharedPreferences, SharedPreferences.Editor editor, Context context, int PRIVATE_MODE) {
        this.sharedPreferences = context.getSharedPreferences(PREFNAME,PRIVATE_MODE);
        this.editor = sharedPreferences.edit();
        this.context = context;
        this.PRIVATE_MODE = PRIVATE_MODE;
    }

    private void simpanSession(String username,String password){
        editor.putBoolean(ISLOGIN,true);
        editor.putString(USERNAME,username);
        editor.putString(PASSWORD,password);
        editor.commit();
    }

    public void sudahLogin(){
        if (!this.islogin()){
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }
    }

    private boolean islogin() {
        return sharedPreferences.getBoolean(ISLOGIN,false);
    }
}
