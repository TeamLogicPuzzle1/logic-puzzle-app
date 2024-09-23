/*
 * 간략: 공지사항 창
 * 최초 작성자: 안승민
 * 작성일: 2024-09-23
 * 버전: 0.0.1
 * */
package com.cookandroid.test_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingAnnounce extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_announce);
        /*
        * 변수명
        * ReturnSettingBtn (환경설정으로 돌아가는 버튼)*/
        ImageButton returnSettingBtn;
        returnSettingBtn = (ImageButton) findViewById(R.id.ReturnSettingBtn);
        returnSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), SettingAnnounce.class);
                startActivity(intent);
            }
        });
    }
}
