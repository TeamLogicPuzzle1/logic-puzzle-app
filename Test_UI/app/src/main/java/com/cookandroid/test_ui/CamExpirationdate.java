package com.cookandroid.test_ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

public class CamExpirationdate extends AppCompatActivity {

    String inputText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cam_expirationdate);

        Intent intent = getIntent();
        String inputText = intent.getStringExtra("inputText");


        AppCompatButton inputDateBtn = (AppCompatButton) findViewById(R.id.InputDateBtn);
        inputDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDatePickerDialog();
            }
        });
    }

    // 날짜 선택 Dialog 표시
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // 날짜 선택 후 확인 버튼을 눌렀을 때 main_page_tab으로 이동
                String selectedDate = year + "." + (month + 1) + "." + day;

                // main_page_tab으로 이동하고 AddItem 팝업창 띄우기
                Intent intent = new Intent(CamExpirationdate.this, main_page_tab.class);
                intent.putExtra("selectedDate", selectedDate); // 선택한 날짜 전달
                intent.putExtra("inputText", inputText); // inputText 전달
                startActivity(intent);
            }
        }, year, month, day);

        // 취소 시 dismiss
        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "취소", (dialog, which) -> {
            dialog.dismiss();
        });

        // 다이얼로그 띄우기
        datePickerDialog.show();
    }
}
