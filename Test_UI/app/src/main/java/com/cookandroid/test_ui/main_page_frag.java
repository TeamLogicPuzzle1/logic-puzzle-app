/*
 * 간략: 상품 등록 창
 * 최초 작성자: 홍진기
 * 최초 작성일: 2024-09-28
 * 수정일: 2024-09-30
 * 버전: 0.0.5
 * */
package com.cookandroid.test_ui;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import com.cookandroid.test_ui.ItemAdapter;
import java.util.List;


public class main_page_frag extends Fragment implements AddItemDialog.OnDataPassListener{
    // List<Item> itemList = new ArrayList<>();  // com.cookandroid.test_ui.Item을 사용
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    String nameText, classificartionText, storageText, dateText, quantityText;



    Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productList = new ArrayList<>();  // 초기화하여 NullPointerException 방지
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_page_frag, container, false);

        // RecyclerView 초기화
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // ProductAdapter 생성 및 RecyclerView에 설정
        productAdapter = new ProductAdapter(productList);
        recyclerView.setAdapter(productAdapter);

        // mainItemLayout = v.findViewById(R.id.MainItemLayout);
        /* WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getActivity().getWindow().setAttributes(layoutParams); */

        // 환경 설정 버튼을 눌렀을때 설정페이지로 들어가는 모드
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
        addItem.setOnClickListener(view -> {
                // FragmentManager fragmentManager = getParentFragmentManager();
                // AddItemDialog addItemDialog = new AddItemDialog();
                // addItemDialog.show(fragmentManager, null);
                intent = new Intent(getActivity(), CamBarcode.class);
                startActivity(intent);

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


    // AddItemDialog에서 데이터를 받아와 CardView를 생성
    @Override
    public void onDataPass(String name, String classification, String storage, String date, int quantity) {
        Product product = new Product(name, classification, storage, quantity, date);
        productAdapter.addProduct(product);  // 어댑터에 새로운 제품 추가 및 RecyclerView 업데이트
    }

    /*private void createNewItemLayout(String name, String classification, String storage, String date, int quantity) {
        Log.d("main_page_frag", "Adding new item to layout");

        // 레이아웃을 product_item_layout으로 인플레이트하여 CardView 추가
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View newItemView = inflater.inflate(R.layout.product_item_layout, recyclerView, false);

        // product_item_layout에서 텍스트 업데이트
        TextView nameTextView = newItemView.findViewById(R.id.NameTextView);
        TextView classificationTextView = newItemView.findViewById(R.id.ClassificationTextView);
        TextView storageTextView = newItemView.findViewById(R.id.StorageTextView);
        TextView quantityTextView = newItemView.findViewById(R.id.QuantityTextView);
        TextView dateTextView = newItemView.findViewById(R.id.DateTextView);

        nameTextView.setText(name);
        classificationTextView.setText("분류: " + classification);
        storageTextView.setText("위치: " + storage);
        quantityTextView.setText("수량: " + quantity);
        dateTextView.setText(date);

        // mainItemLayout에 새로 생성한 CardView 추가
        recyclerView.addView(newItemView);
    } */


}