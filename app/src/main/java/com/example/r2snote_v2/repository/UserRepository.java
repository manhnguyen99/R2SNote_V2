package com.example.r2snote_v2.repository;

import com.example.r2snote_v2.Service.APIClient;
import com.example.r2snote_v2.Service.UserService;

public class UserRepository {

    public static UserService getUserService(){
        return APIClient.getClient().create(UserService.class);
    }
}
