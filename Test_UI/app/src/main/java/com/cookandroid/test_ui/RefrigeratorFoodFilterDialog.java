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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.refrigerator_food_filter, container, false);
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
