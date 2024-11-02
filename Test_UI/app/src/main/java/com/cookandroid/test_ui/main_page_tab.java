package com.cookandroid.test_ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

@SuppressWarnings("deprecation")
public class main_page_tab extends AppCompatActivity implements AddItemDialog.OnDataPassListener{
    private TabLayout storeFragmentTablayout;
    private ViewPager viewPager;
    private VPadapter vpAdapter;
    private main_page_frag mainPageFrag;

    Intent intent;

    @Override
    public void onDataPass(String name, String classification, String storage, String date, int quantity) {
        // 전달된 데이터를 main_page_frag에 전달
        if (mainPageFrag != null) {
            mainPageFrag.onDataPass(name, classification, storage, date, quantity);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_tab_layout);

        storeFragmentTablayout = findViewById(R.id.store_fragment_tablayout);
        viewPager = findViewById(R.id.ViewPager);
        storeFragmentTablayout.setupWithViewPager(viewPager);

        vpAdapter = new VPadapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        mainPageFrag = new main_page_frag(); // main_page_frag 인스턴스 생성
        vpAdapter.addFragment(mainPageFrag, null);
        vpAdapter.addFragment(new main_page_frag2(), null);
        vpAdapter.addFragment(new main_page_frag3(), null);
        viewPager.setAdapter(vpAdapter);

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
            addItemDialog.show(fragmentManager, "AddItemDialog");
        }
    }
}
