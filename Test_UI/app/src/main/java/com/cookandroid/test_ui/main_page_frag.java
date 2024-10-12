/*
 * 간략: 상품 등록 창
 * 최초 작성자: 홍진기
 * 최초 작성일: 2024-09-28
 * 수정일: 2024-09-30
 * 버전: 0.0.5
 * */
package com.cookandroid.test_ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;


public class main_page_frag extends Fragment implements AddItemDialog.OnDataPassListener{
    private ViewGroup parentLayout;


    private void createNewItemLayout(String name, String classification, String storage, String date, int quantity) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View newItemView = inflater.inflate(R.layout.product_item_layout, parentLayout, false);

        // 아이템 뷰에서 텍스트 업데이트
        TextView nameTextView = newItemView.findViewById(R.id.NameTextView);
        TextView classificationTextView = newItemView.findViewById(R.id.ClassificationTextView);
        TextView storageTextView = newItemView.findViewById(R.id.StorageTextView);
        TextView dateTextView = newItemView.findViewById(R.id.DateTextView);
        TextView quantityTextView = newItemView.findViewById(R.id.QuantityTextView);

        nameTextView.setText(name);
        classificationTextView.setText("분류: " + classification);
        storageTextView.setText("위치: " + storage);
        dateTextView.setText(date);
        quantityTextView.setText("수량: " + quantity);

        parentLayout.addView(newItemView);
    }

    Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_page_frag, container, false);
        parentLayout = v.findViewById(R.id.AddItemLayout);
        if (parentLayout == null) {
            Log.e("main_page_frag", "parentLayout is null. Check your layout XML.");
        }
        /* WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getActivity().getWindow().setAttributes(layoutParams); */

        // 환경 설정 버튼을 눌렀을때 설정페이지로 들어가는 ㅋ모드
        // settingBtn(설정)
        ImageButton settingBtn = v.findViewById(R.id.SettingBtn);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), SettingLeaderVer.class);
                startActivity(intent);
            }
        });
        // 추가 버튼을 눌렀을 때 상품등록 팝업창을 구현하는 코드
        // addItem(더하기 버튼)
        ImageButton addItem = v.findViewById(R.id.AddItem);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // FragmentManager fragmentManager = getParentFragmentManager();
                // AddItemDialog addItemDialog = new AddItemDialog();
                // addItemDialog.show(fragmentManager, null);
                intent = new Intent(getActivity(), CamBarcode.class);
                startActivity(intent);
            }
        });
        // 필터버튼을 눌렀을때 냉장고 필터 팝업창이 뜨는 코드
        // 변수 설명 refrigeratorFoodFilterCheck(냉장고 필터 버튼)
        ImageButton refrigeratorFoodFilterCheck = (ImageButton) v.findViewById(R.id.RefrigeratorFoodFilterCheck);
        refrigeratorFoodFilterCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                RefrigeratorFoodFilterDialog refrigeratorFoodFilterDialog = new RefrigeratorFoodFilterDialog();
                refrigeratorFoodFilterDialog.show(fragmentManager, null);

            }
        });

        return v;

    }

    @Override
    public void onDataPass(String name, String classification, String storage, String date, int quantity) {
       createNewItemLayout(name, classification, storage, date, quantity);
    }
}