package com.cookandroid.test_ui.util;

import com.cookandroid.test_ui.DTO.UserCheckDto;
import com.cookandroid.test_ui.DTO.UserSignupDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface
{
    @POST("api/v1/user/signup")
    Call<UserSignupDto> userSignupDto(@Body UserSignupDto dto);

    @GET("api/v1/user/check")
    Call<UserCheckDto> userCheckDto(@Query("id") String id);
}
