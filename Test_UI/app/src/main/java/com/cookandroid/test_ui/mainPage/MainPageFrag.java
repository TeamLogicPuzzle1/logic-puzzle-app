/*
 * 간략: 메인페이지 1번 상품 등록 및 조회 창
 * 최초 작성자: 홍진기
 * 작성일: 2024-09-27
 * 수정일: 2024-11-09
 * 버전: 0.1.0
 * */
package com.cookandroid.test_ui.mainPage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.cookandroid.test_ui.R;
import com.cookandroid.test_ui.setting.SettingLeaderVer;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class MainPageFrag extends Fragment implements ProductAdapter.SelectionModeListener{
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ProductViewModel productViewModel;
    private ArrayList<Product> productList = new ArrayList<>();
    private ProductFileManager productFileManager;
    private boolean adapterInitialized = false;
    private static final int REQUEST_CODE_PAGE_2 = 1;

    private Intent intent;

    AppCompatButton recipeProductButton;
    AppCompatButton deleteProductButton;

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
        productAdapter = new ProductAdapter(new ArrayList<>(productList), this, productViewModel);
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
        // 버튼 초기화
        recipeProductButton = v.findViewById(R.id.RecipeProductButton);
        deleteProductButton = v.findViewById(R.id.DeleteProductButton);

        recipeProductButton.setVisibility(View.INVISIBLE);
        deleteProductButton.setVisibility(View.INVISIBLE);

        deleteProductButton.setOnClickListener(view -> {
            List<Product> deletedProducts = productAdapter.removeSelectedItems(); // Adapter에서 삭제된 항목 가져오기
            productViewModel.removeProducts(deletedProducts); // ViewModel에서 해당 항목 삭제
            productFileManager.saveProductList(new ArrayList<>(productViewModel.getProductList().getValue())); // 파일에도 변경된 리스트 저장
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(productAdapter, requireContext()));
        itemTouchHelper.attachToRecyclerView(recyclerView);
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
    @Override
    public void onSelectionModeChanged(boolean isSelectionMode) {
        // 선택 모드일 때만 버튼 표시
        int visibility = isSelectionMode ? View.VISIBLE : View.INVISIBLE;
        recipeProductButton.setVisibility(visibility);
        deleteProductButton.setVisibility(visibility);

        deleteProductButton.setVisibility(isSelectionMode ? View.VISIBLE : View.GONE);
    }

    // MainPageFrag 클래스 내부
    @Override
    public void onProductRemoved(int position) {
        // 항목 삭제 후 추가 작업이 필요할 경우 이곳에 작성합니다.
        Log.d("MainPageFrag", "Product removed at position: " + position);
    }
}
