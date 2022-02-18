package com.example.r2snote_v2.repository;

import android.util.Log;

import com.example.r2snote_v2.Service.APIClient;
import com.example.r2snote_v2.Service.PriorityCategoryStatusService;
import com.example.r2snote_v2.ViewModel.RefreshLiveData;
import com.example.r2snote_v2.model.Result;
import com.example.r2snote_v2.ui.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PriorityRepository {
    private PriorityCategoryStatusService mPriorityService;

    public PriorityRepository() {
        mPriorityService = APIClient.getClient().create(PriorityCategoryStatusService.class);
    }

    public RefreshLiveData<Result> loadAllPriority(String tab) {
        final RefreshLiveData<Result> liveData = new RefreshLiveData<>((callback) -> {
            mPriorityService.getAllData(tab, MainActivity.userLogin.getEmail()).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if (response.body().getStatus() == 1) {
                        callback.onDataLoaded(response.body());

                    } else {
                        Log.e("Fail", "fail");
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Log.e("Fail", "fail");
                }
            });
        });
        return liveData;
    }

    public Call<Result> editPriority(String tab, String email, String name, String nname) {
        return mPriorityService.updateData(tab, email, name, nname);
    }

    public Call<Result> createPriority(String tab, String email, String name) {
        return mPriorityService.postData(tab, email, name);
    }

    public Call<Result> removePriority(String tab, String email, String name) {
        return mPriorityService.deleteData(tab, email, name);
    }
}
