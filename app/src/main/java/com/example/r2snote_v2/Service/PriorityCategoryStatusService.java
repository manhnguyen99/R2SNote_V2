package com.example.r2snote_v2.Service;

import com.example.r2snote_v2.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PriorityService {
    @GET("get?tab=Priority&email")
    Call<Result> getAllPriority(
            @Query("email") String email);

    @GET("add?tab=Priority&email&name")
    Call<Result> postPriority(
            @Query("email") String email,
            @Query("name") String name);

    @GET("update?tab=Priority&email&name&nname")
    Call<Result> updatePriority(
            @Query("email") String email,
            @Query("name") String name,
            @Query("nname") String nname);

    @GET("del?tab=Priority&email&name")
    Call<Result> deletePriority(
            @Query("email") String email,
            @Query("name") String name);

}
