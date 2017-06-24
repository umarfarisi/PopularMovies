package com.example.myapplication.api.core;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author Muhammad Umar Farisi
 * @created 24/06/2017
 */
@SuppressWarnings("ALL")
public class ApiRequest <T> implements Comparable<ApiRequest<T>> {

    private final Call<T> request;
    private final Callback<T> result;
    private int priority;

    public ApiRequest(Call<T> request, Callback<T> result, int priority) {
        this.request = request;
        this.result = result;
        this.priority = priority;
    }

    public ApiRequest(Call<T> request, Callback<T> result) {
        this.request = request;
        this.result = result;
    }

    public Call<T> getRequest() {
        return request;
    }

    public Callback<T> getResult() {
        return result;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiRequest<?> that = (ApiRequest<?>) o;


        return !request.request().url().toString().equals(that.request.request().url().toString()) &&
                result.getClass().getName().equals(that.result.getClass().getName());

    }

    @Override
    public int hashCode() {
        int result1 = request.request().url().toString().hashCode();
        result1 = 31 * result1 + result.getClass().getName().hashCode();
        return result1;
    }

    @Override
    public int compareTo(@NonNull ApiRequest<T> o) {
        return o.priority - priority;
    }

}
