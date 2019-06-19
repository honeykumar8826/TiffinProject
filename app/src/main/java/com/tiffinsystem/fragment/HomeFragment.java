package com.tiffinsystem.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tiffinsystem.NavigationActivity;
import com.tiffinsystem.R;
import com.tiffinsystem.adapter.BannerImgAdapter;
import com.tiffinsystem.modal.BannerImgModal;
import com.tiffinsystem.network.NetworkClient;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView recyclerBanner;
    Retrofit retrofit;
    NetworkClient api;
    private static final String TAG = "HomeFragment";
    private List<BannerImgModal> bannerImgModalList;
    private static final String API_KEY = "9e5ef71432c64196a16273c85cfb94c1";
    Context context;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Home");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerBanner = view.findViewById(R.id.recycles_banner);
        retrofit = new Retrofit.Builder().baseUrl(NetworkClient.BASE_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(NetworkClient.class);
        bannerImgModalList = new ArrayList<>();
        context  = getActivity();
        CallBannerApi();
    }

    private void CallBannerApi() {
        recyclerBanner.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        api = retrofit.create(NetworkClient.class);
        Call<ResponseBody> call = api.getBannerImage();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Log.i(TAG, "onResponse: " + response.body());


                String result = null;
                try {
                    result = response.body().string();

                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray imgArray = jsonObject.getJSONArray("records");
                    for(int i= 0;i<imgArray.length();i++)
                    {
                        JSONObject jsonImg = imgArray.getJSONObject(i);
                        String imgUrl = jsonImg.getString("image_url");
                        BannerImgModal bannerImgModal = new BannerImgModal(imgUrl);
                        bannerImgModalList.add(bannerImgModal);
                    }
                    BannerImgAdapter newsAdapter = new BannerImgAdapter(context,bannerImgModalList);
                    newsAdapter.notifyDataSetChanged();
                    recyclerBanner.setAdapter(newsAdapter);



                    Log.i(TAG, "onResponse: "+result);
                    Log.i(TAG, "onResponse: "+jsonObject);
                    String imageUrl = jsonObject.get("image_url").toString();
                    
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

                @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "Internet Issue", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Big Issue", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
