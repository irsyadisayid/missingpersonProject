package com.example.daftarpencarianorang.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daftarpencarianorang.Model.DataItemMissing;
import com.example.daftarpencarianorang.Model.MissingPersonData;
import com.example.daftarpencarianorang.R;

public class DetailFragment extends Fragment {
    TextView   height, hair, skin, eye, desc, infol;
    private  DataItemMissing dataItemMissing;
    View mView;
    public static MissingPersonData mPersonData;

    public DetailFragment(DataItemMissing dataItemMissing) {
        this.dataItemMissing = dataItemMissing;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_detail, container, false);

        height= mView.findViewById(R.id.detail_heigth);
        hair= mView.findViewById(R.id.detail_hair);
        skin= mView.findViewById(R.id.detail_skin);
        eye= mView.findViewById(R.id.detail_eye);
        desc= mView.findViewById(R.id.detail_desc);
        infol= mView.findViewById(R.id.detail_infol);

        Log.i("TAGG", "onCreateView: "+ dataItemMissing.getTb());

        height.setText(String.format(getString(R.string.tinggi) + "\n%s", dataItemMissing.getTb()));
        hair.setText(String.format(getString(R.string.rambut) + "\n%s", dataItemMissing.getRambut()));
        skin.setText(String.format(getString(R.string.kulit) + "\n%s", dataItemMissing.getKulit()));
        eye.setText(String.format(getString(R.string.mata) + "\n%s", dataItemMissing.getMata()));
        desc.setText(String.format(getString(R.string.cirik) + "\n%s", dataItemMissing.getCirik()));
        infol.setText(String.format(getString(R.string.infol) + "\n%s", dataItemMissing.getInfot()));

        return mView;
    }
}
