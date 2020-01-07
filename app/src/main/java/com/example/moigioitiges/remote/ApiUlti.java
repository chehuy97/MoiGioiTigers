package com.example.moigioitiges.remote;

public class ApiUlti {
    public static final String BASE_URL = "https://tiger010203.herokuapp.com/";
    public static ApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
