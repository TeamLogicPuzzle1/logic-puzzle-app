/*
 * 간략: 메인페이지 창
 * 최초 작성자: 홍진기
 * 최초 작성일: 2024-09-28
 * 수정일:
 * 버전: 0.0.1
 * */
package com.cookandroid.test_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainPage extends AppCompatActivity {
    private Intent intent;
    private ImageButton settingBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        settingBtn = (ImageButton) findViewById(R.id.SettingBtn);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), SettingLeaderVer.class);
                startActivity(intent);
            }
        });

    }
}
