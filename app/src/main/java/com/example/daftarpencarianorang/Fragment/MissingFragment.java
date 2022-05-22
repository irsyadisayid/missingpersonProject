package com.example.daftarpencarianorang.Fragment;


import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daftarpencarianorang.Activity.MissingPersonDetailActivity;
import com.example.daftarpencarianorang.Adapter.MissingListAdapter;
import com.example.daftarpencarianorang.BuildConfig;
import com.example.daftarpencarianorang.Constants.DataManager;
import com.example.daftarpencarianorang.Model.MissingPersonData;
import com.example.daftarpencarianorang.R;
import com.example.daftarpencarianorang.Remote.Client;
import com.example.daftarpencarianorang.Model.DataItemMissing;
import com.example.daftarpencarianorang.Remote.ResponseMissingPerson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.daftarpencarianorang.Constants.Parameters.DATA_TRANSFER_KEY;

public class MissingFragment extends Fragment implements MissingListAdapter.MissingListListener {
    View mView;
    RecyclerView mRecyclerView;
    MissingListAdapter mAdapter;
    ArrayList<MissingPersonData> mList;
    ProgressBar mProgressBar;
    private Client client;
    public DataManager dataManager;
    public Gson gson;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_missing, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataManager = new DataManager(getActivity());
        gson = new GsonBuilder().create();
        mProgressBar = mView.findViewById(R.id.loading);
        client = new Client();

        mList = new ArrayList<>();
        mRecyclerView = mView.findViewById(R.id.recyclerView);
        mAdapter = new MissingListAdapter(getActivity(), MissingFragment.this,1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);


        client.getAPI().getAllMissing("Bearer " + dataManager.getUser().getJwt()).enqueue(new Callback<ResponseMissingPerson>() {
            @Override
            public void onResponse(Call<ResponseMissingPerson> call, Response<ResponseMissingPerson> response) {
                if (response.isSuccessful()) {
                    mAdapter.setData(response.body().getData());
                    mAdapter.notifyDataSetChanged();
                    mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseMissingPerson> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    @Override
    public void onClick(DataItemMissing data) {
        Intent intent = new Intent(getActivity(), MissingPersonDetailActivity.class);
        intent.putExtra(DATA_TRANSFER_KEY, data);
        getActivity().startActivity(intent);
    }

    @Override
    public void share(DataItemMissing data) {
        new DownloadFile().execute(BuildConfig.BASE_URL_REPORT +data.getId(), "Report-"+data.getNama()+ UUID.randomUUID() +".pdf");
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "testthreepdf");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            try {

                URL url = new URL(fileUrl);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                //urlConnection.setRequestMethod("GET");
                //urlConnection.setDoOutput(true);
                urlConnection.connect();

                if (urlConnection.getResponseCode() == 200) {
                    File filePdf = new File(Environment.getExternalStorageDirectory() + "/testthreepdf/" + fileName);  // -> filename = maven.pdf
//                    Uri path = Uri.fromFile(filePdf);
                    Uri path = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName()+".fileprovider", filePdf);
                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                    whatsappIntent.setDataAndType(path, "application/pdf");
                    whatsappIntent.setPackage("com.whatsapp");
                    whatsappIntent.putExtra(Intent.EXTRA_STREAM, path);
                    try {
                        Objects.requireNonNull(getActivity()).startActivity(whatsappIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp")));
                    }

                    try{
                        startActivity(whatsappIntent);
                    }catch(ActivityNotFoundException e){
                        Log.i("TAGG", "ActivityNotFoundException: "+e.getMessage());

                        e.printStackTrace();
                    }
                }
                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);

                byte[] buffer = new byte[1024 * 1024];
                int bufferLength = 0;
                while((bufferLength = inputStream.read(buffer))>0 ){
                    fileOutputStream.write(buffer, 0, bufferLength);
                }
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.i("TAGG", "FileNotFoundException: "+e.getMessage());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.i("TAGG", "MalformedURLException: "+e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("TAGG", "IOException "+e);
            }
            return null;
        }
    }

    public void edit(DataItemMissing data) {

    }


    @Override
    public void delete(int id) {

    }
}
