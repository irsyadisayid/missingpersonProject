package com.example.daftarpencarianorang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.daftarpencarianorang.BuildConfig;
import com.example.daftarpencarianorang.R;
import com.example.daftarpencarianorang.Model.DataItemMissing;

import java.util.List;
import java.util.Objects;

public class MissingListAdapter extends RecyclerView.Adapter<MissingListAdapter.MyViewHolder> {
    Context mContext;
    List<DataItemMissing> mList;
    MissingListListener listener;
    private int type;

    public MissingListAdapter(Context context,MissingListListener listener,int type) {
        mContext = context;
        this.listener = listener;
        this.type = type;
    }

    public void setData(List<DataItemMissing> list){
        mList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.missing_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataItemMissing data = mList.get(position);

        if(type == 0){
            holder.delete.setVisibility(View.VISIBLE);
            holder.edit.setVisibility(View.VISIBLE);

            holder.delete.setOnClickListener(view -> listener.delete(data.getId()));
            holder.edit.setOnClickListener(view -> listener.edit(data));
        }

        holder.mName.setText(data.getNama());
        holder.mBorn.setText(String.format("Tanggal Lahir : %s", data.getTtl()));
        holder.mMissingDate.setText(String.format("Tanggal Hilang : %s", data.getTglhilang()));
        holder.mGender.setText(String.format("Jenis Kelamin: %s", data.getJekel()));


        Glide.with(mContext).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.ic_person)).load(BuildConfig.BASE_URL+"images/" +data.getPhoto()).into(holder.mMissingImage);

        holder.detailBtn.setOnClickListener(view -> listener.onClick(data));

        holder.share.setOnClickListener(view -> listener.share(data));
    }

    @Override
    public int getItemCount() {
        if(mList != null){
            return mList.size();
        }

        return 0;

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mMissingImage,share;
        public TextView mName;
        public TextView mAge;
        public TextView mGender;
        public TextView mMissingDate;
        public TextView mBorn;
        public Button detailBtn,delete,edit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mMissingImage = itemView.findViewById(R.id.missing_person_image);
            mName = itemView.findViewById(R.id.missing_name);
            mAge = itemView.findViewById(R.id.age);
            mBorn = itemView.findViewById(R.id.born);
            mGender = itemView.findViewById(R.id.gender);
            mMissingDate = itemView.findViewById(R.id.missing_date);
            detailBtn = itemView.findViewById(R.id.see_detail);
            delete = itemView.findViewById(R.id.hapus);
            share = itemView.findViewById(R.id.imgShare);
            edit = itemView.findViewById(R.id.edit);
        }
    }
    public interface MissingListListener{
        void onClick(DataItemMissing data);
        void share(DataItemMissing data);
        void edit(DataItemMissing data);
        void delete(int id);
    }
}
