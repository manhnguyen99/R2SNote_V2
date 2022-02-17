package com.example.r2snote_v2.repository;

import com.example.r2snote_v2.Service.APIClient;
import com.example.r2snote_v2.Service.ProrityService;

public class PriorityRepository {
    public static ProrityService getProrityService(){
        return APIClient.getClient().create(ProrityService.class);
    }
}
