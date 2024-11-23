/*
 * 간략: 메인페이지 1번 상품 등록 및 조회 창
 * 최초 작성자: 홍진기
 * 작성일: 2024-09-27
 * 수정일: 2024-11-23
 * 수정자: 박시형
 * 수정이유: 내부 로직 전부 추가
 * 버전: 0.2.0
 * */
package com.cookandroid.test_ui.mainPage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.test_ui.DTO.response.RecipeFindResDto;
import com.cookandroid.test_ui.DTO.response.RecipeListDto;
import com.cookandroid.test_ui.R;
import com.cookandroid.test_ui.util.ApiInterface;
import com.cookandroid.test_ui.util.RetrofitClient;
import com.cookandroid.test_ui.util.TokenManger;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPageFrag2 extends Fragment {
    private ApiInterface api;
    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private List<Recipe_item> recipeList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page_frag2, container, false);

        // RecyclerView 초기화
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 레시피 리스트 초기화
        recipeList = new ArrayList<>();
        adapter = new RecipeAdapter(recipeList);
        recyclerView.setAdapter(adapter);

        // API 선언
        api = RetrofitClient.getRetrofit().create(ApiInterface.class);
        String accessToken = TokenManger.getAccessToken();
        String authorizationHeader = "Bearer " + accessToken;

        // MainPageFrag에서 전달된 데이터 받기
        Bundle arguments = getArguments();
        if (arguments != null) {
            ArrayList<String> productNames = arguments.getStringArrayList("productNames");
            if (productNames != null && !productNames.isEmpty()) {
                Log.d("MainPageFrag2", "받은 상품 이름 리스트: " + productNames);
                fetchRecipes(productNames, authorizationHeader);
            } else {
                Log.d("MainPageFrag2", "상품 이름 리스트가 비어있습니다.");
            }
        } else {
            Log.d("MainPageFrag2", "전달된 데이터가 없습니다.");
        }

        return view;
    }

    private void fetchRecipes(List<String> productNames, String authorizationHeader) {
        if (productNames == null || productNames.isEmpty()) {
            Log.d("fetchRecipes", "productNames 리스트가 비어있습니다.");
            return;
        }

        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            Log.d("fetchRecipes", "Authorization 헤더가 비어있습니다.");
            return;
        }

        api.recipeFindDto(authorizationHeader, productNames).enqueue(new Callback<RecipeFindResDto>() {
            @Override
            public void onResponse(Call<RecipeFindResDto> call, Response<RecipeFindResDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RecipeFindResDto responseData = response.body();
                    Log.d("통신성공", "responseData : " + new Gson().toJson(responseData));

                    // 레시피 데이터를 RecyclerView에 반영
                    recipeList.clear();
                    for (RecipeListDto dto : responseData.getProductsResListDto()) {
                        // 재료 리스트를 문자열로 변환
                        String ingredients = dto.getIngredients() != null
                                ? String.join(", ", dto.getIngredients())
                                : "재료 없음";
                        recipeList.add(new Recipe_item(dto.getRecipeName(), ingredients));
                    }
                    adapter.notifyDataSetChanged(); // RecyclerView 업데이트
                } else {
                    Log.e("통신실패@", "Error Code: " + response.code() + ", responseData: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<RecipeFindResDto> call, Throwable t) {
                Log.e("통신실패=", "통신 실패", t);
                call.cancel();
            }
        });


    }
}
