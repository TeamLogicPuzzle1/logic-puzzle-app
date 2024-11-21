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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cookandroid.test_ui.R;
import com.google.common.util.concurrent.ListenableFuture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class CamExpirationdate extends AppCompatActivity {
    View expirationdateView, expirationdateInputTextView;
    Intent intent;
    private PreviewView cameraExpirationdatePreviewView;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private String inputText, barcode, productName;
    private static final int REQUEST_CODE_PAGE_2 = 1;
    private AppCompatButton cameraExpirationDateBtn, inputDateBtn, returnCamBarcode;

    private ImageCapture imageCapture;  // 이미지 캡쳐 객체 추가
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cam_expirationdate);
        intent = getIntent();

        inputText = intent.getStringExtra("inputText");
        barcode = intent.getStringExtra("barcode");
        productName = intent.getStringExtra("productName");
        String apiResponse = intent.getStringExtra("apiResponse");

        // 바코드와 API 응답 데이터 확인
        Log.d("CamExpirationdate", "받은 바코드: " + barcode);
        Log.d("CamExpirationdate", "API 응답 데이터: " + apiResponse);

        // 상품 이름이 없는 경우 기본값 설정
        if (productName == null || productName.isEmpty()) {
            productName = "상품 이름 없음";
        }


        // API 응답 데이터를 파싱하여 UI 업데이트
        if (apiResponse != null) {
           try {
               JSONObject jsonObject = new JSONObject(apiResponse);
               JSONObject c005Object = jsonObject.getJSONObject("C005");
               JSONArray rowArray = c005Object.getJSONArray("row");

               // 첫 번째 상품의 이름 가져오기
               if(rowArray.length() > 0) {
                   JSONObject firstRow = rowArray.getJSONObject(0);
                   inputText = firstRow.optString("PRDLST_NM", inputText); // PRDLST_NM이 없으면 기존 inputText 유지
               }
           } catch (JSONException e) {
               Log.e("CamExpirationdate", "JSON 파싱 오류: " + e.getMessage());
           }
        } else {
            Toast.makeText(this, "API 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
        }
        expirationdateView = (View) findViewById(R.id.ExpirationdateView);
        expirationdateInputTextView = (View) findViewById(R.id.ExpirationdateInputTextView);

        cameraExpirationdatePreviewView = (PreviewView) findViewById(R.id.CameraExpirationdatePreviewView);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            startCameraPreview();
        }

        // 유통기한 버튼
        cameraExpirationDateBtn = (AppCompatButton) findViewById(R.id.CameraExpirationDateBtn);
        cameraExpirationDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                captureImage();
            }
        });


        // 직접 입력
        inputDateBtn = (AppCompatButton) findViewById(R.id.InputDateBtn);
        inputDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expirationdateInputTextView.setBackgroundColor(Color.parseColor("#ffffff"));
                expirationdateView.setBackgroundColor(Color.parseColor("#bdbdbd"));
                showDatePickerDialog();
            }
        });

        // 취소
        returnCamBarcode = (AppCompatButton) findViewById(R.id.ReturnCamBarcodeBtn);
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

                intent.putExtra("selectedDate", selectedDate);  // 날짜 전달
                intent.putExtra("inputText", inputText); // 최종 설정된 inputText 전달

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

                // 이미지 캡처 설정
                imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build();

                preview.setSurfaceProvider(cameraExpirationdatePreviewView.getSurfaceProvider());

                // 기존 카메라 바인딩 해제 후 새로 바인딩
                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture); // 이미지 캡처 추가
            } catch (ExecutionException | InterruptedException e) {
                Log.e("CameraActivity", "Camera initialization failed: " + e.getMessage());
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void captureImage() {
        if (imageCapture == null) {
            Log.e("CamExpirationdate", "ImageCapture is not initialized");
            return;
        }

        // 저장할 파일 생성
        File photoFile = new File(getExternalFilesDir(null), "captured_image_" + System.currentTimeMillis() + ".jpg");

        // 출력 옵션 설정
        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        // 이미지 캡처 및 저장
        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                Log.d("CamExpirationdate", "Image saved to: " + photoFile.getAbsolutePath());
                Toast.makeText(CamExpirationdate.this, "Image Saved: " + photoFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Log.e("CamExpirationdate", "Image capture failed: " + exception.getMessage());
                Toast.makeText(CamExpirationdate.this, "Image capture failed", Toast.LENGTH_SHORT).show();
            }
        });
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