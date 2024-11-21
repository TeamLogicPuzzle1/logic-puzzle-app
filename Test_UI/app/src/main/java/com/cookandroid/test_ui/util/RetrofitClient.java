package com.cookandroid.test_ui.util;

import android.util.Log;

import com.cookandroid.test_ui.DTO.common.TokenDto;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    static ApiInterface api;
    private static Retrofit retrofit;
    private static String BASE_URL = "http://43.202.243.97/";
    private static String ACCESS_TOKEN = "";

    public static void setAccessToken(String token) {
        ACCESS_TOKEN = token;
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            // HttpLoggingInterceptor to log network requests
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Interceptor to add Authorization header with Bearer token
            Interceptor authInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Headers headers = originalRequest.headers();
                    Request.Builder builder = originalRequest.newBuilder();

                    Log.d("Interceptor", "Headers: " + headers);

                    // Only add token if it exists
                    if (originalRequest.header("Auth") != null && originalRequest.header("Auth").equals("false")) {
                        Request newRequest = originalRequest.newBuilder()
                                .removeHeader("Auth")
                                .build();
                        Log.d("Headers", "login");
                        return chain.proceed(newRequest);
                    } else {
                        if (!ACCESS_TOKEN.isEmpty()) {
                            builder.header("Authorization", "Bearer " + ACCESS_TOKEN);
                        } else {
                            return errorResponse(originalRequest);
                        }
                    }

                    Request requestWithAuth = builder.build();
                    Response response = chain.proceed(requestWithAuth);

                    // If 401 Unauthorized, try refreshing token
                    if (response.code() == 401) {
                        response.close(); // Close the initial response
                        boolean tokenRefreshed = refreshToken(); // Attempt to refresh the token
                        if (tokenRefreshed) {
                            // Retry the request with the new access token
                            Request newRequest = originalRequest.newBuilder()
                                    .header("Authorization", "Bearer " + ACCESS_TOKEN)
                                    .build();
                            return chain.proceed(newRequest);
                        } else {
                            // If token refresh fails, return an error response
                            return errorResponse(originalRequest);
                        }
                    }
                    return response;
                }
            };

            // Build OkHttpClient with interceptors
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addInterceptor(authInterceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    private static boolean refreshToken() {
        ApiInterface apiService = getRetrofit().create(ApiInterface.class);
        String REFRESH_TOKEN = TokenManger.getRefreshToken();
        Call<TokenDto> call = apiService.authRefreshDto(new TokenDto(REFRESH_TOKEN));
        try {
            retrofit2.Response<TokenDto> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                TokenDto responseData = response.body();
                ACCESS_TOKEN = (String) responseData.getAccess(); // 새로운 액세스 토큰 저장
                Log.d("ACCESS_TOKEN ====", ACCESS_TOKEN);
                return true;
            }
        } catch (IOException e) {
            Log.e("RetrofitClient", "Error refreshing token", e);
        }
        return false;
    }

    private static Response errorResponse(Request request) {
        return new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_2)
                .code(401) // You can replace this with an appropriate network error code
                .message("Unauthorized: No access token provided")
                .body(ResponseBody.create(null, "Access token missing or invalid"))
                .build();
    }
}
