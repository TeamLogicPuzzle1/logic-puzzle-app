package com.cookandroid.test_ui.mainPage;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class VPadapter extends FragmentPagerAdapter {

    // 프래그먼트 리스트와 제목 리스트
    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private final ArrayList<String> fragmentTitleList = new ArrayList<>();

    public VPadapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT); // BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT 사용
    }

    // 특정 위치의 프래그먼트를 반환
    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.d("VPadapter", "getItem 호출됨 - Position: " + position);
        return fragmentArrayList.get(position);
    }


    // 프래그먼트의 총 개수를 반환
    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    // 프래그먼트를 리스트에 추가
    public void addFragment(Fragment fragment, String title) {
        fragmentArrayList.add(fragment);
        fragmentTitleList.add(title);
    }

    // 특정 위치의 프래그먼트 제목을 반환 (TabLayout에서 사용)
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }
}
