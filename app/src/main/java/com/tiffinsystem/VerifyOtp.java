package com.tiffinsystem;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.tiffinsystem.preferencemanager.PreferenceStorage;


public class VerifyOtp extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    ProgressBar progressBar;
    AppCompatButton btnVerify;
    TextInputEditText etOtp;
    LinearLayout linearLayout;
    String otpNumber,mobileNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        setInItId();
        setToolbar();
        getOtpFromIntent();

    }

    private void getOtpFromIntent() {
        otpNumber = getIntent().getStringExtra("otpNumber");
        mobileNumber = getIntent().getStringExtra("mobileNumber");

    }

    private void setInItId() {

        toolbar = findViewById(R.id.customToolBar);
        etOtp = findViewById(R.id.et_otp_number);
        btnVerify  = findViewById(R.id.btn_verify_otp);
        progressBar = findViewById(R.id.pg_verify_otp);
        linearLayout = findViewById(R.id.ll_otp_verify_main);
        btnVerify.setOnClickListener(this);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_verify_otp:
                progressBar.setVisibility(View.VISIBLE);
                verifyOtp();
                break;
                default:
                    break;
        }
    }

    private void verifyOtp() {

        String enterOtp = etOtp.getText().toString();
        if(enterOtp.equals(""))
        {
            progressBar.setVisibility(View.GONE);
            Snackbar.make(linearLayout,"Enter the  Otp",2000).show();
        }
        else {
            if(enterOtp.equals(otpNumber))
            {
                PreferenceStorage preferenceStorage = new PreferenceStorage(VerifyOtp.this);
                preferenceStorage.saveMobileNumber(mobileNumber);
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(VerifyOtp.this,NavigationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            else
            {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(linearLayout,"Enter the Correct Otp",2000).show();
            }
        }



    }
    public void saveMobileNumber(String mobileNumber)
    {
        SharedPreferences.Editor editor = getSharedPreferences("userInfo", MODE_PRIVATE).edit();
        editor.putString("mobileNumber", mobileNumber);
        editor.apply();
    }
}
