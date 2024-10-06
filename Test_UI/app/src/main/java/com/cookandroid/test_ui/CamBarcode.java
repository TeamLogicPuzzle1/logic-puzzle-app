package com.cookandroid.test_ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class CamBarcode extends AppCompatActivity {
    Intent intent;
    EditText inputTextNameEdt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cam_barcode);
        AppCompatButton inputTextBtn = (AppCompatButton) findViewById(R.id.InputTextBtn);
        inputTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View inputTextNameDialog = View.inflate(CamBarcode.this, R.layout.input_text_name, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(CamBarcode.this);
                dlg.setTitle("상품명을 입력해주세요.");
                dlg.setView(inputTextNameDialog);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        inputTextNameEdt = inputTextNameDialog.findViewById(R.id.InputTextNameEdt);
                        String inputText = inputTextNameEdt.getText().toString();

                        intent  = new Intent(getApplicationContext(), CamExpirationdate.class);
                        intent.putExtra("inputText", inputText);
                        startActivity(intent);
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

    }


}
