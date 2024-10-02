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
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
        AppCompatButton alarmSettingBtn, announceBtn, settingPwChangeBtn, deleteMemberBtn;
        ImageButton settingBackHomeBtn;
        alarmSettingBtn = (AppCompatButton) findViewById(R.id.AlarmSettingBtn);
        alarmSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), SettingAlarm.class);
                startActivity(intent);
            }
        });
        // 공지사항 버튼
        announceBtn = (AppCompatButton) findViewById(R.id.AnnounceBtn);
        announceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), SettingAnnounce.class);
                startActivity(intent);
            }
        });
        // 이전 버튼
        settingBackHomeBtn = (ImageButton) findViewById(R.id.SettingBackHomeBtn);
        settingBackHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), main_page_tab.class);
                startActivity(intent);
            }
        });
        // 비밀번호 변경 버튼 팝업창
        settingPwChangeBtn = (AppCompatButton) findViewById(R.id.SettingPwChagneBtn);
        settingPwChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingPwChange();
            }
        });

        deleteMemberBtn = (AppCompatButton) findViewById(R.id.DeleteMemberBtn);
        deleteMemberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingMyPasswordCheck();
            }
        });

    }
    // 비밀번호 변경 팝업 창
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
        EditText settingNewPwEdt, settingNewPwCheckEdt;
        settingNewPwEdt = v.findViewById(R.id.SettingNewPwEdt);
        settingNewPwCheckEdt = v.findViewById(R.id.SettingNewPwCheckEdt);
        TextView settingNewPwMatchMessage = v.findViewById(R.id.SettingNewPwMatchMessage);
        settingNewPwCheckEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkPassword(settingNewPwEdt, settingNewPwCheckEdt, settingNewPwMatchMessage);
            }
        });


        settingPwChangeDialog.show();
        }
        private void checkPassword(EditText settingNewPwEdt, EditText settingNewPwCheckEdt, TextView settingNewPwMatchMessage) {
            String password = settingNewPwEdt.getText().toString();
            String confirm = settingNewPwCheckEdt.getText().toString();

            if (password.equals(confirm) && !password.isEmpty()) {
                settingNewPwMatchMessage.setVisibility(TextView.VISIBLE);
                settingNewPwMatchMessage.setText("비밀번호가 일치합니다.");
                settingNewPwMatchMessage.setTextColor(Color.rgb(124, 179, 66));  // 초록색
            } else {
                settingNewPwMatchMessage.setVisibility(TextView.VISIBLE);
                settingNewPwMatchMessage.setText("비밀번호가 일치하지 않습니다.");
                settingNewPwMatchMessage.setTextColor(Color.RED);
            }
        }

        private void SettingMyPasswordCheck() {
            Dialog settingMyPasswordCheck = new Dialog(this);

            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.setting_my_password_check, null);
            settingMyPasswordCheck.setContentView(v);
            if(settingMyPasswordCheck != null && settingMyPasswordCheck.getWindow() != null) {
                settingMyPasswordCheck.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
            Button returnSettingBtn = v.findViewById(R.id.ReturnSettingBtn);
            returnSettingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    settingMyPasswordCheck.dismiss();
                }
            });
            Button deleteBtnOk = v.findViewById(R.id.DeleteBtnOk);
            deleteBtnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SettingDeleteAccount();
                }
            });
            settingMyPasswordCheck.show();
        }

        private void SettingDeleteAccount() {
            Dialog settingDeleteAccount = new Dialog(this);

            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.setting_delete_account, null);
            settingDeleteAccount.setContentView(v);
            if(settingDeleteAccount != null && settingDeleteAccount.getWindow() != null) {
                settingDeleteAccount.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
            Button returnDeleteCheckBtn = v.findViewById(R.id.ReturnDeleteCheckBtn);
            returnDeleteCheckBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    settingDeleteAccount.dismiss();
                }
            });

            Button deleteAccount = v.findViewById(R.id.DeleteAccount);
            deleteAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "계정이 탈퇴되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent1);
                }
            });
            settingDeleteAccount.show();
        }
}


