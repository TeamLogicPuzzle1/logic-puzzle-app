package com.cookandroid.test_ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings("deprecation")
public class PwChange extends AppCompatActivity {
    Intent intent;
    Button returnPwFind, pwChangeBtn;
    EditText newPwEdt, newPwCheckEdt;
    TextView newPwMatchMessage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pw_change);
        Button returnPwFind = (Button) findViewById(R.id.ReturnPwFind);
        returnPwFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), PwFind.class);
                startActivity(intent);
            }
        });
        newPwEdt = (EditText) findViewById(R.id.NewPwEdt);
        newPwCheckEdt = (EditText) findViewById(R.id.NewPwCheckEdt);
        newPwMatchMessage = (TextView) findViewById(R.id.NewPwMatchMessage);

        newPwCheckEdt.addTextChangedListener(new TextWatcher() {
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

        pwChangeBtn = (Button) findViewById(R.id.PwChangeBtn);
        pwChangeBtn.setOnClickListener(new View.OnClickListener() {
            // 자바코드 추후 수정 예정
            @Override
            public void onClick(View view) {
                Toast.makeText(PwChange.this, "비밀번호 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

    }
    // 비밀번호가 일치하는지 확인하는 메서드
    private void checkPasswords() {
        String password = newPwEdt.getText().toString();
        String confirm = newPwCheckEdt.getText().toString();

        if (password.equals(confirm) && !password.isEmpty()) {
            // 비밀번호가 일치하면 메시지를 보이게 설정
            newPwMatchMessage.setVisibility(TextView.VISIBLE);
            newPwMatchMessage.setText("비밀번호가 일치합니다.");
            newPwMatchMessage.setTextColor(Color.rgb(124, 179, 66)); // 초록색
        } else {
            // 일치하지 않으면 메시지를 숨김
            newPwMatchMessage.setVisibility(TextView.VISIBLE);
            newPwMatchMessage.setText("비밀번호가 일치하지 않습니다.");
            newPwMatchMessage.setTextColor(Color.RED);
        }
    }
}
