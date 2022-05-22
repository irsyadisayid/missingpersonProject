package com.example.daftarpencarianorang.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.daftarpencarianorang.Activity.LogInActivity;
import com.example.daftarpencarianorang.Constants.DataManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseFragment extends Fragment {
    public DataManager dataManager;
    public Gson gson;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataManager = new DataManager(getContext());
        gson = new GsonBuilder().create();
    }

    protected void logout(){
        dataManager.clear();
        startActivity(new Intent(getContext(), LogInActivity.class));
    }
}
