package com.cookandroid.test_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class MainPageFrag extends Fragment {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ProductViewModel productViewModel;
    private ArrayList<Product> productList = new ArrayList<>();
    private ProductFileManager productFileManager;
    private boolean adapterInitialized = false;
    private static final int REQUEST_CODE_PAGE_2 = 1;

    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        productFileManager = new ProductFileManager(requireContext());

        // ViewModel 초기화
        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);

        // ViewModel에 저장된 데이터가 비어있는 경우에만 파일에서 불러오기
        if (productViewModel.getProductList().getValue() == null || productViewModel.getProductList().getValue().isEmpty()) {
            List<Product> loadedProductList = productFileManager.loadProductList();
            if (loadedProductList != null) {
                productViewModel.restoreProductList(loadedProductList);
                productList.addAll(loadedProductList); // productList에 로드된 데이터 추가
            }
        } else {
            productList.addAll(productViewModel.getProductList().getValue());
        }
    }



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_page_frag, container, false);

        // 뒤로가기 버튼을 막는 코드 추가
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        });

        // RecyclerView 초기화
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // ProductAdapter 생성 및 RecyclerView에 설정
        // productAdapter = new ProductAdapter(new ArrayList<>(productList));
        productAdapter = new ProductAdapter(new ArrayList<>(productList));
        recyclerView.setAdapter(productAdapter);

        // ViewModel 옵저버 설정
        productViewModel.getProductList().observe(getViewLifecycleOwner(), products -> {
            Log.d("MainPageFrag", "Observer triggered - Product count: " + products.size());
            productAdapter.updateProducts(products);
        });
        adapterInitialized = true;

        // 설정 버튼
        ImageButton settingBtn = v.findViewById(R.id.SettingBtn);
        settingBtn.setOnClickListener(view -> {
            intent = new Intent(getActivity(), SettingLeaderVer.class);
            startActivity(intent);
        });

        // 추가 버튼
        ImageButton addItem = v.findViewById(R.id.AddItem);
        addItem.setOnClickListener(view -> {
            intent = new Intent(getActivity(), CamBarcode.class);
            startActivity(intent);
        });

        // 냉장고 필터 버튼
        ImageButton refrigeratorFoodFilterCheck = v.findViewById(R.id.RefrigeratorFoodFilterCheck);
        refrigeratorFoodFilterCheck.setOnClickListener(view -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            RefrigeratorFoodFilterDialog refrigeratorFoodFilterDialog = new RefrigeratorFoodFilterDialog();
            refrigeratorFoodFilterDialog.show(fragmentManager, null);
        });

        return v;
    }

    public boolean isAdapterInitialized() {
        return adapterInitialized;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (productViewModel.getProductList().getValue() != null) {
            outState.putParcelableArrayList("productList", new ArrayList<>(productViewModel.getProductList().getValue()));
        }
    }

    public void updateProductList(List<Product> products) {
        if (adapterInitialized && productAdapter != null) {
            productAdapter.updateProducts(products);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (productList != null) {
            productFileManager.saveProductList(productList);
        } else {
            Log.e("MainPageFrag", "productList is null during onPause. Initializing a new ArrayList.");
            productList = new ArrayList<>();
        }
    }
    public ProductAdapter getProductAdapter() {
        return productAdapter;
    }
    /* @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == REQUEST_CODE_PAGE_2 && resultCode == RESULT_OK && data != null) {
            String newProductData = data.getStringExtra("productList");
            if(newProductData != null) {
                productAdapter.notifyItemInserted(productList.size() -1);
            }
        }
    } */
}
