/*
 * 간략: 리더 환경 설정 창
 * 최초 작성자: 홍진기
 * 작성일: 2024-09-22
 * 버전: 0.0.1
 * */
package com.cookandroid.test_ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class SettingLeaderVer extends AppCompatActivity {
    Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        /*
        * 변수명
        * alarmSettingBtn (알람 설정으로 들어가는 버튼)*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_leader_ver);
        AppCompatButton alarmSettingBtn;
        alarmSettingBtn = (AppCompatButton) findViewById(R.id.AlarmSettingBtn);
        alarmSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), SettingAlarm.class);
                startActivity(intent);
            }
        });
    }
}
