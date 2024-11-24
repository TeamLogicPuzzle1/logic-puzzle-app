package com.cookandroid.test_ui.mainPage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.cookandroid.test_ui.DTO.request.Product;
import com.cookandroid.test_ui.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainPageTab extends AppCompatActivity implements AddItemDialog.OnDataPassListener, EditItemDialog.OnProductEditedListener {

    private TabLayout storeFragmentTablayout;
    private ViewPager viewPager;
    private VPadapter vpAdapter;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_tab_layout);

        Log.d("MainPageTab", "onCreate 호출됨");

        // ViewModel 초기화
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        Log.d("MainPageTab", "ViewModel 초기화 완료");

        // ViewPager 및 TabLayout 초기화
        storeFragmentTablayout = findViewById(R.id.store_fragment_tablayout);
        viewPager = findViewById(R.id.ViewPager);

        // VPadapter 초기화
        vpAdapter = new VPadapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        // 프래그먼트 추가
        vpAdapter.addFragment(new MainPageFrag(), "홈");
        vpAdapter.addFragment(new MainPageFrag2(), "레시피");
        vpAdapter.addFragment(new MainPageFrag3(), "휴지통");
        Log.d("MainPageTab", "프래그먼트 추가 완료");

        // ViewPager와 어댑터 연결
        viewPager.setAdapter(null); // 어댑터 초기화
        viewPager.setAdapter(vpAdapter); // 어댑터 설정
        viewPager.setOffscreenPageLimit(3); // 3개의 페이지를 미리 로드
        Log.d("MainPageTab", "ViewPager Adapter 설정 완료");

        // TabLayout과 ViewPager 연결
        storeFragmentTablayout.setupWithViewPager(viewPager);

        // 탭 아이콘 설정
        if (storeFragmentTablayout.getTabAt(0) != null)
            storeFragmentTablayout.getTabAt(0).setIcon(R.drawable.home2);
        if (storeFragmentTablayout.getTabAt(1) != null)
            storeFragmentTablayout.getTabAt(1).setIcon(R.drawable.recipe);
        if (storeFragmentTablayout.getTabAt(2) != null)
            storeFragmentTablayout.getTabAt(2).setIcon(R.drawable.trash);
        Log.d("MainPageTab", "탭 아이콘 설정 완료");

        // Intent로부터 데이터 처리
        handleIncomingIntent(getIntent());

        // ViewModel 관찰자로 데이터 업데이트
        observeViewModel();
    }



    private void observeViewModel() {
        productViewModel.getProductList().observe(this, products -> {
            MainPageFrag mainPageFrag = (MainPageFrag) vpAdapter.getItem(0);
            if (mainPageFrag.isAdapterInitialized()) {
                mainPageFrag.getProductAdapter().updateProducts(products);
                mainPageFrag.updateImminentExpirationCount();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent); // 새로운 Intent로 업데이트
        handleIncomingIntent(intent);
    }

    private void handleIncomingIntent(Intent intent) {
        String selectedDate = intent.getStringExtra("selectedDate");
        String expirationDate = intent.getStringExtra("expirationDate");
        String inputText = intent.getStringExtra("inputText");

        Log.d("MainPageTab", "selectedDate: " + selectedDate);
        Log.d("MainPageTab", "expirationDate: " + expirationDate);
        Log.d("MainPageTab", "inputText: " + inputText);

        if (selectedDate != null) {
            showAddItemDialog(selectedDate, inputText);
        } else if (expirationDate != null) {
            showAddItemDialog(expirationDate, inputText);
        } else {
            Log.e("MainPageTab", "유통기한 정보가 없습니다.");
        }
    }

    private void showAddItemDialog(String date, String inputText) {
        AddItemDialog addItemDialog = AddItemDialog.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString("selectedDate", date);
        bundle.putString("inputText", inputText);
        addItemDialog.setArguments(bundle);

        if (getSupportFragmentManager().findFragmentByTag("AddItemDialog") == null) {
            addItemDialog.show(getSupportFragmentManager(), "AddItemDialog");
        }
    }

    @Override
    public void onProductEdited(Product product) {
        productViewModel.updateProduct(product);
    }

    @Override
    public void onDataPass(String name, String category, String location, int quantity, String expirationDate, Uri imageUri, String memo) {
        Product newProduct = new Product(name, category, location, quantity, expirationDate, imageUri, memo);
        productViewModel.addProduct(newProduct);
    }
}
