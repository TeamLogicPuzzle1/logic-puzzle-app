/*
 * 간략: 회원가입 창
 * 최초 작성자: 홍진기
 * 작성일: 2024-09-14
 * 수정일: 2024-09-28
 * 버전: 0.0.2
 * */
package com.cookandroid.test_ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.test_ui.DTO.UserCheckDto;
import com.cookandroid.test_ui.util.ApiInterface;
import com.cookandroid.test_ui.util.LogMsgOutput;
import com.cookandroid.test_ui.util.RetrofitClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccount extends AppCompatActivity {
    /*
    * 변수명 returnSignInBtn(회원가입 버튼)
    */
    ApiInterface api;
    RetrofitClient RetrofitClient;
    TextView certification_Timer, duplicateMessage, pwMatchMessage, sendCodeCheckMessage;
    EditText pwEdt, pwCheckEdt, userId;
    Button careturnSignInBtn, sendCodeBtn, duplicateCheckBtn, sendCodeCheckBtn;
    CountDownTimer countDownTimer;
    Intent intent;

    // boolean isTimerRunning = false;
    // long timeLeftInMillis = 180000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);


        api = RetrofitClient.getRetrofit().create(ApiInterface.class);

        // 취소버튼
        careturnSignInBtn = (Button) findViewById(R.id.Ca_Return_SignIn_Btn);
        careturnSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
        // 인증번호 타이머
        certification_Timer = (TextView) findViewById(R.id.Certification_Timer);
        sendCodeBtn = (Button) findViewById(R.id.SendCodeBtn);
        sendCodeBtn.setOnClickListener(v -> {
            startTimer();
            sendCodeBtn.setEnabled(false);
        });
        // 중복확인 버튼
        userId = (EditText) findViewById(R.id.UserId); // 아이디
        duplicateCheckBtn = (Button) findViewById(R.id.DuplicateCheckBtn);
        duplicateMessage = (TextView) findViewById(R.id.DuplicateMessage); // 중복확인 메시지
        duplicateCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserCheckDto userCheckDto = new UserCheckDto();
                String checkId = userId.getText().toString();
                userCheckDto.setUserId(userId.getText().toString());

                // duplicateMessage.setVisibility(View.VISIBLE);
                api.userCheckDto(checkId).enqueue(new Callback<UserCheckDto>() {
                    @Override
                    public void onResponse(Call<UserCheckDto> call, Response<UserCheckDto> response) {
                        if(response.isSuccessful()){
                            UserCheckDto responseData = response.body();
                            LogMsgOutput.logPrintOut(getApplicationContext(), "통신성공");
                            LogMsgOutput.logPrintOut(getApplicationContext(), "responseData : " + new Gson().toJson(responseData));
                            Log.v("@@@@@@@@@@@@@@@@@@","@@@@@@@@@@@@@@@@@@" + response.body().getData());
                            duplicateMessage.setVisibility(View.VISIBLE);
                            duplicateMessage.setText("사용가능한 아이디입니다.");
                            duplicateMessage.setTextColor(Color.rgb(124, 179, 66)); // 초록색


                        } else {
                            duplicateMessage.setVisibility(View.VISIBLE);
                            duplicateMessage.setText("이미 사용중인 아이디입니다.");
                            duplicateMessage.setTextColor(Color.RED);
                        }

                    }

                    @Override
                    public void onFailure(Call<UserCheckDto> call, Throwable t) {
                        LogMsgOutput.logPrintOut(getApplicationContext(), "통신실패");
                        LogMsgOutput.logPrintOut(getApplicationContext(), "Throwable : " + t.getMessage());
                        call.cancel();
                    }
                });
            }
        });
        // 비밀번호와 비밀번호 확인
        pwEdt = (EditText) findViewById(R.id.PwEdt); // 비밀번호 입력칸
        pwCheckEdt = (EditText) findViewById(R.id.PwCheckEdt);  // 비밀번호 확인 입력칸
        pwMatchMessage = (TextView) findViewById(R.id.PwMatchMessage);
        pwCheckEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkPasswords();
            }
        });
        // 인증번호 확인 버튼
        sendCodeCheckBtn = (Button) findViewById(R.id.SendCodeCheckBtn);
        // 인증번호 확인 메시지
        sendCodeCheckMessage = (TextView) findViewById(R.id.SendCodeCheckMessage);
        sendCodeCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCodeCheckMessage.setVisibility(View.VISIBLE);
            }
        });

    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(180000, 1000) {
            @Override
            public void onTick(long l) {
                long minutes = l / 60000;
                long seconds =  (l % 60000) / 1000;
                certification_Timer.setText(String.format("%02d:%02d", minutes, seconds));

            }

            @Override
            public void onFinish() {
                certification_Timer.setText("00:00");
                sendCodeBtn.setEnabled(true);
            }
        }.start();
    }
    private void Destory() {
        super.onDestroy();
        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    // 비밀번호가 일치하는지 확인하는 메서드
    private void checkPasswords() {
        String password = pwEdt.getText().toString();
        String confirm = pwCheckEdt.getText().toString();

        if (password.equals(confirm) && !password.isEmpty()) {
            // 비밀번호가 일치하면 메시지를 보이게 설정
            pwMatchMessage.setVisibility(TextView.VISIBLE);
            pwMatchMessage.setText("비밀번호가 일치합니다.");
            pwMatchMessage.setTextColor(Color.rgb(124, 179, 66)); // 초록색
        } else {
            // 일치하지 않으면 메시지를 숨김
            pwMatchMessage.setVisibility(TextView.VISIBLE);
            pwMatchMessage.setText("비밀번호가 일치하지 않습니다.");
            pwMatchMessage.setTextColor(Color.RED);
        }
    }
}
