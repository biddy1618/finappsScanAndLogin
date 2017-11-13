package com.example.dauren.myapplication.retrofit;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by dauren on 1/17/17.
 */
public interface CustomService {
    @GET("WebSocket/ws/qr/{qrcode}/{login}")
    Call<ExClass> getBoolean(
            @Path("qrcode") String qrcode,
            @Path("login" ) String login
    );

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.7.15:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
