/*
 * 간략: 음식물 쓰레기 조사 창1
 * 최초 작성자: 홍진기
 * 작성일: 2024-09-30
 * 수정일: 2024-10-01
 * 버전: 0.0.2
 * */
package com.cookandroid.test_ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class LiterFilter2 extends DialogFragment implements View.OnClickListener {
    public LiterFilter2(){

    }
    public LiterFilter2 getInstance(Context context) {
        LiterFilter2 literFilter2 = new LiterFilter2();
        return literFilter2;
    }
    /* FragmentDialog에서 onCreate 대신 onCreateView 작성
         - v(레이아웃 연결)
         - cancelFilterBtn2(취소버튼)
        */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.liter_filter_2, container, false);
        // 둥근팝업창 만들기 위한 필요한 코드 없을시 직사각형으로 배치
        if(getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        Button cancelFilter2 = v.findViewById(R.id.CancelFilterBtn2);
        cancelFilter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return v;
    }

    @Override
    public void onClick(View view) {

    }
}
