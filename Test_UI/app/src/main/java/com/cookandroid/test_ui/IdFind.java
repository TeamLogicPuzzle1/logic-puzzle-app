/*
 * 간략: 회원가입 창
 * 최초 작성자: 홍진기
 * 작성일: 2024-09-14
 * 수정일: 2024-10-01
 * 버전: 0.0.2
 * */
package com.cookandroid.test_ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class IdFind extends AppCompatActivity {
    // 변수명
    /*
    * ifRefurnSigninBtn(취소 버튼), idFindSendCodeBtn(인증번호 발송 버튼), idFindSendCodeCheckBtn(인증번호 확인 버튼)
    * idFindOkBtn(확인 버튼)
    * idFindCertification_Timer(3:00 타이머), idFindSendCodeCheckMessage(인증번호 확인 메시지)
    * idFindOkMessage(아이디 찾기 메시지) */
    Button ifReturnSignInBtn, idFindSendCodeBtn, idFindSendCodeCheckBtn, idFindOkBtn;
    TextView idFindCertification_Timer, idFindSendCodeCheckMessage, idFindOkMessage;
    Intent intent;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.id_find);
        ifReturnSignInBtn = (Button) findViewById(R.id.IF_Return_SignIn_Btn);
        ifReturnSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
        idFindSendCodeBtn = (Button) findViewById(R.id.IDFindSendCodeBtn);
        idFindCertification_Timer = (TextView) findViewById(R.id.IDFind_Certification_Timer);
        idFindSendCodeBtn.setOnClickListener(v-> {
            startTimer();
            idFindSendCodeBtn.setEnabled(false);
        });
        idFindSendCodeCheckBtn = (Button) findViewById(R.id.IDFindSendCodeCheckBtn);
        idFindSendCodeCheckMessage = (TextView) findViewById(R.id.IDSendCodeCheckMessage);
        idFindSendCodeCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 추후 수정 예정
                idFindSendCodeCheckBtn.setVisibility(View.INVISIBLE);
                idFindSendCodeCheckMessage.setVisibility(View.VISIBLE);
            }
        });
        idFindOkBtn = (Button) findViewById(R.id.IDFindOkBtn);
        idFindOkMessage = (TextView) findViewById(R.id.IDFindOkMessage);
        idFindOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 자바 코드로 추후 수정 예정
                idFindOkMessage.setVisibility(View.VISIBLE);
            }
        });
    }
    private void startTimer() {
        countDownTimer = new CountDownTimer(180000, 1000) {
            @Override
            public void onTick(long l) {
                long minutes = l / 60000;
                long seconds =  (l % 60000) / 1000;
                idFindCertification_Timer.setText(String.format("%02d:%02d", minutes, seconds));

            }

            @Override
            public void onFinish() {
                idFindCertification_Timer.setText("00:00");
                idFindSendCodeBtn.setEnabled(true);
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
