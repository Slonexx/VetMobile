package com.example.vetmobile.DateBase;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;

    public static Retrofit getInstance(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://vetmobile.kz/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getRetrofitHttp(){
        if (retrofit == null){

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://vetmobile.kz/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService(){
        ApiService apiService = getRetrofitHttp().create(ApiService.class);

        return apiService;
    }

}
