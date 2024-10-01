/*
 * 간략: 비밀번호 찾기 창
 * 최초 작성자: 홍진기
 * 작성일: 2024-09-18
 * 버전: 0.0.1
 * */
package com.cookandroid.test_ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PwFind extends AppCompatActivity {
    /*
    * 변수명
    * pf_Return_SignIn_Btn(비밀번호 찾기 창에 있는 취소 버튼)
    * */
    Intent intent;
    CountDownTimer countDownTimer;
    Button pf_Return_SignIn_Btn, pwFindSendCodeBtn, pwFindSendCodeCheck;
    TextView pwFindCertification_Timer;
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
        pwFindSendCodeBtn = (Button) findViewById(R.id.PWFindSendCodeBtn);
        pwFindCertification_Timer = (TextView) findViewById(R.id.PwFind_Certification_Timer);
        pwFindSendCodeBtn.setOnClickListener(v-> {
            startTimer();
            pwFindSendCodeBtn.setEnabled(false);
        });
        pwFindSendCodeCheck = (Button) findViewById(R.id.PWFindSendCodeCheck);
        pwFindSendCodeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), PwChange.class);
                startActivity(intent);
            }
        });

    }
    private void startTimer() {
        countDownTimer = new CountDownTimer(180000, 1000) {
            @Override
            public void onTick(long l) {
                long minutes = l / 60000;
                long seconds =  (l % 60000) / 1000;
                pwFindCertification_Timer.setText(String.format("%02d:%02d", minutes, seconds));

            }

            @Override
            public void onFinish() {
                pwFindCertification_Timer.setText("00:00");
                pwFindSendCodeBtn.setEnabled(true);
            }
        }.start();
    }
    private void Destory() {
        super.onDestroy();
        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
