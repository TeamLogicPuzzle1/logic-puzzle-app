/*
 * 간략: 비밀번호 찾기 창
 * 최초 작성자: 홍진기
 * 작성일: 2024-09-18
 * 버전: 0.0.1
 * */
package com.cookandroid.test_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PwFind extends AppCompatActivity {
    /*
    * 변수명
    * pf_Return_SignIn_Btn(비밀번호 찾기 창에 있는 취소 버튼)
    * */
    Intent intent;
    Button pf_Return_SignIn_Btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pw_find);
        pf_Return_SignIn_Btn = (Button) findViewById(R.id.PF_Return_SignIn_Btn);
        pf_Return_SignIn_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }
}
