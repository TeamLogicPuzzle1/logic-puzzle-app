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
            if (getParentFragment() instanceof OnFilterAppliedListener) {
                filterAppliedListener = (OnFilterAppliedListener) getParentFragment();
            } else {
                throw new RuntimeException("Parent fragment must implement OnFilterAppliedListener");
            }
        }


        private AppCompatButton refrigeratorBtn, freezerBtn, roomTemeratureBtn, meatBtn, seaFoodBtn,
        vegetableBtn, fruitBtn, dairyProductBtn, softDrinkBtn, etcBtn, unclassifiedBtn;
        private Button cancelBtn, checkFilterBtn;

        private Set<String> selectedFilters = new HashSet<>();

        private Set<String> selectedLocations = new HashSet<>();
        private Set<String> selectedCategories = new HashSet<>();

        public void setSelectedFilters(Set<String> filters) {
            this.selectedFilters = new HashSet<>(filters); // 이전 필터값 저장

            // 필터를 위치와 분류로 나누어 저장
            for (String filter : filters) {
                if (filter.equals("냉장고") || filter.equals("냉동고") || filter.equals("실온")) {
                    selectedLocations.add(filter);
                } else {
                    selectedCategories.add(filter);
                }
            }
        }
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.refrigerator_food_filter, container, false);

            refrigeratorBtn = v.findViewById(R.id.RefrigeratorBtn); // 냉장고 버튼
            freezerBtn = v.findViewById(R.id.FreezerBtn);   // 냉동고 버튼
            roomTemeratureBtn = v.findViewById(R.id.RoomTemperatureBtn);    // 실온 버튼
            meatBtn = v.findViewById(R.id.MeatBtn); // 고기 버튼
            seaFoodBtn = v.findViewById(R.id.SeaFoodBtn);   // 해산물 버튼
            vegetableBtn = v.findViewById(R.id.VegetableBtn);   // 야채 버튼
            fruitBtn = v.findViewById(R.id.FruitBtn);   // 과일 버튼
            dairyProductBtn = v.findViewById(R.id.DairyProductBtn); //  유제품 버튼
            softDrinkBtn = v.findViewById(R.id.SoftDrinkBtn);   //  음료 버튼
            etcBtn = v.findViewById(R.id.EtcBtn);   // 기타 버튼
            unclassifiedBtn = v.findViewById(R.id.UnclassifiedBtn); // 미분류 버튼

            // 버튼 클릭 이벤트 수정
            refrigeratorBtn.setOnClickListener(view -> toggleFilter(refrigeratorBtn, "냉장고", true));
            freezerBtn.setOnClickListener(view -> toggleFilter(freezerBtn, "냉동고", true));
            roomTemeratureBtn.setOnClickListener(view -> toggleFilter(roomTemeratureBtn, "실온", true));

            meatBtn.setOnClickListener(view -> toggleFilter(meatBtn, "고기", false));
            seaFoodBtn.setOnClickListener(view -> toggleFilter(seaFoodBtn, "해산물", false));
            vegetableBtn.setOnClickListener(view -> toggleFilter(vegetableBtn, "야채", false));
            fruitBtn.setOnClickListener(view -> toggleFilter(fruitBtn, "과일", false));
            dairyProductBtn.setOnClickListener(view -> toggleFilter(dairyProductBtn, "유제품", false));
            softDrinkBtn.setOnClickListener(view -> toggleFilter(softDrinkBtn, "음료", false));
            etcBtn.setOnClickListener(view -> toggleFilter(etcBtn, "기타", false));
            unclassifiedBtn.setOnClickListener(view -> toggleFilter(unclassifiedBtn, "미분류", false));



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
            checkFilterBtn.setOnClickListener(view -> {
                if (filterAppliedListener != null) {
                    Set<String> combinedFilters = new HashSet<>();
                    combinedFilters.addAll(selectedLocations);
                    combinedFilters.addAll(selectedCategories);

                    // 아무 필터도 선택되지 않은 경우
                    if (combinedFilters.isEmpty()) {
                        Log.d("RefrigeratorFoodFilter", "No filters selected. Displaying all items.");
                        // 전체 데이터를 표시하기 위해 특별한 값을 전달
                        filterAppliedListener.onFiltersApplied(new HashSet<>()); // 빈 필터를 전달
                    } else {
                        Log.d("SelectedLocations", "Locations: " + selectedLocations);
                        Log.d("SelectedCategories", "Categories: " + selectedCategories);
                        filterAppliedListener.onFiltersApplied(combinedFilters);
                    }
                }
                dismiss();
            });

            applySelectedFilters();
            return v;
        }


        private void applySelectedFilters() {
            // 위치 필터 버튼 상태 적용
            toggleButtonState(refrigeratorBtn, selectedLocations.contains("냉장고"));
            toggleButtonState(freezerBtn, selectedLocations.contains("냉동고"));
            toggleButtonState(roomTemeratureBtn, selectedLocations.contains("실온"));

            // 분류 필터 버튼 상태 적용
            toggleButtonState(meatBtn, selectedCategories.contains("고기"));
            toggleButtonState(seaFoodBtn, selectedCategories.contains("해산물"));
            toggleButtonState(vegetableBtn, selectedCategories.contains("야채"));
            toggleButtonState(fruitBtn, selectedCategories.contains("과일"));
            toggleButtonState(dairyProductBtn, selectedCategories.contains("유제품"));
            toggleButtonState(softDrinkBtn, selectedCategories.contains("음료"));
            toggleButtonState(etcBtn, selectedCategories.contains("기타"));
            toggleButtonState(unclassifiedBtn, selectedCategories.contains("미분류"));
        }



        private void toggleButtonState(Button button, boolean isSelected) {
            button.setSelected(isSelected); // 선택 상태 설정
            if (isSelected) {
                button.setBackgroundResource(R.drawable.rectangle_circle_selected); // 선택된 배경 색상
            } else {
                button.setBackgroundResource(R.drawable.rectangle_circle_default); // 기본 배경 색상
            }
        }


        // 토글 필터 메소드
        private void toggleFilter(Button button, String filter, boolean isLocation) {
            Set<String> targetSet = isLocation ? selectedLocations : selectedCategories;

            if (targetSet.contains(filter)) {
                // 선택 해제
                targetSet.remove(filter);
                toggleButtonState(button, false); // 버튼 시각적 상태를 비활성화
            } else {
                // 선택 활성화
                targetSet.add(filter);
                toggleButtonState(button, true); // 버튼 시각적 상태를 활성화
            }
        }


        @Override
        public void onClick(View view) {

        }
    }
