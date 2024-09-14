package com.cookandroid.test_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class IdFind extends AppCompatActivity {
    Button ifReturnSignInBtn;
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.id_find);
        ifReturnSignInBtn = (Button) findViewById(R.id.IF_Return_SignIn_Btn);
        ifReturnSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }
}
