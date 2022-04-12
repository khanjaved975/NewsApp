package com.javedkhan.currencyapp.android.data.services.retrofit;


import retrofit2.Response;

public interface RetrofitResponseCallback {
    void success(Response response);
    void error(Response response);
    void failure(String message);
}
