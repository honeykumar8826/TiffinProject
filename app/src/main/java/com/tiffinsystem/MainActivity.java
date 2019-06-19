package com.tiffinsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.tiffinsystem.adapter.BannerImgAdapter;
import com.tiffinsystem.modal.BannerImgModal;
import com.tiffinsystem.network.NetworkClient;
import com.tiffinsystem.preferencemanager.PreferenceStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton btnContinue;
    TextInputEditText etPhone;
    RelativeLayout relativeLayout;
    Retrofit retrofit;
    NetworkClient api;
    ProgressBar progressBar;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceStorage preferenceStorage = new PreferenceStorage(MainActivity.this);
        if(preferenceStorage.checkSharedPrefValue())
        {
            Intent intent = new Intent(MainActivity.this,NavigationActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            setId();
            btnContinue.setOnClickListener(this);
        }
    }

    private void setId() {
        btnContinue = findViewById(R.id.btn_continue);
        progressBar = findViewById(R.id.pg_mobile_number);
        etPhone = findViewById(R.id.et_phone);
        relativeLayout = findViewById(R.id.rl_main);
        retrofit = new Retrofit.Builder().baseUrl(NetworkClient.BASE_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(NetworkClient.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_continue:
                String phoneNumber = etPhone.getText().toString();
                if(phoneNumber.length()<10)
                {
                    Snackbar.make(relativeLayout,"Enter the Correct Number",2000).show();
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                   // Snackbar.make(relativeLayout,phoneNumber,2000).show();
                    sendOtp(phoneNumber);


                }
                break;
                default:
                    break;
        }
    }
    private void sendOtp(final String phoneNum) {
        api = retrofit.create(NetworkClient.class);
        Call<ResponseBody> call = api.getOtpNumber(phoneNum);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Log.i(TAG, "onResponse: " + response.body());

                String result = null;
                try {
                    result = response.body().string();
                    if(result !=null)
                    {
                        JSONObject jsonObject = new JSONObject(result);
                        String otpStatus = jsonObject.get("isOtpSend").toString();
                        if(otpStatus.equals("1"))
                        {
                            String otpNumber =  jsonObject.get("otp").toString();
                            Intent intent = new Intent(MainActivity.this,VerifyOtp.class);
                            intent.putExtra("otpNumber", otpNumber);
                            intent.putExtra("mobileNumber",phoneNum);
                            startActivity(intent);
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
                progressBar.setVisibility(View.GONE);
                if (t instanceof IOException) {
                    Snackbar.make(relativeLayout,"Internet Issue",2000).show();
                } else {
                    Snackbar.make(relativeLayout,"Invalid phone number",2000).show();
                }
            }
        });
    }

}

