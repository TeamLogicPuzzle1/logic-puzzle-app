package com.cookandroid.test_ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class main_page_tab extends AppCompatActivity {
    private TabLayout storeFragmentTablayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_tab_layout);

        storeFragmentTablayout = (TabLayout) findViewById(R.id.store_fragment_tablayout);
        viewPager = (ViewPager) findViewById(R.id.ViewPager);

        storeFragmentTablayout.setupWithViewPager(viewPager);

        VPadapter vpAdapter = new VPadapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new main_page_frag(), "상품등록창");
        vpAdapter.addFragment(new main_page_frag2(), "레시피추천창");
        vpAdapter.addFragment(new main_page_frag3(), "음식물쓰레기 조사창");
        viewPager.setAdapter(vpAdapter);


    }
}
