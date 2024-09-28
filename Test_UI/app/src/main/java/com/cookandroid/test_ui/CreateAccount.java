/*
 * 간략: 회원가입 창
 * 최초 작성자: 홍진기
 * 작성일: 2024-09-14
 * 수정일: 2024-09-28
 * 버전: 0.0.2
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

public class CreateAccount extends AppCompatActivity {
    /*
    * 변수명 returnSignInBtn(회원가입 버튼)
    */
    // TextView certification_Timer;
    Button careturnSignInBtn, certificationNumberSendBtn;
    CountDownTimer countDownTimer;
    Intent intent;
    // boolean isTimerRunning = false;
    // long timeLeftInMillis = 180000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        careturnSignInBtn = (Button) findViewById(R.id.Ca_Return_SignIn_Btn);
        careturnSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
        /* certificationNumberSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isTimerRunning) {
                    startTimer();
                }else {

                }
            }
        });*/
    }

    /* private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                 isTimerRunning = false;
                 certificationNumberSendBtn.setEnabled(true);
                certification_Timer.setText("00:00");
            }

        };
        countDownTimer.start();
        isTimerRunning = true;
        certificationNumberSendBtn.setEnabled(false);
    }
    private void updateTimer() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    } */
}
