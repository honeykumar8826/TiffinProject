package com.tiffinsystem.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkClient {
    String BASE_URL1 = "https://newsapi.org/v2/";
    //String BASE_URL2 = "http://192.168.100.204/broccoli_api/api/user/";
    String BASE_URL2 = "http://demo.ledsolarcart.com/broccoli_api/api/user/";
    @GET("login.php")
    Call<ResponseBody> getOtpNumber(@Query("phone") String phoneNumber);
    @GET("slider_images.php")
    Call<ResponseBody> getBannerImage();
    /*@GET("top-headlines")
    Call<ResponseBody> getNews(@Query("country") String country, @Query("apiKey") String apiKey);*/
}
