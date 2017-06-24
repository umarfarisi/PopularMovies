package com.example.myapplication.api.core;

import com.example.myapplication.utils.ApiUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Muhammad Umar Farisi
 * @created 24/06/2017
 */
public class ApiHelper {

    private static Retrofit retrofit;

    private static Retrofit getRetrofit(){
        if(retrofit == null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .setDateFormat("yyyy-MM-dd")
                    .create();
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(ApiUtils.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static <T> T service(Class<T> service){
        return getRetrofit().create(service);
    }

}
