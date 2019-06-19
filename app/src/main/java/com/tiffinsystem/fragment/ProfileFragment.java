package com.tiffinsystem.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiffinsystem.R;
import com.tiffinsystem.preferencemanager.PreferenceStorage;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView userName, userEmail, userPhone;

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
        setUserRecord();
    }

    private void setUserRecord() {
        PreferenceStorage preferenceStorage = new PreferenceStorage(getActivity());

        String phoneNumber = preferenceStorage.getMobileNumber();
        userPhone.setText(phoneNumber);
    }
}
