package com.tiffinsystem.preferencemanager;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferenceStorage {
    private SharedPreferences sharedPreferences;
     public PreferenceStorage(Context context) {
        sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    }
    public  void saveMobileNumber(String mobileNumber)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mobileNumber", mobileNumber);
        editor.apply();
    }
    public boolean checkSharedPrefValue()
    {
        SharedPreferences prefs = sharedPreferences;
        String storeMobileNumber = prefs.getString("mobileNumber", null);
        if (storeMobileNumber != null) {
            return true;
        }
        return false;
    }
    public String getMobileNumber()
    {
        SharedPreferences prefs = sharedPreferences;
        String storeMobileNumber = prefs.getString("mobileNumber", null);
        if (storeMobileNumber != null) {
            return storeMobileNumber;
        }
        return null;
    }
}
