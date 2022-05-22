package com.example.daftarpencarianorang.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.daftarpencarianorang.BuildConfig;
import com.example.daftarpencarianorang.Constants.DataManager;
import com.example.daftarpencarianorang.Model.DataItemMissing;
import com.example.daftarpencarianorang.R;
import com.example.daftarpencarianorang.Remote.Client;
import com.example.daftarpencarianorang.Remote.Responses;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.StorageTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iceteck.silicompressorr.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.daftarpencarianorang.Constants.Parameters.DATA_TRANSFER_KEY;

public class FormActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 456;
    private Button mFileChooserBtn, mUploadBtn, cancelBtn, editBtn;
    private ProgressBar mDialogProgress;
    private ImageView imageView;

    private TextInputEditText name, born, height, hair, skin, eye, spec, missingDate, infol;
    ;
    private RadioGroup mGroup;
    private Uri mImageUri;
    private Client client;

    private StorageTask mUploadTask;
    private int taskIndex = 0;

    private Dialog mAlertDialog;
    private TextView dialogText;
    public DataManager dataManager;
    public Gson gson;
    final Calendar myCalendar = Calendar.getInstance();

    public static DataItemMissing mPersonData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        dataManager = new DataManager(FormActivity.this);
        gson = new GsonBuilder().create();

        client = new Client();
        // init all views with these Ids
        initViews();

        mFileChooserBtn = findViewById(R.id.select_btn);
        mFileChooserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });


        mUploadBtn.setEnabled(false);
        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkData()) {
                    uploadFile();
//                    mAlertDialog.show();
                }
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        missingDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initViews() {
        mAlertDialog = new Dialog(FormActivity.this);
        mAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mAlertDialog.setContentView(R.layout.upload_dialogue_layout);
        mAlertDialog.setTitle("Title");

        mAlertDialog.setCancelable(false);
        imageView = findViewById(R.id.photo_list);
        editBtn = findViewById(R.id.edit_btn);
        mUploadBtn = findViewById(R.id.upload_btn);

        dialogText = mAlertDialog.findViewById(R.id.dialog_text);
        mDialogProgress = mAlertDialog.findViewById(R.id.progressDialog);
        mDialogProgress.setVisibility(View.GONE);
        cancelBtn = mAlertDialog.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask.isInProgress()) {
                    mUploadTask.cancel();
                    dialogText.setText("Canceling....");
                    mDialogProgress.setVisibility(View.VISIBLE);
                }
            }
        });

        name = findViewById(R.id.msg_nama);
        born = findViewById(R.id.msg_ttl);
        height = findViewById(R.id.msg_tb);
        hair = findViewById(R.id.msg_rambut);
        skin = findViewById(R.id.msg_kulit);
        eye = findViewById(R.id.msg_mata);
        spec = findViewById(R.id.msg_cirik);
        missingDate = findViewById(R.id.msg_tglhilang);
        infol = findViewById(R.id.msg_infot);
        mGroup = findViewById(R.id.radioGroup);

        infol.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };

        missingDate.setOnClickListener(view -> new DatePickerDialog(FormActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        mPersonData = getIntent().getParcelableExtra(DATA_TRANSFER_KEY);

        if (mPersonData != null) {
            name.setText(mPersonData.getNama());
            born.setText(mPersonData.getTtl());
            height.setText(mPersonData.getTb());
            hair.setText(mPersonData.getRambut());
            skin.setText(mPersonData.getKulit());
            eye.setText(mPersonData.getMata());
            spec.setText(mPersonData.getCirik());
            missingDate.setText(mPersonData.getTglhilang());
            infol.setText(mPersonData.getInfot());
            editBtn.setVisibility(View.VISIBLE);
            mUploadBtn.setVisibility(View.GONE);

            if (mPersonData.getJekel().equals("Laki - Laki")) {
                Log.e("TAG", "initViews lk: " + mPersonData.getJekel());
                RadioButton male = findViewById(R.id.rdo_laki);
                male.setChecked(true);
            } else {
                Log.e("TAG", "initViews pr: " + mPersonData.getJekel());
                RadioButton female = findViewById(R.id.rdo_pr);
                female.setChecked(true);
            }

            if (!mPersonData.getPhoto().isEmpty()) {
                Glide.with(this).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.ic_person)).load(BuildConfig.BASE_URL + "images/" + mPersonData.getPhoto()).into(imageView);
            }

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    update(mPersonData.getId());
                }
            });
        }

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void update(int id) {
        RadioButton gender = findViewById(mGroup.getCheckedRadioButtonId());
        RequestBody nama = stringConvert(name.getText().toString());
        RequestBody idUser = stringConvert(String.valueOf(dataManager.getUser().getId()));
        RequestBody ttl = stringConvert(born.getText().toString());
        RequestBody jekel = stringConvert(gender.getText().toString());
        RequestBody tb = stringConvert(height.getText().toString());
        RequestBody rambut = stringConvert(hair.getText().toString());
        RequestBody kulit = stringConvert(skin.getText().toString());
        RequestBody mata = stringConvert(eye.getText().toString());
        RequestBody cirik = stringConvert(spec.getText().toString());
        RequestBody tglhilang = stringConvert(missingDate.getText().toString());
        RequestBody infot = stringConvert(infol.getText().toString());

        String uri = FileUtils.getPath(this, mImageUri);
        File originalFile = new File(Uri.parse(uri).getPath());

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("photo", originalFile.getName(),
                RequestBody.create(MediaType.parse("image/*"), originalFile));

        client.getAPI().editMissing(
                "Bearer " + dataManager.getUser().getJwt(), id, idUser,
                nama, ttl, jekel, tb, rambut, kulit, mata, cirik, tglhilang, infot, filePart
        ).enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FormActivity.this, "Sukses Edit", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                Toast.makeText(FormActivity.this, "Gagal Upload" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadFile() {
        RadioButton gender = findViewById(mGroup.getCheckedRadioButtonId());
        RequestBody nama = stringConvert(name.getText().toString());
        RequestBody ttl = stringConvert(born.getText().toString());
        RequestBody jekel = stringConvert(gender.getText().toString());
        RequestBody tb = stringConvert(height.getText().toString());
        RequestBody rambut = stringConvert(hair.getText().toString());
        RequestBody kulit = stringConvert(skin.getText().toString());
        RequestBody mata = stringConvert(eye.getText().toString());
        RequestBody cirik = stringConvert(spec.getText().toString());
        RequestBody tglhilang = stringConvert(missingDate.getText().toString());
        RequestBody infot = stringConvert(infol.getText().toString());

        String uri = FileUtils.getPath(this, mImageUri);
        File originalFile = new File(Uri.parse(uri).getPath());

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("photo", originalFile.getName(),
                RequestBody.create(MediaType.parse("image/*"), originalFile));

        client.getAPI().insertMissing(
                "Bearer " + dataManager.getUser().getJwt(),
                nama, ttl, jekel, tb, rambut, kulit, mata, cirik, tglhilang, infot, filePart
        ).enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FormActivity.this, "Sukses Upload", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FormActivity.this, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                Toast.makeText(FormActivity.this, "Gagal Upload" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && data != null && resultCode == RESULT_OK && data.getData() != null) {
            mImageUri = data.getData();
            Log.e("TAG", "onActivityResult: " + mImageUri);
            imageView.setImageURI(mImageUri);
            mUploadBtn.setEnabled(true);
        }
    }

    private boolean checkData() {
        int x = 0;

        if (name.getText().toString().trim().equals("")) {
            name.setError("Silahkan Tulis Nama");
            x++;
        }
        if (born.getText().toString().trim().equals("")) {
            born.setError("Silahkan Tulis Tempat tanggal Lahir");
            x++;
        }
        if (mGroup.getCheckedRadioButtonId() != R.id.rdo_laki && mGroup.getCheckedRadioButtonId() != R.id.rdo_pr) {
            Toast.makeText(FormActivity.this, "Silahkan Pilih jenis Kelamin!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (height.getText().toString().trim().equals("")) {
            height.setError("Silahkan Tulis Tinggi Badan");
            x++;
        }
        if (hair.getText().toString().trim().equals("")) {
            hair.setError("Silahkan Tulis Rambut");
            x++;
        }
        if (skin.getText().toString().trim().equals("")) {
            skin.setError("Silahkan Tulis Kulit");
            x++;
        }
        if (eye.getText().toString().trim().equals("")) {
            eye.setError("Silahkan Tulis Kulit");
            x++;
        }
        if (spec.getText().toString().trim().equals("")) {
            spec.setError("Silahkan Tulis Kulit");
            x++;
        }
        if (missingDate.getText().toString().trim().equals("")) {
            missingDate.setError("Silahkan Tulis Tanggal Kehilangan");
            x++;
        }
        if (infol.getText().toString().trim().equals("")) {
            infol.setError("Silahkan Tulis Deskripsi");
            x++;
        }
        if (x > 0) {
            return false;
        } else {
            return true;
        }
    }

    public RequestBody stringConvert(String data) {
        RequestBody body =
                RequestBody.create(MediaType.parse("text/plain"), data);

        return body;
    }

}