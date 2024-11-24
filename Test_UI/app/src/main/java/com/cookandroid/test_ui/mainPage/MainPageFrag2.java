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
import java.util.Collections;
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
        Log.d("MainPageFrag2", "onCreateView 호출됨");
        View view = inflater.inflate(R.layout.fragment_main_page_frag2, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recipeList = new ArrayList<>();
        adapter = new RecipeAdapter(recipeList);
        recyclerView.setAdapter(adapter);

        api = RetrofitClient.getRetrofit().create(ApiInterface.class);
        handleIncomingArguments();

        return view;
    }


    /**
     * 전달된 데이터를 처리하여 초기화
     */
    private void handleIncomingArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            ArrayList<String> productNames = arguments.getStringArrayList("productNames");
            Log.d("handleIncomingArguments", "받은 productNames: " + productNames); // 로그 추가
            if (productNames != null && !productNames.isEmpty()) {
                fetchRecipes(productNames);
            } else {
                Log.d("handleIncomingArguments", "productNames가 비어있습니다.");
            }
        } else {
            Log.d("handleIncomingArguments", "getArguments()에서 전달된 데이터가 없습니다.");
        }
    }




    /**
     * 레시피 데이터를 서버에서 가져옴
     */
    private void fetchRecipes(List<String> productNames) {
        Log.d("fetchRecipes", "fetchRecipes 호출됨 - productNames: " + productNames);
        if (productNames == null || productNames.isEmpty()) {
            Log.d("fetchRecipes", "productNames 리스트가 비어있습니다.");
            return;
        }

    String accessToken = TokenManger.getAccessToken();
        if (accessToken == null || accessToken.isEmpty()) {
            Log.d("fetchRecipes", "Access Token이 유효하지 않습니다."); // 확인 로그
            return;
        }

        String authorizationHeader = "Bearer " + accessToken;
        String joinedProductNames = String.join(",", productNames);

        Log.d("fetchRecipes", "Authorization Header: " + authorizationHeader);
        Log.d("fetchRecipes", "Product Names: " + joinedProductNames);

        api.recipeFindDto(authorizationHeader, Collections.singletonList(joinedProductNames)).enqueue(new Callback<RecipeFindResDto>() {
            @Override
            public void onResponse(Call<RecipeFindResDto> call, Response<RecipeFindResDto> response) {
                Log.d("API_CALL", "onResponse 호출됨");
                if (response.isSuccessful() && response.body() != null) {
                    RecipeFindResDto responseData = response.body();
                    Log.d("통신성공", "responseData: " + new Gson().toJson(responseData));
                    updateRecipeList(responseData);
                } else {
                    Log.e("통신실패", "Error Code: " + response.code() + ", Response: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<RecipeFindResDto> call, Throwable t) {
                Log.e("API_CALL", "API 호출 실패: " + t.getMessage());
            }
        });

    }


    /**
     * 레시피 리스트를 업데이트
     */
    private void updateRecipeList(RecipeFindResDto responseData) {
        if (responseData == null || responseData.getProductsResListDto() == null) {
            Log.d("updateRecipeList", "responseData 또는 getProductsResListDto가 null입니다.");
            return;
        }

        recipeList.clear(); // 기존 리스트 초기화
        for (RecipeListDto dto : responseData.getProductsResListDto()) {
            String ingredients = dto.getIngredients() != null
                    ? String.join(", ", dto.getIngredients()) // 재료 리스트를 문자열로 변환
                    : "재료 없음";
            Recipe_item item = new Recipe_item(dto.getRecipeName(), ingredients);
            recipeList.add(item);
            Log.d("updateRecipeList", "추가된 아이템: " + item.getName() + " - 재료: " + item.getIngredients());
        }
        Log.d("updateRecipeList", "최종 업데이트된 레시피 리스트: " + recipeList);

        adapter.notifyDataSetChanged(); // RecyclerView 업데이트
    }


    /**
     * 외부에서 데이터 업데이트 요청 시 호출
     */
    public void updateRecipeData(ArrayList<String> productNames) {
        if (productNames == null || productNames.isEmpty()) {
            Log.d("updateRecipeData", "상품 이름 리스트가 비어있습니다.");
            return;
        }

        Log.d("updateRecipeData", "호출된 productNames: " + productNames);

        String accessToken = TokenManger.getAccessToken();
        if (accessToken == null || accessToken.isEmpty()) {
            Log.d("updateRecipeData", "Access Token이 유효하지 않습니다.");
            return;
        }

        Log.d("updateRecipeData", "Access Token: " + accessToken);

        // API 호출
        fetchRecipes(productNames);
    }

}
