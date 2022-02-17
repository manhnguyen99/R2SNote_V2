package com.example.r2snote_v2.Service;

import com.example.r2snote_v2.model.Priority;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProrityService {

    @GET("/get?tab&email")
    Call<Priority> getPrority(@Query("tab") String data, @Query("email") String email);

}
