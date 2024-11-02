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

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
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

@SuppressWarnings("deprecation")
public class main_page_frag extends Fragment implements AddItemDialog.OnDataPassListener{
    // List<Item> itemList = new ArrayList<>();  // com.cookandroid.test_ui.Item을 사용
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ProductViewModel productViewModel;


    private Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_page_frag, container, false);

        productViewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(requireActivity().getApplication(), this)).get(ProductViewModel.class);

        // RecyclerView 초기화
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // ProductAdapter 생성 및 RecyclerView에 설정
        productAdapter = new ProductAdapter(new ArrayList<>());
        recyclerView.setAdapter(productAdapter);

        // 이전에 저장된 데이터가 있다면 복원
        productViewModel.getProductList().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productAdapter.updateProducts(products);
            }
        });

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
    public void onSaveInstanceState(@NonNull Bundle outstate) {
        super.onSaveInstanceState(outstate);
    }
    @Override
    public void onDataPass(String name, String classification, String storage, String date, int quantity) {
        // 새로운 Product 객체 생성
        Product product = new Product(name, classification, storage, quantity, date);
        if (productViewModel != null) {
            productViewModel.addProduct(product); // ViewModel에 추가
        } else {
            Log.e("main_page_frag", "ProductViewModel is not initialized.");
        }
    }





}