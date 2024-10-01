/*
 * 간략: 리더 환경 설정 창
 * 최초 작성자: 홍진기
 * 작성일: 2024-09-22
 * 수정일: 2024-09-30
 * 수정자: 안승민
 * 버전: 0.0.4
 *
 * */
package com.cookandroid.test_ui;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class SettingLeaderVer extends AppCompatActivity {
    Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        /*
        * 변수명
        * alarmSettingBtn (알람 설정으로 들어가는 버튼)
        * announceBtm(공지사항 설정으로 들어가는 버튼)
        * */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_leader_ver);
        AppCompatButton alarmSettingBtn, announceBtn, settingPwChangeBtn;
        ImageButton settingBackHomeBtn;
        alarmSettingBtn = (AppCompatButton) findViewById(R.id.AlarmSettingBtn);
        alarmSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), SettingAlarm.class);
                startActivity(intent);
            }
        });

        announceBtn = (AppCompatButton) findViewById(R.id.AnnounceBtn);
        announceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), SettingAnnounce.class);
                startActivity(intent);
            }
        });

        settingBackHomeBtn = (ImageButton) findViewById(R.id.SettingBackHomeBtn);
        settingBackHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), main_page_tab.class);
                startActivity(intent);
            }
        });

        settingPwChangeBtn = (AppCompatButton) findViewById(R.id.SettingPwChagneBtn);
        settingPwChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingPwChange();
            }
        });

    }

    private void SettingPwChange() {
        Dialog settingPwChangeDialog = new Dialog(this);

        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.setting_pw_change, null);
        settingPwChangeDialog.setContentView(v);
        if(settingPwChangeDialog != null && settingPwChangeDialog.getWindow() != null) {
            settingPwChangeDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        Button settingBtnReturn = v.findViewById(R.id.ReturnSettingBtn);
        settingBtnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingPwChangeDialog.dismiss();
            }
        });

        settingPwChangeDialog.show();
    }
}
