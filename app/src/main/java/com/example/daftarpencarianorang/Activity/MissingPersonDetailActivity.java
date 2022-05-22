package com.example.daftarpencarianorang.Activity;

import static com.example.daftarpencarianorang.Constants.Parameters.DATA_TRANSFER_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.daftarpencarianorang.Adapter.DetailTabAdapter;
import com.example.daftarpencarianorang.BuildConfig;
import com.example.daftarpencarianorang.Model.DataItemMissing;
import com.example.daftarpencarianorang.Model.MissingPersonData;
import com.example.daftarpencarianorang.R;
import com.google.android.material.tabs.TabLayout;

public class MissingPersonDetailActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    ImageView mImageView;
    TextView name,born, gender, height, hair, skin, eye, spec, missing_date, infol;
    public static DataItemMissing mPersonData;
//    Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_person_detail);

        initViews();

//        sendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MissingPersonDetailActivity.this, MessageSendActivity.class);
//                intent.putExtra(DATA_TRANSFER_KEY, mPersonData);
//                startActivity(intent);
//            }
//        });


        final DetailTabAdapter adapter = new DetailTabAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount(),mPersonData);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    public void initViews() {
        mPersonData =  getIntent().getParcelableExtra(DATA_TRANSFER_KEY);

        Log.e("TAGG", "initViews: "+mPersonData.getTb() );

        mImageView = findViewById(R.id.missing_image);
        Glide.with(MissingPersonDetailActivity.this).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.ic_person)).load(BuildConfig.BASE_URL+"images/" +mPersonData.getPhoto()).into(mImageView);

        // init views with these Ids
        name = findViewById(R.id.missing_name);
        born = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        missing_date = findViewById(R.id.missing_date);

        // setData on Views
        name.setText(mPersonData.getNama());
        born.setText(String.format("Tanggal Lahir: %s", mPersonData.getTtl()));
        missing_date.setText(String.format("Tanggal Hilang: %s", mPersonData.getTglhilang()));
        gender.setText(String.format("Jenis Kelamin: %s", mPersonData.getJekel()));

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Detail"));
        tabLayout.addTab(tabLayout.newTab().setText("Photos"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

//        sendBtn = findViewById(R.id.send_btn);
    }
}