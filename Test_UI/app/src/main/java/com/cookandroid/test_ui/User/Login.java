/*
 * 간략: 로그인 창
 * 최초 작성자: 홍진기
 * 최초 작성일: 2024-09-14
 * 수정일: 2024-09-28
 * 버전: 0.0.2
 * */
package com.cookandroid.test_ui.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cookandroid.test_ui.DTO.response.AuthResLoginDto;
import com.cookandroid.test_ui.DTO.request.AuthReqLoginDto;
import com.cookandroid.test_ui.R;
import com.cookandroid.test_ui.mainPage.MainPageTab;
import com.cookandroid.test_ui.util.ApiInterface;
import com.cookandroid.test_ui.util.LogMsgOutput;
import com.cookandroid.test_ui.util.TokenManger;
import com.cookandroid.test_ui.util.UserManger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    /*
     * 변수명
     * singUpBtn(회원가입 버튼)
     * idFindBtn(아이디 찾기 버튼)
     * pwFindBtn(비밀번호 찾기 버튼)
     * loginBtn(로그인 버튼)
     * */ ApiInterface api;
    com.cookandroid.test_ui.util.RetrofitClient RetrofitClient;
    AppCompatButton signUpBtn, idFindBtn, pwFindBtn;
    Button loginBtn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        signUpBtn = (AppCompatButton) findViewById(R.id.SignUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), CreateAccount.class);
                startActivity(intent);
            }
        });
        idFindBtn = (AppCompatButton) findViewById(R.id.IdFindBtn);
        idFindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), IdFind.class);
                startActivity(intent);
            }
        });
        pwFindBtn = (AppCompatButton) findViewById(R.id.PwFindBtn);
        pwFindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), PwFind.class);
                startActivity(intent);
            }
        });
        loginBtn = (Button) findViewById(R.id.LoginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 아이디와 비밀번호에 관련된 데이터를 가져오지 못했기 때문에
                // if문 제외하고 바로 연결하는 방식으로 처리 추후 수정 예정
                // 그리고 바로 프로필 선택창으로 연결하는 작업이 필요
                AuthReqLoginDto authReqLoginDto = new AuthReqLoginDto();
                authReqLoginDto.setMemberId(1);
                authReqLoginDto.setPinNum(774900);

                api = RetrofitClient.getRetrofit().create(ApiInterface.class);

                api.authLoginDto(authReqLoginDto).enqueue(new Callback<AuthResLoginDto>() {
                    @Override
                    public void onResponse(Call<AuthResLoginDto> call, Response<AuthResLoginDto> response) {
                        AuthResLoginDto responseData = response.body();
                        if (response.isSuccessful()) {
                            LogMsgOutput.logPrintOut(getApplicationContext(), "login success");
                            //LogMsgOutput.logPrintOut(getApplicationContext(), "responseData : " + new Gson().toJson(responseData.getTokenDto().getAccess().toString()));
                            LogMsgOutput.logPrintOut(getApplicationContext(), "responseData : " + response.raw().body());

                            UserManger userManger = UserManger.getInstance(getApplicationContext());

                            userManger.setId(responseData.getUserInfoDto().getId());
                            userManger.setUserId(responseData.getUserInfoDto().getUserId());
                            userManger.setProfileName(responseData.getUserInfoDto().getProfileName());
                            userManger.setLeaderYn(responseData.getUserInfoDto().getLeaderYn());

                            Log.d("Id = ", "Id : " + UserManger.getId());
                            Log.d("UserId = ", "userId : " + UserManger.getUserId());
                            Log.d("ProfileName = ", "profileName : " + UserManger.getProfileName());
                            Log.d("LeaderYn = ", "leaderYn : " + UserManger.getLeaderYn());

                            TokenManger tokenManger = TokenManger.getInstance(getApplicationContext());

                            tokenManger.setAccessToken(responseData.getTokenDto().getAccess().toString());
                            tokenManger.setRefreshToken(responseData.getTokenDto().getRefresh().toString());

                            Log.d("AccessToken = ", "accessToken : " + TokenManger.getAccessToken());

                            RetrofitClient.setAccessToken(TokenManger.getAccessToken());
                        } else {
                            LogMsgOutput.logPrintOut(getApplicationContext(), "login fail @@@");
                            LogMsgOutput.logPrintOut(getApplicationContext(), "responseData : " + response.raw().body());
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResLoginDto> call, Throwable t) {
                        LogMsgOutput.logPrintOut(getApplicationContext(), "login fail === ");
                        LogMsgOutput.logPrintOut(getApplicationContext(), "Throwable : " + t.getMessage());
                        call.cancel();
                    }
                });

                intent = new Intent(getApplicationContext(), MainPageTab.class);
                startActivity(intent);
                // intent = new Intent(getApplicationContext(), ProfileSelect.class );


            }
        });
    }
}