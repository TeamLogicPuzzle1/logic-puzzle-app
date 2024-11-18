package com.cookandroid.test_ui.mainPage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.cookandroid.test_ui.R;

import java.io.IOException;
import java.lang.reflect.Array;

public class EditItemDialog extends DialogFragment implements View.OnClickListener{
    public EditItemDialog() {}
    private Uri imageUri;
    private int counter = 0;

    private ImageView editResourceImage;
    private EditText editTextNameEdt, editMemoEditText;
    private Spinner editSpinClassification, editSpinStorage;
    private Button editDateTimePickerBtn, editItemBtn;
    private TextView editCounterTextView;
    private ImageButton editCounterPlusBtn, editCounterMinusBtn, editBackIvBtn;

    private String selectedDate;
    // 전달받은 Product 객체
    private Product product;

    private OnProductEditedListener productEditedListener; // 리스너 변수 추가

    public interface OnProductEditedListener {
        void onProductEdited(Product product);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnProductEditedListener) {
            productEditedListener = (OnProductEditedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnProductEditedListener");
        }
    }

    public static EditItemDialog newInstance(Product product) {
        EditItemDialog editItemDialog = new EditItemDialog();
        Bundle args = new Bundle();
        args.putParcelable("product", product);
        editItemDialog.setArguments(args);
        return editItemDialog;
    }

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    try {
                        // 선택한 이미지를 Bitmap으로 변환하여 ImageView에 표시
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
                        editResourceImage.setImageBitmap(bitmap);
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    );
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_item, container,false);

        editSpinClassification = v.findViewById(R.id.EditSpinClassification);
        ArrayAdapter spin_adapter_class = ArrayAdapter.createFromResource(v.getContext(), R.array.classification,
                android.R.layout.simple_spinner_item);
        editSpinClassification.setAdapter(spin_adapter_class);

        editSpinStorage = v.findViewById(R.id.EditSpinStorage);
        ArrayAdapter spin_adapter_stor = ArrayAdapter.createFromResource(v.getContext(), R.array.storage_location,
                android.R.layout.simple_spinner_item);
        spin_adapter_stor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editSpinStorage.setAdapter(spin_adapter_stor);

        editResourceImage = v.findViewById(R.id.EditResourceImage);
        editTextNameEdt = v.findViewById(R.id.EditTextNameEdt);

        editDateTimePickerBtn = v.findViewById(R.id.EditDateTimePickerBtn);
        editCounterTextView = v.findViewById(R.id.EditCounterTextView);
        editCounterPlusBtn = v.findViewById(R.id.EditCounterPlusBtn);
        editCounterMinusBtn = v.findViewById(R.id.EditCounterMinusBtn);
        editBackIvBtn = v.findViewById(R.id.EditBackIvBtn);
        editMemoEditText = v.findViewById(R.id.EditMemoEditText);

        editItemBtn = v.findViewById(R.id.EditItemBtn);

        // 전달받은 Product 객체 가져오기
        if(getArguments() != null) {
            product = (Product) getArguments().getParcelable("product");
            if(product != null) {
                setDataToViews();
            }
        }

        editDateTimePickerBtn.setOnClickListener(view -> {
            DatePickerDialogFragment datePickerDialog = new DatePickerDialogFragment();
            datePickerDialog.setOnDateSetListener((year, month, day) -> {
                selectedDate = year + "." + month + "." + day;
                editDateTimePickerBtn.setText(selectedDate);

                // 선택한 날짜를 product 객체의 expirationDate로 설정
                if (product != null) {
                    product.setExpirationDate(selectedDate);
                }
            });

            FragmentManager fragmentManager = getParentFragmentManager();
            datePickerDialog.show(fragmentManager, "datePicker");
        });


        editCounterMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCounterTextView.setText(String.valueOf(counter--));
            }
        });

        editCounterPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCounterTextView.setText(String.valueOf(counter++));
            }
        });

        // 뒤로가기 버튼
        editBackIvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        editResourceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        editItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 수정된 데이터로 product 업데이트

                product.setName(editTextNameEdt.getText().toString());
                product.setMemo(editMemoEditText.getText().toString());
                product.setQuantity(Integer.parseInt(editCounterTextView.getText().toString()));
                if(selectedDate != null) {
                    product.setExpirationDate(selectedDate);
                }
                product.setCategory(editSpinClassification.getSelectedItem().toString());
                product.setLocation(editSpinStorage.getSelectedItem().toString());
                product.setImageUri(imageUri);


                // 리스너를 사용하여 변경된 product 전달
                if (productEditedListener != null) {
                    productEditedListener.onProductEdited(product);
                }
                dismiss();
            }

        });

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        productEditedListener = null; // 연결 해제 시 리스너 제거
    }

    private void setDataToViews() {
        editTextNameEdt.setText(product.getName());
        editMemoEditText.setText(product.getMemo());
        editDateTimePickerBtn.setText(product.getExpirationDate());
        editCounterTextView.setText(String.valueOf(product.getQuantity()));

        // 선택된 날짜 초기화
        selectedDate = product.getExpirationDate(); // 기존 소비기한으로 초기화

        // 분류 스피너 값 설정
        int classificationPosition = ((ArrayAdapter) editSpinClassification.getAdapter())
                .getPosition(product.getCategory());
        editSpinClassification.setSelection(classificationPosition);

        // 저장 위치 스피너 값 설정
        int storagePosition = ((ArrayAdapter) editSpinStorage.getAdapter())
                .getPosition(product.getLocation());
        editSpinStorage.setSelection(storagePosition);

        // 이미지 설정
        if (product.getImageUri() != null) {
            imageUri = product.getImageUri(); // 기존 이미지 URI 초기화
            editResourceImage.setImageURI(product.getImageUri());
        } else {
            editResourceImage.setImageResource(R.drawable.default_image);
        }
    }

    @Override
    public void onClick(View view) {

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }
}
