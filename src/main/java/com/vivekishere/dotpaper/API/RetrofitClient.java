package com.vivekishere.dotpaper.API;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vivekishere.dotpaper.Utility.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private Retrofit retrofit;
    private static RetrofitClient retrofitClient;
    //for configure the http client
    private OkHttpClient.Builder builder = new OkHttpClient.Builder();

    //for logging network request and response
    private HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();


    public RetrofitClient() { //unsplash
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
    }

    public static synchronized RetrofitClient getInstance(Context context){
        if (retrofitClient == null){
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }
    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
