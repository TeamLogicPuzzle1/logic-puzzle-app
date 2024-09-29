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
        vpAdapter.addFragment(new main_page_frag(), null);
        vpAdapter.addFragment(new main_page_frag2(), null);
        vpAdapter.addFragment(new main_page_frag3(), null);
        viewPager.setAdapter(vpAdapter);

        storeFragmentTablayout.getTabAt(0).setIcon(R.drawable.home2);
        storeFragmentTablayout.getTabAt(1).setIcon(R.drawable.recipe);
        storeFragmentTablayout.getTabAt(2).setIcon(R.drawable.trash);



    }
}
