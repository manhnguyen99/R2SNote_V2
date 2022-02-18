package com.example.r2snote_v2.repository;

import android.util.Log;

import com.example.r2snote_v2.Service.APIClient;
import com.example.r2snote_v2.Service.DashboardService;
import com.example.r2snote_v2.ViewModel.RefreshLiveData;
import com.example.r2snote_v2.model.Dashboard;
import com.example.r2snote_v2.model.Result;
import com.example.r2snote_v2.ui.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardRepository {
    public static DashboardService getDashboardService(){
        return APIClient.getClient().create(DashboardService.class);
    }
}
