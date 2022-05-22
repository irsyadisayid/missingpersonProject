package com.example.daftarpencarianorang.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.daftarpencarianorang.Fragment.DetailFragment;
import com.example.daftarpencarianorang.Fragment.PhotosFragment;
import com.example.daftarpencarianorang.Model.DataItemMissing;

public class DetailTabAdapter extends FragmentPagerAdapter {
    Context mContext;
    int totalTabs;
    DataItemMissing dataItemMissing;

    public DetailTabAdapter(Context context, @NonNull FragmentManager fm, int totalTabs,DataItemMissing dataItemMissing) {
        super(fm);
        mContext = context;
        this.totalTabs = totalTabs;
        this.dataItemMissing = dataItemMissing;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DetailFragment(dataItemMissing);
            case 1:
                return new PhotosFragment(dataItemMissing);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
