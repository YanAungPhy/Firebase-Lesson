package com.yap.testapplication.network;


import com.yap.testapplication.modal.ResponseStatus;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitStatusAPI {

    //http://api.dg8923.com/api/dialog/get
    //https://run.mocky.io/v3/  90fea295-8b4d-4e7c-be1e-f0788188102f

    @GET("api/dialog/get")
    Call<ResponseStatus> getData();
}
