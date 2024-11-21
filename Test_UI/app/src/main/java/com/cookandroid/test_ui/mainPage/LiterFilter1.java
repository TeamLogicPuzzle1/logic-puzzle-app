/*
 * 간략: 음식물 쓰레기 조사 창2
 * 최초 작성자: 홍진기
 * 수정자: 박시형
 * 작성일: 2024-09-30
 * 수정일: 2024-11-20
 * 수정 이유: 뷰 확인 버튼 클릭시 연결 안함, 각 버리는 양에 따른 버튼 이펙트 안 만듬, 변수명 이상함, api연결 안되어 있음
 * 버전: 0.0.3
 * */
package com.cookandroid.test_ui.mainPage;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class LiterFilter1 extends DialogFragment implements View.OnClickListener {
    //api연결을 위해 api관련 변수 및 api선언(작성자:박시형)
    ApiInterface api;
    com.cookandroid.test_ui.util.RetrofitClient RetrofitClient;
    List<Button> wasteaddbuttons = new ArrayList<>();
    //선택 초기화를 위한 이미 선택된 버튼 표시를 위한 버튼 선언(작성자:박시형)
    private Button selectedButton = null;
    private int wasteaddLiter = 0;

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
        // 추가창 L에 맞게 연결후 wasteaddbuttons 리스트에 넣음(작성자:박시형)
        wasteaddbuttons.add(v.findViewById(R.id.wasteadd1L));
        wasteaddbuttons.add(v.findViewById(R.id.wasteadd2L));
        wasteaddbuttons.add(v.findViewById(R.id.wasteadd3L));
        wasteaddbuttons.add(v.findViewById(R.id.wasteadd5L));
        wasteaddbuttons.add(v.findViewById(R.id.wasteadd10L));
        wasteaddbuttons.add(v.findViewById(R.id.wasteadd20L));

// 버튼 클릭시 리스너 설정(작성자:박시형)
        for (Button button : wasteaddbuttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 이전 선택된 버튼이 있을 경우 초기화(작성자:박시형)
                    if (selectedButton != null) {
                        selectedButton.setBackgroundColor(Color.WHITE);
                    }

                    // 선택된 버튼 강조(작성자:박시형)
                    button.setBackgroundColor(Color.GRAY);
                    selectedButton = button;

                    // 선택된 버튼에 따라 wasteaddLiter 값 설정(작성자:박시형)
                    if (selectedButton == v.findViewById(R.id.wasteadd1L)) {
                        wasteaddLiter = 1;
                    } else if (selectedButton == v.findViewById(R.id.wasteadd2L)) {
                        wasteaddLiter = 2;
                    } else if (selectedButton == v.findViewById(R.id.wasteadd3L)) {
                        wasteaddLiter = 3;
                    } else if (selectedButton == v.findViewById(R.id.wasteadd5L)) {
                        wasteaddLiter = 4;
                    } else if (selectedButton == v.findViewById(R.id.wasteadd10L)) {
                        wasteaddLiter = 5;
                    } else if (selectedButton == v.findViewById(R.id.wasteadd20L)) {
                        wasteaddLiter = 6;
                    }
                }
            });
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
                if(selectedButton!=null) {
                    FoodWasteReqAdd foodWasteReqAdd = new FoodWasteReqAdd();
                    //유저아이디 매칭시키는거 연결해야함(작성자:박시형)
                    foodWasteReqAdd.setUserId("39");
                    //0=1L/1=2L/2=3L/3=5L/4=10L/5=20L
                    foodWasteReqAdd.setQuantity(wasteaddLiter - 1);

                    api = RetrofitClient.getRetrofit().create(ApiInterface.class);

                    api.foodWasteCreateDto(foodWasteReqAdd).enqueue(new Callback<FoodWasteReqAdd>() {


                        @Override
                        public void onResponse(Call<FoodWasteReqAdd> call, Response<FoodWasteReqAdd> response) {
                            FoodWasteReqAdd responseData = response.body();
                            if (response.isSuccessful()) {
                                Log.d("통신성공", "responseData : " + new Gson().toJson(responseData));
                            } else {
                                Log.d("통신실패@", "responseData : " + response.raw().body());

                            }
                        }

                        @Override
                        public void onFailure(Call<FoodWasteReqAdd> call, Throwable t) {
                            Log.d("통신실패=", "통신실패");
                            call.cancel();
                        }
                    });
                    dismiss();
                }
            }
        });
        return v;
    }
    @Override
    public void onClick(View view) {

    }
}
