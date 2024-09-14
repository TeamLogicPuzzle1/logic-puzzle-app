/*
 * 간략: 회원가입 창
 * 최초 작성자: 홍진기
 * 작성일: 2024-09-14
 * 버전: 0.0.1
 * */
package com.cookandroid.test_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CreateAccount extends AppCompatActivity {

    Button returnSingInBtn;
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        returnSingInBtn = (Button) findViewById(R.id.Return_SignIn_Btn);
        returnSingInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), Login.class);
            }
        });
    }
}
