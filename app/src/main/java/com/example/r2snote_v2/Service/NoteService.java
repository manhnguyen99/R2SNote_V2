package com.example.r2snote_v2.Service;

import com.example.r2snote_v2.model.Result;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NoteService {

    @GET("get?tab=Note&email")
    Call<Result> getAllNotes(
            @Query("email") String email);

    @GET("add?tab=Note&email&name&Priority&Category&Status&PlanDate")
    Call<Result> postNote(
            @Query("email") String email,
            @Query("name") String name,
            @Query("Priority") String Priority,
            @Query("Category") String Category,
            @Query("Status") String Status,
            @Query("PlanDate") String PlanDate);

    @GET("update/?tab=Note&email&name&nname&Priority&Category&Status&PlanDate")
    Call<Result> updateNote(
            @Query("email") String email,
            @Query("name") String name,
            @Query("nname") String nname,
            @Query("Priority") String Priority,
            @Query("Category") String Category,
            @Query("Status") String Status,
            @Query("PlanDate") String PlanDate);

    @GET("del?tab=Note&email&name")
    Call<Result> deleteNote(
            @Query("email") String email,
            @Query("name") String name);

}
