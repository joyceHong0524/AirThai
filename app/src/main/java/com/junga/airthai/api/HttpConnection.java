package com.junga.airthai.api;

import androidx.annotation.NonNull;

import android.util.Log;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpConnection {
    private static final String TAG = "HttpConnection";
    private OkHttpClient client;
    private static HttpConnection instance = new HttpConnection();

    private static final String BASE_URL = "https://api.waqi.info/feed/";
    private static final String TOKEN = "f5cc1650f839e43c9f5125a2cd9ed79beb62e83e";

    public static HttpConnection getInstance() {
        return instance;
    }

    private HttpConnection() {
        this.client = new OkHttpClient();
    }

    //    ** Request to Web Server**
    public void requestWebServer(@NonNull String cityName, Callback callback) {

        cityName = cityName.replaceAll(" ", "");
        cityName = cityName.toLowerCase();
        String request_url = BASE_URL + cityName + "/?token=" + TOKEN;
        Log.d(TAG, "requestWebServer: " + request_url);
        Log.d(TAG, "body" + request_url);
        Request request = new Request.Builder()
                .url(request_url)
                .build();


        client.newCall(request).enqueue(callback);
    }
}
