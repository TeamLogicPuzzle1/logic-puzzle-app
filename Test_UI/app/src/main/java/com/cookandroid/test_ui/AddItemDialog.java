/*
 * 간략: 음식물 쓰레기 조사 창1
 * 최초 작성자: 홍진기
 * 작성일: 2024-09-29
 * 수정일: 2024-09-30
 * 버전: 0.0.2
 * */
package com.cookandroid.test_ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class AddItemDialog extends DialogFragment implements View.OnClickListener {
    // 다른 자바창에 연결하기 위한 메소드 작성
    public AddItemDialog() {}
    public static AddItemDialog getInstance(Context context) {
        AddItemDialog addItemDialog = new AddItemDialog();
        return addItemDialog;
    }
    /*
    * v(xml파일과 연결)
    * spinClasssification(아이템분류를 선택하는 스피너)
    * spinStorage(저장위치를 선택하는 스피너)
    * backIvBtn(이전버튼)*/
    private int counter = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_item, container, false);
        if(getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        Spinner spinClassification = v.findViewById(R.id.SpinClassification);
        ArrayAdapter spin_adapter_class = ArrayAdapter.createFromResource(v.getContext(), R.array.classification,
                android.R.layout.simple_spinner_item);
        spinClassification.setAdapter(spin_adapter_class);

        Spinner spinStorage = v.findViewById(R.id.SpinStorage);
        ArrayAdapter spin_adapter_stor = ArrayAdapter.createFromResource(v.getContext(), R.array.storage_location,
                android.R.layout.simple_spinner_item);
        spin_adapter_stor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStorage.setAdapter(spin_adapter_stor);


        TextView counterTextViwe = v.findViewById(R.id.CounterTextView);
        ImageButton counterPlusBtn = v.findViewById(R.id.CounterPlusBtn);
        counterPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counterTextViwe.setText(String.valueOf(counter++));
            }
        });
        ImageButton counterMinusBtn = v.findViewById(R.id.CounterMinusBtn);
        counterMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counterTextViwe.setText(String.valueOf(counter--));
            }
        });
        ImageButton backIvBtn = v.findViewById(R.id.BackIvBtn);
        backIvBtn.setOnClickListener(new View.OnClickListener() {
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
    /* super(context);
        setContentView(R.layout.add_item);

        spinClassification = (Spinner) findViewById(R.id.SpinClassification);
        ArrayAdapter spin_adapter_class = ArrayAdapter.createFromResource(getContext(), R.array.classification,
                android.R.layout.simple_spinner_item);
        spin_adapter_class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinClassification.setAdapter(spin_adapter_class);

        spinStorage = (Spinner) findViewById(R.id.SpinStorage);
        ArrayAdapter spin_adapter_stor = ArrayAdapter.createFromResource(getContext(), R.array.storage_location,
                android.R.layout.simple_spinner_item);
        spin_adapter_stor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStorage.setAdapter(spin_adapter_stor);

        backIvBtn = (ImageButton) findViewById(R.id.BackIvBtn);
        backIvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    dismiss();
            }
        }); */

}

