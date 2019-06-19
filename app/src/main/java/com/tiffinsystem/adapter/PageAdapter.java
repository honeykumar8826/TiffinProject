package com.tiffinsystem.adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.tiffinsystem.fragment.CartFragment;
import com.tiffinsystem.fragment.HomeFragment;
import com.tiffinsystem.fragment.SearchFragment;
import com.tiffinsystem.fragment.TiffinFragment;

public class PageAdapter  extends FragmentPagerAdapter {
    private int tabNum;
    public static String TAG ="Adapter";
    public PageAdapter(FragmentManager fm, int tabNum) {
        super(fm);
        this.tabNum = tabNum;
    }
    @Override
    public Fragment getItem(int position) {
        Log.i(TAG, "getItem: ");
        switch (position)
        {
            case 0: return new HomeFragment();
            case 1: return new TiffinFragment();
            case 2: return new SearchFragment();
            case 3: return new CartFragment();
            default:return  null;
        }
    }
    @Override
    public int getCount() {
        Log.i(TAG, "getCount: ");
        return tabNum;
    }
}
