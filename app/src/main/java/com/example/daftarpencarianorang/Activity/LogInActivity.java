package com.example.daftarpencarianorang.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.daftarpencarianorang.Constants.DataManager;
import com.example.daftarpencarianorang.R;
import com.example.daftarpencarianorang.Remote.Client;
import com.example.daftarpencarianorang.Remote.ResponseLogin;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class LogInActivity extends AppCompatActivity {
    private TextInputEditText mEmail, mPassword;

    private Button logInBtn;
    private TextView signUpText;
    private ProgressBar mProgressBar;
    private Switch mSwitch;
    private Client client;
    protected DataManager dataManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        dataManager = new DataManager(this);

        initViews();
        if (dataManager.getLoggedInMode()) {
            finish();
            startActivity(new Intent(LogInActivity.this, MainActivity.class));
        }

        client = new Client();

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    mPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
            }
        });

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkData()) {
                    LogInUser(mEmail.getText().toString(), mPassword.getText().toString());
                    mProgressBar.setVisibility(View.VISIBLE);
                    hideSoftInputFromWindow();
                } else {
                    Toast.makeText(LogInActivity.this, "please write your email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkStoragePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkData() {
        if (!mEmail.getText().toString().trim().equals("") && !mPassword.getText().toString().trim().equals(""))
            return true;
        else return false;
    }

    private void hideSoftInputFromWindow() {
// Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!checkStoragePermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "required access storage", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Toast.makeText(this, "required access storage", Toast.LENGTH_SHORT).show();
                    }
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }
            }
        }
    }

    private void LogInUser(String email, String password) {
        client.getAPI().login(email, password).enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()) {
                    dataManager.putUser(response.body().getData());
                    dataManager.setLoggedIn();
                    finish();
                    startActivity(new Intent(LogInActivity.this, MainActivity.class));
                    Toast.makeText(LogInActivity.this, "sukses login", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LogInActivity.this, "gagal login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(LogInActivity.this, "gagal login", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        mSwitch = findViewById(R.id.switch12);

        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);

        mEmail = findViewById(R.id.login_email);
        mPassword = findViewById(R.id.login_password);

        signUpText = findViewById(R.id.signup_text);
        logInBtn = findViewById(R.id.login_btn);
    }

}