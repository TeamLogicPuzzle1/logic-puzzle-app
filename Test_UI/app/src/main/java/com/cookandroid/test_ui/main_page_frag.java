/*
 * 간략: 상품 등록 창
 * 최초 작성자: 홍진기
 * 최초 작성일: 2024-09-28
 * 수정일: 2024-09-29
 * 버전: 0.0.3
 * */
package com.cookandroid.test_ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;


public class main_page_frag extends Fragment {
    Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_page_frag, container, false);

        /* WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getActivity().getWindow().setAttributes(layoutParams); */

        ImageButton settingBtn = v.findViewById(R.id.SettingBtn);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), SettingLeaderVer.class);
                startActivity(intent);
            }
        });

        ImageButton addItem = v.findViewById(R.id.AddItem);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                AddItemDialog addItemDialog = new AddItemDialog();
                addItemDialog.show(fragmentManager, null);
            }
        });
        return v;

    }
}