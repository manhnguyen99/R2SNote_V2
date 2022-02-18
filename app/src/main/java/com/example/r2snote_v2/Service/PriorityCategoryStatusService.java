package com.example.r2snote_v2.Service;

import com.example.r2snote_v2.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PriorityCategoryStatusService {
    @GET("get?tab&email")
    Call<Result> getAllData(
            @Query("tab") String tab,
            @Query("email") String email);

    @GET("add?tab&email&name")
    Call<Result> postData(
            @Query("tab") String tab,
            @Query("email") String email,
            @Query("name") String name);

    @GET("update?tab&email&name&nname")
    Call<Result> updateData(
            @Query("tab") String tab,
            @Query("email") String email,
            @Query("name") String name,
            @Query("nname") String nname);

    @GET("del?tab&email&name")
    Call<Result> deleteData(
            @Query("tab") String tab,
            @Query("email") String email,
            @Query("name") String name);

}
