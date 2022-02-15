package com.example.r2snote_v2.Service;

import com.example.r2snote_v2.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @GET("/login?email&pass")
    Call<User> getUserByEmail(@Query("email") String email, @Query("pass") String passWord);

    @POST("/signup?email&pass&firstname&lastname")
    Call<User> createNewUser(@Query("email") String email,
                             @Query("pass") String passWord,
                             @Query("firstname") String firstName,
                             @Query("lastname") String lastName);

}
