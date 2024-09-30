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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class AddItemDialog extends DialogFragment implements View.OnClickListener {

    public AddItemDialog() {}
    public static AddItemDialog getInstance(Context context) {
        AddItemDialog addItemDialog = new AddItemDialog();
        return addItemDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_item, container, false);
        Spinner spinClassification = v.findViewById(R.id.SpinClassification);
        ArrayAdapter spin_adapter_class = ArrayAdapter.createFromResource(v.getContext(), R.array.classification,
                android.R.layout.simple_spinner_item);
        spinClassification.setAdapter(spin_adapter_class);

        Spinner spinStorage = v.findViewById(R.id.SpinStorage);
        ArrayAdapter spin_adapter_stor = ArrayAdapter.createFromResource(v.getContext(), R.array.storage_location,
                android.R.layout.simple_spinner_item);
        spin_adapter_stor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStorage.setAdapter(spin_adapter_stor);

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

