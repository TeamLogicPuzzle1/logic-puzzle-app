/*
 * 간략: 유통기한
 * 최초 작성자: 홍진기
 * 작성일: 2024-10-06
 * 수정일: 2024-11-04
 * 버전: 0.0.4
 * */
package com.cookandroid.test_ui.mainPage;
import android.Manifest;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cookandroid.test_ui.R;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class CamExpirationdate extends AppCompatActivity {
    View expirationdateView, expirationdateInputTextView;
    Intent intent;
    private PreviewView cameraExpirationdatePreviewView;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private String inputText;
    private static final int REQUEST_CODE_PAGE_2 = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cam_expirationdate);
        intent = getIntent();
        inputText = intent.getStringExtra("inputText");

        expirationdateView = (View) findViewById(R.id.ExpirationdateView);
        expirationdateInputTextView = (View) findViewById(R.id.ExpirationdateInputTextView);

        cameraExpirationdatePreviewView = (PreviewView) findViewById(R.id.CameraExpirationdatePreviewView);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            startCameraPreview();
        }


        AppCompatButton inputDateBtn = (AppCompatButton) findViewById(R.id.InputDateBtn);
        inputDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expirationdateInputTextView.setBackgroundColor(Color.parseColor("#ffffff"));
                expirationdateView.setBackgroundColor(Color.parseColor("#bdbdbd"));
                showDatePickerDialog();
            }
        });
        AppCompatButton returnCamBarcode = (AppCompatButton) findViewById(R.id.ReturnCamBarcodeBtn);
        returnCamBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), CamBarcode.class);
                startActivity(intent);
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
                intent = new Intent(getApplicationContext(), MainPageTab.class);
                intent.putExtra("selectedDate", selectedDate);  // 선택한 날짜 전달
                intent.putExtra("inputText", inputText);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        }, year, month, day);

        // 취소 시 dismiss
        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "취소", (dialog, which) -> {
            expirationdateInputTextView.setBackgroundColor(Color.parseColor("#bdbdbd"));
            expirationdateView.setBackgroundColor(Color.parseColor("#ffffff"));
            dialog.dismiss();
        });

        // 다이얼로그 띄우기
        datePickerDialog.show();
    }
    private void startCameraPreview() {
        // 카메라 제공자 가져오기
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK) // 후면 카메라
                        .build();

                preview.setSurfaceProvider(cameraExpirationdatePreviewView.getSurfaceProvider());

                cameraProvider.unbindAll(); // 기존 카메라 바인딩 해제
                cameraProvider.bindToLifecycle(this, cameraSelector, preview); // 카메라 바인딩
            } catch (ExecutionException | InterruptedException e) {
                Log.e("CameraActivity", "Camera initialization failed: " + e.getMessage());
            }
        }, ContextCompat.getMainExecutor(this));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCameraPreview();
            } else {
                Toast.makeText(this, "Camera permission is required to use this feature", Toast.LENGTH_SHORT).show();
            }
        }
    }

}