package com.cookandroid.test_ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class MainPageTab extends AppCompatActivity implements AddItemDialog.OnDataPassListener {
    private TabLayout storeFragmentTablayout;
    private ViewPager viewPager;
    private VPadapter vpAdapter;
    private MainPageFrag mainPageFrag;
    private ProductAdapter productAdapter;
    Intent intent;
    private ProductViewModel productViewModel;
    @Override
    public void onDataPass(String name, String classification, String storage, String date, int quantity,  Uri imageUri, String memo) {
        Product product = new Product(name, classification, storage, quantity, date, imageUri, memo);
        productViewModel.addProduct(product); // ViewModel에 Product 추가
        if(mainPageFrag != null && mainPageFrag.isAdapterInitialized()) {
            mainPageFrag.getProductAdapter().addProduct(product);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_tab_layout);

        // ViewModel 초기화
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        // 상태가 복원될 경우, ViewModel에 저장된 리스트를 복원
        if(savedInstanceState != null) {
            List<Product> restoredList = savedInstanceState.getParcelableArrayList("productList");
            if(restoredList != null) {
                // ViewModel에 복원된 리스트 설정
                productViewModel.restoreProductList(restoredList);
            }
        }

        productViewModel.getProductList().observe(this, products -> {
            if (mainPageFrag != null && mainPageFrag.isAdapterInitialized()) {
                mainPageFrag.updateProductList(products);
            }
        });

        storeFragmentTablayout = findViewById(R.id.store_fragment_tablayout);
        viewPager = findViewById(R.id.ViewPager);
        storeFragmentTablayout.setupWithViewPager(viewPager);

        vpAdapter = new VPadapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        mainPageFrag = new MainPageFrag(); // main_page_frag 인스턴스 생성
        vpAdapter.addFragment(mainPageFrag, null);
        vpAdapter.addFragment(new MainPageFrag2(), null);
        vpAdapter.addFragment(new MainPageFrag3(), null);
        viewPager.setAdapter(vpAdapter);
        viewPager.setOffscreenPageLimit(3); // ViewPager에 유지할 페이지 수 설정

        storeFragmentTablayout.getTabAt(0).setIcon(R.drawable.home2);
        storeFragmentTablayout.getTabAt(1).setIcon(R.drawable.recipe);
        storeFragmentTablayout.getTabAt(2).setIcon(R.drawable.trash);

        intent = getIntent();
        String selectedDate = intent.getStringExtra("selectedDate");
        String inputText = intent.getStringExtra("inputText");

        // Debug log to check received values
        Log.d("MainPageTab", "selectedDate: " + selectedDate);
        Log.d("MainPageTab", "inputText: " + inputText);

        if (selectedDate != null) {
            showAddItemDialog(selectedDate, inputText);
        }

        productViewModel.getProductList().observe(this, products -> {
            if (mainPageFrag != null) {
                mainPageFrag.updateProductList(products);
            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent); // 새로운 Intent로 업데이트
        // 필요한 데이터 처리
        String selectedDate = intent.getStringExtra("selectedDate");
        String inputText = intent.getStringExtra("inputText");

        if (selectedDate != null && inputText != null) {
            // AddItemDialog를 호출하여 다이얼로그 표시
            showAddItemDialog(selectedDate, inputText);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        List<Product> products = productViewModel.getProductList().getValue();
        if (products != null && !products.isEmpty()) {
            outState.putParcelableArrayList("productList", new ArrayList<>(products));
        }
    }
    private void showAddItemDialog(String selectedDate, String inputText) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddItemDialog addItemDialog = AddItemDialog.getInstance(this);
        // Bundle을 사용하여 AddItemDialog에 데이터 전달
        Bundle bundle = new Bundle();
        bundle.putString("inputText", inputText);
        bundle.putString("selectedDate", selectedDate);
        addItemDialog.setArguments(bundle);

        // 다이얼로그가 이미 열려 있는지 확인 후 표시
        if (fragmentManager.findFragmentByTag("AddItemDialog") == null) {
            addItemDialog.show(getSupportFragmentManager(), "AddItemDialog");
        }
    }
}
