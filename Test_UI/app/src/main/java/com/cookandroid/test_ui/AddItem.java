package com.cookandroid.test_ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class AddItem extends AppCompatActivity {

    Spinner spinClassification, spinStorage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        spinClassification = (Spinner) findViewById(R.id.SpinClassification);
        ArrayAdapter spin_adapter_class = ArrayAdapter.createFromResource(this, R.array.classification,
                android.R.layout.simple_spinner_item);
        spin_adapter_class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinClassification.setAdapter(spin_adapter_class);

        spinStorage = (Spinner) findViewById(R.id.SpinStorage);
        ArrayAdapter spin_adapter_stor = ArrayAdapter.createFromResource(this, R.array.storage_location,
                android.R.layout.simple_spinner_item);
        spin_adapter_stor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStorage.setAdapter(spin_adapter_stor);

        }
}
