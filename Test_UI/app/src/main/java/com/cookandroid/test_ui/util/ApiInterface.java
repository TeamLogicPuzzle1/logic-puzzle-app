package com.cookandroid.test_ui.util;

import com.cookandroid.test_ui.DTO.common.TokenDto;
import com.cookandroid.test_ui.DTO.reponse.AuthResLoginDto;
import com.cookandroid.test_ui.DTO.reponse.ProductsResDto;
import com.cookandroid.test_ui.DTO.request.AuthReqLoginDto;
import com.cookandroid.test_ui.DTO.request.UserCheckDto;
import com.cookandroid.test_ui.DTO.request.UserSignupDto;
import com.cookandroid.test_ui.DTO.request.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface
{
    @POST("api/v1/user/signup")
    Call<UserSignupDto> userSignupDto(@Body UserSignupDto dto);

    @GET("api/v1/user/checkId")
    Call<UserCheckDto> userCheckDto(@Query("id") String id);

    @POST("api/v1/auth/login")
    Call<AuthResLoginDto> authLoginDto(@Body AuthReqLoginDto authReqDto);

    @POST("api/v1/auth/refresh")
    Call<TokenDto> authRefreshDto(@Body TokenDto tokenDto);

    @Multipart
    @POST("api/v1/production/products/create-with-image/")
    Call<Product> productionDto(@Part Product productionDto);

    @GET("api/v1/production/products/")
    Call<List<ProductsResDto>> productsListDto(@Query("user_id") String userId, @Query("name") String name, @Query("category") Integer category, @Query("location") Integer location);

}

