package com.cookandroid.test_ui.mainPage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.cookandroid.test_ui.R;

import java.util.HashSet;
import java.util.Set;

public class RefrigeratorFoodFilterDialog extends DialogFragment implements View.OnClickListener {

    public interface OnFilterAppliedListener {
        void onFiltersApplied(Set<String> filters);
    }

    public RefrigeratorFoodFilterDialog(){

    }

    public RefrigeratorFoodFilterDialog getInstance(Context context) {
        RefrigeratorFoodFilterDialog refrigeratorFoodFilterDialog = new RefrigeratorFoodFilterDialog();
        return refrigeratorFoodFilterDialog;
    }

    private OnFilterAppliedListener filterAppliedListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFilterAppliedListener) {
            filterAppliedListener = (OnFilterAppliedListener) context;
        }
        super.onAttach(context);
    }

    private AppCompatButton refrigeratorBtn, freezerBtn, roomTemeratureBtn, meatBtn, seaFoodBtn,
    vegetableBtn, fruitBtn, dairyProductBtn, softDrinkBtn, etcBtn, unclassifiedBtn;
    private Button cancelBtn, checkFilterBtn;

    private Set<String> selectedFilters = new HashSet<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.refrigerator_food_filter, container, false);

        refrigeratorBtn = v.findViewById(R.id.RefrigeratorBtn); // 냉장고 버튼
        freezerBtn = v.findViewById(R.id.FreezerBtn);   // 냉동고 버튼
        roomTemeratureBtn = v.findViewById(R.id.RoomTemperatureBtn);    // 실온 버튼
        meatBtn = v.findViewById(R.id.MeatBtn); // 고기버튼
        seaFoodBtn = v.findViewById(R.id.SeaFoodBtn);   // 해산물 버튼
        vegetableBtn = v.findViewById(R.id.VegetableBtn);   // 야채 버튼
        fruitBtn = v.findViewById(R.id.FruitBtn);   // 과일 버튼
        dairyProductBtn = v.findViewById(R.id.DairyProductBtn); //  유제품 버튼
        softDrinkBtn = v.findViewById(R.id.SoftDrinkBtn);   //  음료 버튼
        etcBtn = v.findViewById(R.id.EtcBtn);   // 기타 버튼
        unclassifiedBtn = v.findViewById(R.id.UnclassifiedBtn); // 미분류 버튼

        // 버튼 클릭 이벤트
        // 위치
        refrigeratorBtn.setOnClickListener(view -> toggleFilter(refrigeratorBtn, "냉장"));
        freezerBtn.setOnClickListener(view -> toggleFilter(freezerBtn, "냉동"));
        roomTemeratureBtn.setOnClickListener(view -> toggleFilter(roomTemeratureBtn, "실온"));
        // 재료
        meatBtn.setOnClickListener(view -> toggleFilter(meatBtn, "고기"));
        seaFoodBtn.setOnClickListener(view -> toggleFilter(seaFoodBtn, "해산물"));
        vegetableBtn.setOnClickListener(view -> toggleFilter(vegetableBtn, "야채"));
        fruitBtn.setOnClickListener(view -> toggleFilter(fruitBtn, "과일"));
        dairyProductBtn.setOnClickListener(view -> toggleFilter(dairyProductBtn, "유제품"));
        softDrinkBtn.setOnClickListener(view -> toggleFilter(softDrinkBtn, "음료"));
        etcBtn.setOnClickListener(view -> toggleFilter(etcBtn, "기타"));
        unclassifiedBtn.setOnClickListener(view -> toggleFilter(unclassifiedBtn, "미분류"));



        // 취소버튼
        cancelBtn = v.findViewById(R.id.CancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        // 확인버튼
        checkFilterBtn = v.findViewById(R.id.CheckFilterBtn);
        // 확인 버튼에서 데이터 전달
        checkFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filterAppliedListener != null) {
                    filterAppliedListener.onFiltersApplied(selectedFilters);
                    Log.d("SelectedFilters", "Filters: " + selectedFilters.toString());
                }
                dismiss();
            }
        });
        return v;
    }

    // 토글 필터 메소드
    private void toggleFilter(Button button, String filter) {
        if (selectedFilters.contains(filter)) {
            selectedFilters.remove(filter);
            button.setSelected(false); // 선택 해제
        } else {
            selectedFilters.add(filter);
            button.setSelected(true);  // 선택 표시
        }
    }

    @Override
    public void onClick(View view) {

    }
}
