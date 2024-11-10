/*
* */
package com.cookandroid.test_ui.User;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.test_ui.R;

public class ProfileSelect extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_select);

    }
}
