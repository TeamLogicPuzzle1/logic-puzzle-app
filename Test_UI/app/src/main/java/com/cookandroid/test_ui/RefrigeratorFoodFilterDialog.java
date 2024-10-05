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

import java.util.zip.Inflater;

public class RefrigeratorFoodFilterDialog extends DialogFragment implements View.OnClickListener {
    public RefrigeratorFoodFilterDialog(){

    }
    public RefrigeratorFoodFilterDialog getInstance(Context context) {
        RefrigeratorFoodFilterDialog refrigeratorFoodFilterDialog = new RefrigeratorFoodFilterDialog();
        return refrigeratorFoodFilterDialog;
    }
    Button btnLoc1, btnLoc2, btnLoc3, btnFit1, btnFit2, btnFit3, btnFit4, btnFit5, btnFit6, btnFit7, btnFit8;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.refrigerator_food_filter, container, false);
        btnLoc1 = v.findViewById(R.id.BtnLoc1);
        btnLoc2 = v.findViewById(R.id.BtnLoc2);
        btnLoc3 = v.findViewById(R.id.BtnLoc3);

        btnFit1 = v.findViewById(R.id.BtnFit1);
        btnFit2 = v.findViewById(R.id.BtnFit2);
        btnFit3 = v.findViewById(R.id.BtnFit3);
        btnFit4 = v.findViewById(R.id.BtnFit4);
        btnFit5 = v.findViewById(R.id.BtnFit5);
        btnFit6 = v.findViewById(R.id.BtnFit6);
        btnFit7 = v.findViewById(R.id.BtnFit7);
        btnFit8 = v.findViewById(R.id.BtnFit8);
        Button cancelBtn = v.findViewById(R.id.CancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
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
