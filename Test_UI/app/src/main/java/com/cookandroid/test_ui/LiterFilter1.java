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

public class LiterFilter1 extends DialogFragment implements View.OnClickListener {
    public LiterFilter1(){}
    public LiterFilter1 getInstance(Context context) {
        LiterFilter1 literFiter1 = new LiterFilter1();
        return literFiter1;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.liter_filter_1, container, false);
        Button cancelFilterBtn1 = v.findViewById(R.id.CancelFilterBtn1);
        cancelFilterBtn1.setOnClickListener(new View.OnClickListener() {
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
