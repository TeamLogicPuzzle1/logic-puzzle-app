package com.cookandroid.test_ui;
import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.view.PreviewView;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class CamBarcode extends AppCompatActivity {
    Intent intent;
    EditText inputTextNameEdt;
    private PreviewView cameraBarcodePreviewView;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cam_barcode);
        AppCompatButton inputTextBtn = (AppCompatButton) findViewById(R.id.InputTextBtn);
        View barCodeView, barCodeInputTextView;

        cameraBarcodePreviewView = findViewById(R.id.CameraBarcodePreviewView);
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

                preview.setSurfaceProvider(cameraBarcodePreviewView.getSurfaceProvider());

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
