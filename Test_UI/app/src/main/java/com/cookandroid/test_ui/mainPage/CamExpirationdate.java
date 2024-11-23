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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.cookandroid.test_ui.DTO.reponse.ProductsExpirationDateResDto;
import com.cookandroid.test_ui.R;
import com.cookandroid.test_ui.util.ApiInterface;
import com.cookandroid.test_ui.util.RetrofitClient;
import com.cookandroid.test_ui.util.TokenManger;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CamExpirationdate extends AppCompatActivity {
    View expirationdateView, expirationdateInputTextView;
    Intent intent;
    private PreviewView cameraExpirationdatePreviewView;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private String inputText, barcode, productName;
    private static final int REQUEST_CODE_PAGE_2 = 1;
    private AppCompatButton cameraExpirationDateBtn, inputDateBtn, returnCamBarcode;

    private ImageCapture imageCapture;  // 이미지 캡쳐 객체 추가

    ApiInterface api;

    public interface ImageCaptureCallback {
        void onImageSaved(String imagePath);
        void onError(String errorMessage);
    }

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

        // 카메라 권한 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            startCameraPreview();
        }

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
        cameraExpirationDateBtn.setOnClickListener(view -> {
            /*captureImage(new ImageCaptureCallback() {
                @Override
                public void onImageSaved(String imagePath) {
                    Log.d("Image Capture", "Captured image path: " + imagePath);

                    // API 요청을 위한 파일 준비
                    MultipartBody.Part imagePart = prepareFilePart(imagePath);
                    if (imagePart == null) {
                        Toast.makeText(CamExpirationdate.this, "파일 준비 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }


                        api = RetrofitClient.getRetrofit().create(ApiInterface.class);


                    String accessToken = TokenManger.getAccessToken();
                    String authorizationHeader = "Bearer " + accessToken;

                    api.productsExpircationDateResDto(authorizationHeader, imagePart).enqueue(new Callback<ProductsExpirationDateResDto>() {
                        @Override
                        public void onResponse(Call<ProductsExpirationDateResDto> call, Response<ProductsExpirationDateResDto> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                String expirationDate = response.body().getExpirationDate();
                                Log.d("API 요청 성공", "유통기한: " + expirationDate);

                                // Intent로 MainPageTab에 데이터 전달
                                Intent intent = new Intent(CamExpirationdate.this, MainPageTab.class);
                                intent.putExtra("inputText", inputText); // 최종 설정된 inputText 전달
                                intent.putExtra("expirationDate", expirationDate); // 유통기한 데이터 전달
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP); // 기존 액티비티 재사용
                                startActivity(intent);
                            } else {
                                Log.e("API 요청 실패", "응답 코드: " + response.code());
                                Toast.makeText(CamExpirationdate.this, "API 요청 실패. 응답 코드: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ProductsExpirationDateResDto> call, Throwable t) {
                            Log.e("API 요청 실패", "네트워크 오류: " + t.getMessage());
                            Toast.makeText(CamExpirationdate.this, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onError(String errorMessage) {
                    Log.e("ImageCapture", "Image capture failed: " + errorMessage);
                    Toast.makeText(CamExpirationdate.this, "Image capture failed", Toast.LENGTH_SHORT).show();
                }
            }); */
            captureImage(new ImageCaptureCallback() {
                @Override
                public void onImageSaved(String imagePath) {
                    Log.d("Image Capture", "Captured image path: " + imagePath);

                    // 이미지 파일에서 Bitmap 생성
                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                    if (bitmap != null) {
                        performOCR(bitmap); // OCR 수행
                    } else {
                        Toast.makeText(CamExpirationdate.this, "이미지를 로드할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    Log.e("ImageCapture", "Image capture failed: " + errorMessage);
                    Toast.makeText(CamExpirationdate.this, "이미지 캡처 실패!", Toast.LENGTH_SHORT).show();
                }
            });

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
        cameraExpirationDateBtn = findViewById(R.id.CameraExpirationDateBtn);
        cameraExpirationDateBtn.setEnabled(true); // 초기 상태에서 버튼 비활성화
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

                imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build();

                preview.setSurfaceProvider(cameraExpirationdatePreviewView.getSurfaceProvider());

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);

                Log.d("CameraActivity", "Camera preview started.");
                cameraExpirationDateBtn.setEnabled(true); // 초기화 완료 후 버튼 활성화
            } catch (ExecutionException | InterruptedException e) {
                Log.e("CameraActivity", "Camera initialization failed: " + e.getMessage());
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void captureImage(@NonNull ImageCaptureCallback callback) {
        if (imageCapture == null) {
            Log.e("CamExpirationdate", "ImageCapture is not initialized");
            callback.onError("ImageCapture is not initialized");
            return;
        }

        // 이미지 저장 파일 생성
        File photoFile = new File(getExternalFilesDir(null), "captured_image_" + System.currentTimeMillis() + ".jpg");
        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        // 이미지 캡처 및 저장
        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                if (photoFile.exists()) {
                    Log.d("CamExpirationdate", "Image saved to: " + photoFile.getAbsolutePath());
                    callback.onImageSaved(photoFile.getAbsolutePath()); // 성공 콜백
                } else {
                    Log.e("CamExpirationdate", "Image file does not exist after saving.");
                    callback.onError("Image file does not exist after saving.");
                }
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Log.e("CamExpirationdate", "Image capture failed: " + exception.getMessage());
                callback.onError("Image capture failed: " + exception.getMessage());
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
                Toast.makeText(this, "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                // 대체 액션 유도
            }
        }
    }

    private MultipartBody.Part prepareFilePart(String filePath) {
        File file = new File(filePath);

        // 파일 존재 여부 확인
        if (!file.exists()) {
            Log.e("CamExpirationdate", "파일이 존재하지 않습니다: " + filePath);
            return null;
        }

        RequestBody requestFile = RequestBody.create(file, okhttp3.MediaType.parse("image/jpeg"));
        return MultipartBody.Part.createFormData("image", file.getName(), requestFile);
    }

    private void performOCR(Bitmap bitmap) {
        // TextRecognizer 초기화
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        InputImage image = InputImage.fromBitmap(bitmap, 0);

        recognizer.process(image)
                .addOnSuccessListener(result -> {
                    String extractedText = result.getText();
                    Log.d("OCR Result", "Extracted Text: " + extractedText);

                    if (extractedText.isEmpty()) {
                        Toast.makeText(this, "OCR 결과가 없습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        String expirationDate = parseExpirationDate(extractedText);

                        // Intent로 MainPageTab에 데이터 전달
                        Intent intent = new Intent(CamExpirationdate.this, MainPageTab.class);
                        intent.putExtra("inputText", inputText); // 최종 설정된 inputText 전달
                        intent.putExtra("expirationDate", expirationDate); // 유통기한 데이터 전달
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP); // 기존 액티비티 재사용
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("OCR Error", "Failed to process image", e);
                    Toast.makeText(this, "OCR 실패!", Toast.LENGTH_SHORT).show();
                });
    }

    // 유통기한 파싱
    private String parseExpirationDate(String text) {
        // 날짜 형식을 탐지하는 정규식
        String regex = "\\b(\\d{4}[.-]\\d{1,2}[.-]\\d{1,2})\\b|\\b(\\d{1,2}[.-]\\d{1,2}[.-]\\d{4})\\b";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(); // 첫 번째 매칭된 날짜 반환
        }
        return "유통기한을 찾을 수 없습니다.";
    }


}