package com.cookandroid.test_ui;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
@SuppressWarnings("deprecation")
public class CamBarcode extends AppCompatActivity {
    Intent intent;
    EditText inputTextNameEdt;
    private PreviewView cameraBarcodePreviewView;
    private BarcodeScanner barcodeScanner;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
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
                        Barcode.FORMAT_QR_CODE,
                        Barcode.FORMAT_CODE_128
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
                intent = new Intent(getApplicationContext(), main_page_tab.class);
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
                cameraProvider.bindToLifecycle(this, cameraSelector, preview); // 카메라 바인딩
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

                        // 바코드 처리 로직 (예: 스캔 후 이동)
                        intent = new Intent(getApplicationContext(), CamExpirationdate.class);
                        intent.putExtra("barcode", rawValue);
                        startActivity(intent);
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


}
