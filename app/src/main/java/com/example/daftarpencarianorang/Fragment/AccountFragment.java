package com.example.daftarpencarianorang.Fragment;

import static com.example.daftarpencarianorang.Constants.Parameters.DATABASE_ROOT_KEY;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daftarpencarianorang.Activity.LogInActivity;
import com.example.daftarpencarianorang.Adapter.MyMissingPersonListAdapter;
import com.example.daftarpencarianorang.Constants.DataManager;
import com.example.daftarpencarianorang.Model.MissingPersonData;
import com.example.daftarpencarianorang.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class AccountFragment extends Fragment {
    View mView;
    TextView userEmail, yourListTitle;
    Button logOutBtn;
    ImageView posterImg;

    DatabaseReference mReference;


    RecyclerView mRecyclerView;
    MyMissingPersonListAdapter mAdapter;
    ArrayList<MissingPersonData> mList;
    ValueEventListener mListener;
    DatabaseReference.CompletionListener mCompletionListener;
    public DataManager dataManager;
    public Gson gson;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_account, container, false);
        initViews(getContext());

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataManager.clear();
                getActivity().finish();
                startActivity(new Intent(getActivity(), LogInActivity.class));
            }
        });




        return mView;
    }

    public void initViews(Context context) {
        dataManager = new DataManager(getActivity());
        gson = new GsonBuilder().create();


        userEmail = mView.findViewById(R.id.user_email);
        userEmail.setText(dataManager.getUser().getEmail());
        yourListTitle = mView.findViewById(R.id.your_list_title);
        logOutBtn = mView.findViewById(R.id.logout_btn);

        // set RecyclerView
        mRecyclerView = mView.findViewById(R.id.recyclerView);
        mList = new ArrayList<>();
        mAdapter = new MyMissingPersonListAdapter(context, mList, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    public void deleteData(int position) {

    }
}