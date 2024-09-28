/*
 * 간략: 로그인 창
 * 최초 작성자: 홍진기
 * 최초 작성일: 2024-09-14
 * 수정일: 2024-09-28
 * 버전: 0.0.2
 * */
package com.cookandroid.test_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {
    /*
    * 변수명
    * singUpBtn(회원가입 버튼)
    * idFindBtn(아이디 찾기 버튼)
    * pwFindBtn(비밀번호 찾기 버튼)
    * loginBtn(로그인 버튼)
    * */
    AppCompatButton signUpBtn, idFindBtn, pwFindBtn;
    Button loginBtn;
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
        idFindBtn = (AppCompatButton) findViewById(R.id.IdFindBtn);
        idFindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), IdFind.class);
                startActivity(intent);
            }
        });
        pwFindBtn = (AppCompatButton) findViewById(R.id.PwFindBtn);
        pwFindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), PwFind.class);
                startActivity(intent);
            }
        });
        loginBtn = (Button) findViewById(R.id.LoginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 아이디와 비밀번호에 관련된 데이터를 가져오지 못했기 때문에
                // if문 제외하고 바로 연결하는 방식으로 처리 추후 수정 예정
                intent = new Intent(getApplicationContext(), MainPage.class);
                startActivity(intent);
            }
        });
    }
}