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
