/*
 * 간략: 음식물 쓰레기 통계 창
 * 최초 작성자: 홍진기
 * 최초 작성일: 2024-09-28
 * 수정일: 2024-09-29
 * 버전: 0.0.2
 * */
package com.cookandroid.test_ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class main_page_frag3 extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_page_frag3, container, false);
    }
}