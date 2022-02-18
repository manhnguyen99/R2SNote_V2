package com.example.r2snote_v2.Service;

import com.example.r2snote_v2.model.Dashboard;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DashboardService {
    @GET("get?tab=Dashboard&email")
    Call<Dashboard> getDashboard(@Query("email")  String email);
}
