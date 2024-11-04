package com.cookandroid.test_ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class VPadapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private final ArrayList<String> fragementTitle = new ArrayList<>();

    public VPadapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT); // 여기에서 BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT 사
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new main_page_frag();
            case 1:
                return new main_page_frag2();
            case 2:
                return new main_page_frag3();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void addFragment(Fragment fragment, String title) {

        fragmentArrayList.add(fragment);
        fragementTitle.add(title);

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragementTitle.get(position);
    }
}
