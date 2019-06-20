package com.tiffinsystem;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.tiffinsystem.adapter.BannerImgAdapter;
import com.tiffinsystem.adapter.PageAdapter;
import com.tiffinsystem.fragment.AddressFragment;
import com.tiffinsystem.fragment.CartFragment;
import com.tiffinsystem.fragment.ContactUsFragment;
import com.tiffinsystem.fragment.HomeFragment;
import com.tiffinsystem.fragment.OrderFragment;
import com.tiffinsystem.fragment.ProfileFragment;
import com.tiffinsystem.fragment.SearchFragment;
import com.tiffinsystem.fragment.TiffinFragment;
import com.tiffinsystem.modal.BannerImgModal;
import com.tiffinsystem.network.NetworkClient;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

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

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recyclerBanner;
    Retrofit retrofit;
    NetworkClient api;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    FrameLayout frameLayout;
    Fragment fragment;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    FragmentTransaction fragmentTransaction;
    Boolean isTabSelected=false;
    Boolean isNavSelected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        setId();
        setSupportActionBar(toolbar);
        /* adding the tabLayout*/
        addTabDynamically();
        setUpTabIcons();
        /*adding the listener for the tabLayout*/
        listenerOnTab();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        displaySelectedScreen(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(this);
        
    }

    private void setUpTabIcons() {
        int tabIcons[] = {
                R.drawable.restaurant,
                R.drawable.lunchbox,
                R.drawable.ic_search,
                R.drawable.ic_shopping_cart
        };
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }
    private void addTabDynamically() {
        tabLayout.addTab(tabLayout.newTab().setText("Restaurant"));
        tabLayout.addTab(tabLayout.newTab().setText("Tiffin"));
        tabLayout.addTab(tabLayout.newTab().setText("Search"));
        tabLayout.addTab(tabLayout.newTab().setText("Cart"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void displaySelectedScreen(int itemViewId) {
        switch (itemViewId)
        {
            case R.id.nav_home:
                fragment = new HomeFragment();
                tabLayout.setVisibility(View.VISIBLE);

                break;
            case R.id.nav_profile:
                isNavSelected = true;
                tabLayout.setVisibility(View.GONE);
                fragment = new ProfileFragment();
                break;
            case R.id.nav_orders:
                tabLayout.setVisibility(View.GONE);
                fragment = new OrderFragment();
                break;
            case R.id.nav_address:
                tabLayout.setVisibility(View.GONE);
                fragment = new AddressFragment();
                break;
            case R.id.nav_contactus:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9999999999"));
                startActivity(intent);
                /*tabLayout.setVisibility(View.GONE);
                fragment = new ContactUsFragment();*/
                break;
                default:
                    break;
        }
        //replacing the fragment
        if (fragment != null) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fl_container, fragment);
            fragmentTransaction.commit();
        }
        drawer.closeDrawer(GravityCompat.START);
    }
    private void listenerOnTab() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                int tabPosition = tab.getPosition();
                // for doing tablayout position on a first index when we select the navigation item
             /*   if(isNavSelected)
                {
                    tabPosition = 0;
                }*/
                switch (tabPosition)
                {
                    case 0:
                        fragment = new HomeFragment();
                        toolbar.setTitle("Home");
                       // isNavSelected = false;
                        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#DD2C00"), PorterDuff.Mode.SRC_IN);
                        break;
                    case 1:
                        toolbar.setTitle("Tiffin");
                        //isNavSelected = false;
                        fragment = new TiffinFragment();
                        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#DD2C00"), PorterDuff.Mode.SRC_IN);
                        break;
                    case 2:
                        fragment = new SearchFragment();
                        toolbar.setTitle("Search");
                       // isNavSelected = false;
                        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#DD2C00"), PorterDuff.Mode.SRC_IN);
                        break;
                    case 3:
                        fragment = new CartFragment();
                        toolbar.setTitle("Cart");
                       // isNavSelected = false;
                        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#DD2C00"), PorterDuff.Mode.SRC_IN);
                        break;
                    default:
                        break;

                }

                //replacing the fragment
                if (fragment != null) {
                     fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fl_container, fragment);
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                switch (tabPosition)
                {
                    case 0:
                        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#757575"), PorterDuff.Mode.SRC_IN);
                        break;
                    case 1:
                        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#757575"), PorterDuff.Mode.SRC_IN);
                        break;
                    case 2:
                        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#757575"), PorterDuff.Mode.SRC_IN);
                        break;
                    case 3:
                        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#757575"), PorterDuff.Mode.SRC_IN);
                        break;
                    case 4:
                        tabLayout.getTabAt(4).getIcon().setColorFilter(Color.parseColor("#757575"), PorterDuff.Mode.SRC_IN);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void setId() {
        toolbar = findViewById(R.id.toolbar);
        drawer  = findViewById(R.id.drawer_layout);
        frameLayout  = findViewById(R.id.fl_container);
        navigationView = findViewById(R.id.nav_view);
      //  recyclerBanner = findViewById(R.id.recycles_banner);
        retrofit = new Retrofit.Builder().baseUrl(NetworkClient.BASE_URL1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(NetworkClient.class);
        viewPager = findViewById(R.id.viewpager_restaurant);
        tabLayout = findViewById(R.id.tabLayout);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displaySelectedScreen(id);

    /*    if (id == R.id.nav_home) {
            Toast.makeText(this, "nav_profile", Toast.LENGTH_SHORT).show();
            // Handle the camera action
        } else if (id == R.id.nav_profile) {
            Toast.makeText(this, "nav_profile", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_orders) {

        } else if (id == R.id.nav_address) {

        } else if (id == R.id.nav_contactus) {

        }*/

        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/
        return true;
    }


}
