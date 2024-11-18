/*
 * 간략: 바코드 창
 * 최초 작성자: 홍진기
 * 작성일: 2024-10-05
 * 수정일: 2024-11-09
 * 버전: 0.0.5
 * */
package com.cookandroid.test_ui.mainPage;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.view.PreviewView;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.cookandroid.test_ui.R;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@SuppressWarnings("deprecation")
public class CamBarcode extends AppCompatActivity {
    Intent intent;
    EditText inputTextNameEdt;
    private PreviewView cameraBarcodePreviewView;
    private BarcodeScanner barcodeScanner;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private boolean isActivityStarted = false;  // 중복 실행 방지 플래그 추가
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cam_barcode);
        AppCompatButton inputTextBtn = (AppCompatButton) findViewById(R.id.InputTextBtn);
        View barCodeView, barCodeInputTextView;

        // PreviewView 초기화
        cameraBarcodePreviewView = findViewById(R.id.CameraBarcodePreviewView);
        BarcodeScannerOptions options = new BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                        Barcode.FORMAT_ALL_FORMATS // 모든 바코드 형식을 허용
                ).build();
        barcodeScanner = BarcodeScanning.getClient(options);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            startCameraPreview();
        }

        barCodeView = (View) findViewById(R.id.BarCodeView);
        barCodeInputTextView = (View) findViewById(R.id.BarCodeInputTextView);
        inputTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barCodeInputTextView.setBackgroundColor(Color.parseColor("#ffffff"));
                barCodeView.setBackgroundColor(Color.parseColor("#bdbdbd"));
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
                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        barCodeInputTextView.setBackgroundColor(Color.parseColor("#bdbdbd"));
                        barCodeView.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                });
                dlg.show();
            }
        });
        AppCompatButton returnMenuBtn = findViewById(R.id.ReturnMenuBtn);
        returnMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainPageTab.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

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

                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1280, 720))  // 해상도 설정
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

                imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new ImageAnalysis.Analyzer() {
                    @Override
                    public void analyze(@NonNull ImageProxy imageProxy) {
                        // 이미지 분석을 위한 ML Kit 사용
                        processImageProxy(imageProxy);
                    }
                });

                preview.setSurfaceProvider(cameraBarcodePreviewView.getSurfaceProvider());

                cameraProvider.unbindAll(); // 기존 카메라 바인딩 해제
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis); // 카메라 바인딩
            } catch (ExecutionException | InterruptedException e) {
                Log.e("CamBarcode", "Camera initialization failed: " + e.getMessage());
            }
        }, ContextCompat.getMainExecutor(this));
    }
    @OptIn(markerClass = androidx.camera.core.ExperimentalGetImage.class)
    private void processImageProxy(ImageProxy imageProxy) {
        @SuppressLint("UnsafeOptInUsageError")
        InputImage image = InputImage.fromMediaImage(imageProxy.getImage(), imageProxy.getImageInfo().getRotationDegrees());

        // ML Kit을 사용하여 바코드 스캔
        barcodeScanner.process(image)
                .addOnSuccessListener(barcodes -> {
                    // 바코드 스캔 성공 시 처리
                    for (Barcode barcode : barcodes) {
                        String rawValue = barcode.getRawValue();
                        Log.d("CamBarcode", "바코드 인식: " + rawValue);

                        // Open-API 호출
                        fetchBarcodeData(rawValue);

                    }
                })
                .addOnFailureListener(e -> {
                    // 바코드 스캔 실패 시 처리
                    Log.e("CamBarcode", "바코드 스캔 실패: " + e.getMessage());
                })
                .addOnCompleteListener(task -> {
                    // 이미지 분석 후 이미지 해제
                    imageProxy.close();
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
    private void fetchBarcodeData(String barcode) {
        // OPEN API 요청 URL 구성
        String keyId = "c1b0d2cc4219416e9ff3";  // Open-API에서 발급받은 인증키 입력
        String serviceId = "C005";  // 서비스 ID
        String dataType = "json";   // 응답 데이터 형식
        int startIdx = 1;   // 시작 인덱스
        int endIdx = 5;     // 종료 인덱스
        String apiUrl = String.format(
                "http://openapi.foodsafetykorea.go.kr/api/%s/%s/%s/%d/%d/BAR_CD=%s",
                keyId, serviceId, dataType, startIdx, endIdx, barcode
        );

        // OkHttp 클라이언트 생성
        OkHttpClient client = new OkHttpClient();

        // 요청 생성
        Request request = new Request.Builder()
                .url(apiUrl)
                .build();

        // 비동기 요청사항
        new Thread(() -> {
            try {
                Response response = client.newCall(request).execute();
                if(response.isSuccessful()) {
                    // API 응답 처리
                    String responseBody = response.body().string();
                    Log.d("CamBarcode", "API 응답: " + responseBody);

                    // UI 업데이트 (예: 데이터를 화면에 표시)
                    runOnUiThread(() -> {
                        if (!isActivityStarted) {
                            isActivityStarted = true; // 플래그 설정

                            // Open-API 응답 데이터를 Intent로 전달
                            Intent intent = new Intent(getApplicationContext(), CamExpirationdate.class);
                            intent.putExtra("barcode", barcode);
                            intent.putExtra("apiResponse", responseBody);
                            startActivity(intent);

                            Toast.makeText(CamBarcode.this, "API 호출 성공", Toast.LENGTH_SHORT).show();
                        }

                        Toast.makeText(CamBarcode.this, "API 호출 성공", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    Log.e("CamBarcode", "API 호출 실패: " + response.message());
                }
            }catch (Exception e) {
                Log.e("CamBarcode", "API 호출 중 오류 발생: " + e.getMessage());
            }

        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityStarted = false; // 플래그 초기화
    }
}

