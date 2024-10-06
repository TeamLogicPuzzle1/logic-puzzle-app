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
public class main_page_tab extends AppCompatActivity {
    private TabLayout storeFragmentTablayout;
    private ViewPager viewPager;
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_tab_layout);

        storeFragmentTablayout = (TabLayout) findViewById(R.id.store_fragment_tablayout);
        viewPager = (ViewPager) findViewById(R.id.ViewPager);

        storeFragmentTablayout.setupWithViewPager(viewPager);

        VPadapter vpAdapter = new VPadapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new main_page_frag(), null);
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

            FragmentManager fragmentManager = getSupportFragmentManager();
            AddItemDialog addItemDialog = AddItemDialog.getInstance(this);

            // 날짜와 입력 텍스트를 AddItemDialog에 전달하기 위해 Bundle 사용
            Bundle bundle = new Bundle();
            bundle.putString("inputText", inputText); // 입력한 텍스트를 전달
            bundle.putString("selectedDate", selectedDate); // 선택한 날짜 전달
            addItemDialog.setArguments(bundle); // 다이얼로그에 번들 전달

            addItemDialog.show(fragmentManager, "AddItemDialog");
        }
    }
}
