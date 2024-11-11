package com.cookandroid.test_ui.util;

import com.cookandroid.test_ui.DTO.reponse.AuthResLoginDto;
import com.cookandroid.test_ui.DTO.UserCheckDto;
import com.cookandroid.test_ui.DTO.UserSignupDto;
import com.cookandroid.test_ui.DTO.request.AuthReqLoginDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface
{
    @POST("api/v1/user/signup")
    Call<UserSignupDto> userSignupDto(@Body UserSignupDto dto);

    @GET("api/v1/user/checkId")
    Call<UserCheckDto> userCheckDto(@Query("id") String id);

    @POST("api/v1/auth/login")
    Call<AuthResLoginDto> authLoginDto(@Body AuthReqLoginDto authReqDto);

}
