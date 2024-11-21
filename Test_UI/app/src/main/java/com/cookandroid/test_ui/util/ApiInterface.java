package com.cookandroid.test_ui.util;

import com.cookandroid.test_ui.DTO.common.BooleanResDto;
import com.cookandroid.test_ui.DTO.common.TokenDto;
import com.cookandroid.test_ui.DTO.reponse.AuthResLoginDto;
import com.cookandroid.test_ui.DTO.reponse.ProductsResDto;
import com.cookandroid.test_ui.DTO.request.FoodWasteReqAdd;
import com.cookandroid.test_ui.DTO.request.FoodWasteReqDel;
import com.cookandroid.test_ui.DTO.request.UserCheckDto;
import com.cookandroid.test_ui.DTO.request.UserSignupDto;
import com.cookandroid.test_ui.DTO.request.AuthReqLoginDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface
{
    @POST("api/v1/user/signup")
    Call<UserSignupDto> userSignupDto(@Body UserSignupDto dto);

    @GET("api/v1/user/checkId")
    Call<UserCheckDto> userCheckDto(@Query("id") String id);

    @POST("api/v1/auth/login")
    @Headers("Auth: false")
    Call<AuthResLoginDto> authLoginDto(@Body AuthReqLoginDto authReqDto);

    @POST("api/v1/auth/refresh")
    Call<TokenDto> authRefreshDto(@Body TokenDto tokenDto);

    @GET("api/v1/production/products")
    @Headers("Auth: true")
    Call<List<ProductsResDto>> productsListDto(@Header("Authorization") String authorization,
                                               @Query("user_id") String userId, @Query("name") String name,
                                               @Query("category") Integer category,
                                               @Query("location") Integer location, @Query("filter_type") String filterType);

    @POST("api/v1/foodWaste/food-waste/reduce/")
    @Headers("Auth: true")
    Call<FoodWasteReqDel> foodWasteDelDto(@Header("Authorization") String authorization, @Body FoodWasteReqDel foodWasteReqDel);

    @POST("api/v1/foodWaste/food-waste/")
    @Headers("Auth: true")
    Call<FoodWasteReqAdd> foodWasteCreateDto(@Header("Authorization") String authorization, @Body FoodWasteReqAdd foodWasteReqAdd);

}
