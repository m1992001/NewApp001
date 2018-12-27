package com.example.newapp;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class HttpUtil {

    public static final String IP = "http://192.168.100.228:4399";

    public static void postHttpRequest(String address, String jsonData, okhttp3.Callback callback){
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json; charset=utf-8"),jsonData);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .post(requestBody)
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void getHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
