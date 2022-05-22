package com.example.daftarpencarianorang.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.daftarpencarianorang.BuildConfig;
import com.example.daftarpencarianorang.Model.DataItemMissing;
import com.example.daftarpencarianorang.Model.MissingPersonData;
import com.example.daftarpencarianorang.R;

import static com.example.daftarpencarianorang.Constants.Parameters.DATA_TRANSFER_KEY;

public class PhotosFragment extends Fragment {
    View mView;
    RecyclerView mRecyclerView;
    MissingPersonData mPersonData;
    DataItemMissing dataItemMissing;
    ImageView imageView;

    public PhotosFragment(DataItemMissing dataItemMissing) {
        this.dataItemMissing = dataItemMissing;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPersonData = (MissingPersonData) getActivity().getIntent().getSerializableExtra(DATA_TRANSFER_KEY);
        mView = inflater.inflate(R.layout.fragment_photos, container, false);
        imageView = mView.findViewById(R.id.img);

        Glide.with(requireActivity()).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.ic_person)).load(BuildConfig.BASE_URL+"images/" +dataItemMissing.getPhoto()).into(imageView);
        return mView;
    }
}