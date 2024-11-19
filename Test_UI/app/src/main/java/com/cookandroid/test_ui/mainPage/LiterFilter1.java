/*
 * 간략: 음식물 쓰레기 조사 창2
 * 최초 작성자: 홍진기
 * 작성일: 2024-09-30
 * 수정일: 2024-10-01
 * 버전: 0.0.2
 * */
package com.cookandroid.test_ui.mainPage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cookandroid.test_ui.DTO.common.BooleanResDto;
import com.cookandroid.test_ui.DTO.reponse.AuthResLoginDto;
import com.cookandroid.test_ui.DTO.request.FoodWasteReqAdd;
import com.cookandroid.test_ui.R;
import com.cookandroid.test_ui.util.ApiInterface;
import com.cookandroid.test_ui.util.LogMsgOutput;
import com.cookandroid.test_ui.util.TokenManger;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class LiterFilter1 extends DialogFragment implements View.OnClickListener {
    ApiInterface api;
    com.cookandroid.test_ui.util.RetrofitClient RetrofitClient;
    // 메소드명 작성
    public LiterFilter1(){}
    public LiterFilter1 getInstance(Context context) {
        LiterFilter1 literFiter1 = new LiterFilter1();
        return literFiter1;
    }
    @Nullable
    @Override
    /* FragmentDialog에서 onCreate 대신 onCreateView 작성
     - v(레이아웃 연결)
     - cancelFilterBtn1(취소버튼)
    */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.liter_filter_1, container, false);
        // 둥근 팝업창 만들기 위해 필요한 코드 없을시 직사각형으로 배치
        if(getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        Button cancelFoodWasteBag = v.findViewById(R.id.CancelFoodWasteBag);
        cancelFoodWasteBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        Button addFoodWasteBag = v.findViewById(R.id.AddFoodWasteBag);
        addFoodWasteBag.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                FoodWasteReqAdd foodWasteReqAdd = new FoodWasteReqAdd();
                foodWasteReqAdd.setUserId("39");
                foodWasteReqAdd.setQuantity(1);

                api = RetrofitClient.getRetrofit().create(ApiInterface.class);

                api.foodWasteCreateDto(foodWasteReqAdd).enqueue(new Callback<FoodWasteReqAdd>() {


                    @Override
                    public void onResponse(Call<FoodWasteReqAdd> call, Response<FoodWasteReqAdd> response) {
                        FoodWasteReqAdd responseData = response.body();
                        if(response.isSuccessful()){
                            Log.d("통신성공","responseData : "+new Gson().toJson(responseData));
                        }else{
                            Log.d("통신실패@","responseData : "+response.raw().body());

                        }
                    }

                    @Override
                    public void onFailure(Call<FoodWasteReqAdd> call, Throwable t) {
                        Log.d("통신실패=","통신실패");
                        call.cancel();
                    }
                });

                dismiss();
            }
        });
        return v;

    }

    @Override
    public void onClick(View view) {

    }
}
