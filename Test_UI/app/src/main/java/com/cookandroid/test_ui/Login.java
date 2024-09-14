/*
 * 간략: 로그인 창
 * 최초 작성자: 홍진기
 * 최초 작성일: 2024-09-14
 * 버전: 0.0.1
 * */
package com.cookandroid.test_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {
    /*
    * 변수명 singUpBtn(회원가입 버튼)
    * */
    AppCompatButton signUpBtn, idFindBtn;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        signUpBtn = (AppCompatButton) findViewById(R.id.SignUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), CreateAccount.class);
                startActivity(intent);
            }
        });
        idFindBtn= (AppCompatButton) findViewById(R.id.IdFindBtn);
        idFindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), IdFind.class);
                startActivity(intent);
            }
        });
    }
}