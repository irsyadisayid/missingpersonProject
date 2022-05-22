package com.example.daftarpencarianorang.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daftarpencarianorang.R;
import com.example.daftarpencarianorang.Remote.Client;
import com.example.daftarpencarianorang.Remote.ResponseRegister;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    TextInputEditText mNama, mTtl, mPekerjaan, mAlamat, mNohp, mEmail, mPassword, mConfermPassword;
    AutoCompleteTextView mAgama, mKewarganegaraan;
    Button signUpBtn;
    TextView logInText;
    TextWatcher mTextWatcher;
    ProgressBar mProgressBar;
    private Client client;

    private FirebaseAuth mAuth;
    private Switch mSwitch;
    private String agama, kwn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        client = new Client();

        initViews();
        logInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftInputFromWindow();
                if (checkData()) {
                    SignUpUser(mNama.getText().toString(), mTtl.getText().toString(), agama,
                            mPekerjaan.getText().toString(), kwn, mAlamat.getText().toString(),
                            mNohp.getText().toString(), mEmail.getText().toString(), mPassword.getText().toString());
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mConfermPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    mPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mConfermPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mEmail.setError(null);
                mPassword.setError(null);
                mConfermPassword.setError(null);
            }
        };

        mEmail.addTextChangedListener(mTextWatcher);
        mPassword.addTextChangedListener(mTextWatcher);
        mConfermPassword.addTextChangedListener(mTextWatcher);
    }

    private void hideSoftInputFromWindow() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private boolean checkData() {
        if (!mEmail.getText().toString().trim().equals("") && !mPassword.getText().toString().trim().equals("")) {
            if (mPassword.getText().toString().equals(mConfermPassword.getText().toString())) {
                return true;
            } else {
                mConfermPassword.setError("Password Anda Tidak Sama!");
                return false;
            }

        } else {
            if (mEmail.getText().toString().equals("")) {
                mEmail.setError("Tulis Email Anda");
            }
            if (mPassword.getText().toString().equals("")) {
                mPassword.setError("Tulis Password Anda");
            }
            if (mConfermPassword.getText().toString().equals("")) {
                mConfermPassword.setError("Tulis Konfirmasi Password Anda");
            }
            if (mNama.getText().toString().equals("")) {
                mConfermPassword.setError("Tulis Nama Anda");
            }
            return false;
        }
    }

    private void SignUpUser(String nama, String ttl, String agama, String pekerjaan,
                            String kewarganegaraan, String alamat, String nohp, String email, String password) {
        client.getAPI().register(nama, ttl, agama, pekerjaan, kewarganegaraan, alamat, nohp, email, password).enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Sukses Daftar", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                }
                Toast.makeText(SignUpActivity.this, "Gagal Daftar", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Gagal Daftar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            finish();
        }
    }

    public void initViews() {
        mAuth = FirebaseAuth.getInstance();

        mProgressBar = findViewById(R.id.progressBar2);
        mProgressBar.setVisibility(View.GONE);

        mEmail = findViewById(R.id.signup_email);
        mPassword = findViewById(R.id.signup_password);
        mNama = findViewById(R.id.signup_nama);
        mConfermPassword = findViewById(R.id.signup_password_confirm);
        mTtl = findViewById(R.id.signup_ttl);
        mAgama = findViewById(R.id.agama);
        mPekerjaan = findViewById(R.id.signup_pekerjaan);
        mAlamat = findViewById(R.id.signup_alamat);
        mNohp = findViewById(R.id.signup_nohp);
        mKewarganegaraan = findViewById(R.id.kwn);

        ArrayAdapter<String> agamaAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.agama));
        mAgama.setAdapter(agamaAdapter);
        mAgama.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                agama = adapterView.getItemAtPosition(i).toString();
            }
        });
        ArrayAdapter<String> kwnAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.kwn));

        mKewarganegaraan.setAdapter(kwnAdapter);
        mKewarganegaraan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) kwn = "WNI";
                if (i== 1) kwn = "WNA";
//                kwn = adapterView.getItemAtPosition(i).toString();
            }
        });

        logInText = findViewById(R.id.login_text);
        signUpBtn = findViewById(R.id.signup_btn);
        mSwitch = findViewById(R.id.switch12);
    }

}