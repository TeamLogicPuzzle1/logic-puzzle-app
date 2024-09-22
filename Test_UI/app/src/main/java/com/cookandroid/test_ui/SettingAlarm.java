/*
 * 간략: 알람 설정 창
 * 최초 작성자: 홍진기
 * 작성일: 2024-09-21
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

public class SettingAlarm extends AppCompatActivity {
    /*
    * 변수명
    * SettingBackBtn (환경 설정 이전으로 돌아가는 버튼)*/
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_alarm);
        ImageButton settingBackBtn;
        settingBackBtn = (ImageButton) findViewById(R.id.SettingBackBtn);
        settingBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), SettingLeaderVer.class);
                startActivity(intent);
            }
        });
    }
}
