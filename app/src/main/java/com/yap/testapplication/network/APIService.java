package com.yap.testapplication.network;

import com.yap.testapplication.modal.PlaceHolderListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("photos")
    Call<List<PlaceHolderListModel>>getDataList();
}
