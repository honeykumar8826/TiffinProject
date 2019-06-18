package com.tiffinsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton btnContinue;
    TextInputEditText etPhone;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setId();
        btnContinue.setOnClickListener(this);

    }

    private void setId() {
        btnContinue = findViewById(R.id.btn_continue);
        etPhone = findViewById(R.id.et_phone);
        relativeLayout = findViewById(R.id.rl_main);
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
                    Snackbar.make(relativeLayout,phoneNumber,2000).show();
                   /* Intent intent = new Intent(this,VerifyOtp.class);
                    startActivity(intent);*/

                }
                break;
                default:
                    break;
        }
    }
}
