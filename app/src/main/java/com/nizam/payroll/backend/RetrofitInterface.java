package com.nizam.payroll.backend;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @POST("/login")
    Call<UserModel> executeLogin(@Body HashMap<String,String> map);

    @POST("/signup")
    Call<Void> executeSignUp(@Body HashMap<String,String> map);

    @GET("/login/{id}")
    Call<UserModel> loginById(@Path("id") int id);
}
