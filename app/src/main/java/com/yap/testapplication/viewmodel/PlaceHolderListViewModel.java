package com.yap.testapplication.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yap.testapplication.modal.PlaceHolderListModel;
import com.yap.testapplication.network.APIService;
import com.yap.testapplication.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlaceHolderListViewModel extends ViewModel {

    private MutableLiveData<List<PlaceHolderListModel>> placeHolderList;

    public PlaceHolderListViewModel() {
        placeHolderList = new MutableLiveData<>();
    }

    public  MutableLiveData<List<PlaceHolderListModel>> getPlaceHolderList() {
        return placeHolderList;
    }

    public void makeApiCall() {
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<List<PlaceHolderListModel>> call = apiService.getDataList();
        call.enqueue(new Callback<List<PlaceHolderListModel>>() {
            @Override
            public void onResponse(Call<List<PlaceHolderListModel>> call, Response<List<PlaceHolderListModel>> response) {
                placeHolderList.postValue(response.body());

                String data = response.body().get(0).thumbnailUrl;
                Log.i("HELLLLLOLOO", data);
                Log.i("------>>>>>>>>>>", "I have a response");
            }

            @Override
            public void onFailure(Call<List<PlaceHolderListModel>> call, Throwable t) {
                Log.i("------>>>>>>>>>>", t.toString());
                placeHolderList.postValue(null);
            }
        });
    }
}
