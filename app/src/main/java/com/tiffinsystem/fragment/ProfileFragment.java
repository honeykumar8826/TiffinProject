package com.tiffinsystem.fragment;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tiffinsystem.R;
import com.tiffinsystem.preferencemanager.PreferenceStorage;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView userName, userEmail, userPhone;
    ImageView imgName, imgEmail;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Profile");

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userName = view.findViewById(R.id.tv_name);
        userEmail = view.findViewById(R.id.tv_email);
        userPhone = view.findViewById(R.id.tv_phone);
        imgName = view.findViewById(R.id.img_edit_name);
        imgEmail = view.findViewById(R.id.img_edit_email);
        setUserRecord();
        imgName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserProile();
            }
        });
        imgEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserProile();

            }
        });
    }

    private void updateUserProile() {
        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.edit_profile_layout, null);
        final EditText etUsername = alertLayout.findViewById(R.id.et_name);
        final EditText etAge = alertLayout.findViewById(R.id.et_email);
        final Button btnCancel = alertLayout.findViewById(R.id.btn_cancel);
        final Button btnOk = alertLayout.findViewById(R.id.btn_ok);
        final AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(),R.style.AlertBuilderTheme));
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etUsername.getText().toString();
                String email = etAge.getText().toString();

                Toast.makeText(getActivity(), "data Stored Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.setCancelable(true);
            }
        });
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        alert.show();
    }

    private void setUserRecord() {
        PreferenceStorage preferenceStorage = new PreferenceStorage(getActivity());
        String phoneNumber = preferenceStorage.getMobileNumber();
        userPhone.setText(phoneNumber);
    }


}
