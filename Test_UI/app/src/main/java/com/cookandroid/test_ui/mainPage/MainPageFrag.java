/*
 * 간략: 메인페이지 1번 상품 등록 및 조회 창
 * 최초 작성자: 홍진기
 * 작성일: 2024-09-27
 * 수정일: 2024-11-23
 * 수정자: 박시형
 * 수정이유: 레시피추천시 로직 추가
 * 버전: 0.2.0
 * */
package com.cookandroid.test_ui.mainPage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.test_ui.DTO.request.Product;
import com.cookandroid.test_ui.R;
import com.cookandroid.test_ui.setting.SettingLeaderVer;
import com.cookandroid.test_ui.util.ApiInterface;
import com.cookandroid.test_ui.util.RetrofitClient;
import com.cookandroid.test_ui.util.TokenManger;
import com.cookandroid.test_ui.util.UserManger;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("deprecation")

public class MainPageFrag extends Fragment implements ProductAdapter.SelectionModeListener, EditItemDialog.OnProductEditedListener, RefrigeratorFoodFilterDialog.OnFilterAppliedListener,
AddItemDialog.OnDataPassListener{

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ProductViewModel productViewModel;
    private ArrayList<Product> productList = new ArrayList<>();
    private ProductFileManager productFileManager;
    private boolean adapterInitialized = false;
    private static final int REQUEST_CODE_PAGE_2 = 1;
    ApiInterface api;
    RetrofitClient RetrofitClient;
    private Intent intent;      // 인텐트 선언

    private AppCompatButton recipeProductButton, deleteProductButton, imminentExpirationDate, consumptionExpirationDate;
    private EditText productSearch;
    // 필터 다이얼로그 호출코드 수정
    private Set<String> currentFilters = new HashSet<>();

    private boolean isImminentSelected = false; // 처음 임박상품과 만료상품의 초기 상태
    private boolean isConsumptionSelected = false;
    private static boolean isThreeTenABPInitialized = false;

    @Override
    public void onDataPass(String name, String category, String location, int quantity, String expirationDate, Uri imageUri, String memo) {
        // 올바른 순서로 Product 객체 생성
        Product newProduct = new Product(name, category, location, quantity, expirationDate, imageUri, memo);

        // 상품 추가
        productViewModel.addProduct(newProduct);

        // RecyclerView 업데이트
        productAdapter.notifyItemInserted(productViewModel.getProductList().getValue().size() - 1);

        // 소비기한 임박상품 카운트 업데이트
        updateImminentExpirationCount(); // 추가된 상품 반영

        // 소비기한 만료상품 카운트 업데이트
        updateExpiredProductCount();
    }


    @Override
    public void onProductEdited(Product product) {
        //ViewModel의 리스트에서 수정된 항목을 갱신
        productViewModel.updateProduct(product);
        productAdapter.notifyDataSetChanged();
    }

    // 냉장고 필터
    @Override
    public void onFiltersApplied(Set<String> filters) {
        Log.d("MainPageFrag", "적용된 필터: " + filters);
        currentFilters = filters;
        applyFilters(filters);
    }



    // 적용된 필더 데이터를
    private void applyFilters(Set<String> filters) {
        Log.d("MainPageFrag", "적용된 필터: " + filters);

        List<Product> allProducts = productViewModel.getProductList().getValue();
        if (allProducts == null) return;

        // 필터가 비어 있으면 전체 데이터를 표시
        if (filters.isEmpty()) {
            productAdapter.updateProducts(allProducts);
            Log.d("MainPageFrag", "No filters applied. Displaying all items.");
            return;
        }

        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : allProducts) {
            Log.d("FilterCheck", "Product Location: " + product.getLocation());
            Log.d("FilterCheck", "Product Category: " + product.getCategory());

            // 위치와 분류를 각각 확인
            boolean matchesLocation = false;
            for (String filter : filters) {
                if (product.getLocation() != null && product.getLocation().toLowerCase().contains(filter.toLowerCase())) {
                    matchesLocation = true;
                    break;
                }
            }

            boolean matchesCategory = filters.contains(product.getCategory());

            // 위치와 분류 중 하나라도 매칭되면 추가
            if (matchesLocation || matchesCategory) {
                filteredProducts.add(product);
            }
        }

        productAdapter.updateProducts(filteredProducts);
        Log.d("FilterResults", "Filtered items count: " + filteredProducts.size());
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        productFileManager = new ProductFileManager(requireContext());

        // ViewModel 초기화
        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);

        // ViewModel에 저장된 데이터가 비어있는 경우에만 파일에서 불러오기
        if (productViewModel.getProductList().getValue() == null || productViewModel.getProductList().getValue().isEmpty()) {
            List<Product> loadedProductList = productFileManager.loadProductList();
            if (loadedProductList != null) {
                productViewModel.restoreProductList(loadedProductList);
                productList.addAll(loadedProductList); // productList에 로드된 데이터 추가
            }
        } else {
            productList.addAll(productViewModel.getProductList().getValue());
        }

    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_page_frag, container, false);
        api = RetrofitClient.getRetrofit().create(ApiInterface.class);

        UserManger.init(requireContext().getApplicationContext());
        TokenManger.init(requireContext().getApplicationContext());

        // 뒤로가기 버튼을 막는 코드 추가
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        });

        // RecyclerView 초기화
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // ProductAdapter 생성 및 RecyclerView에 설정
        productAdapter = new ProductAdapter(new ArrayList<>(productList), this, productViewModel);
        recyclerView.setAdapter(productAdapter);

        // ViewModel 옵저버 설정
        productViewModel.getProductList().observe(getViewLifecycleOwner(), products -> {
            productAdapter.updateProducts(products);
            updateImminentExpirationCount(); // 상품 목록 변경 시 즉시 소비기한 임박상품 카운트 업데이트
            updateExpiredProductCount();    // 상품 목록 변경 시 즉시 소비기한 만료상품 카운트 업데이트
            Product product = new Product();


            // 지우지 말것
            /* String accessToken = TokenManger.getAccessToken();
>>>>>>> 74ae59baabc7859e771e3849c7a16e9129b18383
            String authorizationHeader = "Bearer " + accessToken;

            String userId = null;// UserManger.getUserId();

            // xml에서 데이터 값 가져오기
            String name = null;//product.getName();

            api.productsListDto(authorizationHeader, userId, null, null, null, null).enqueue(new Callback<List<ProductsResDto>>() {
                @Override
                public void onResponse(Call<List<ProductsResDto>> call, Response<List<ProductsResDto>> response) {
                    if (response.isSuccessful()) {
                        List<ProductsResDto> responseData = response.body();
                        if (responseData != null) {
                            for (ProductsResDto product : responseData) {
                                Log.d("@@@@@@@@@@@@@@@@@@", "@@@@@@@@@@@@@@@@@@" + product);
                            }
                        }
                    } else {
                        Log.d("=@@@@@@@@@@@@@@@@@@  ", "통신성공 @@@@");
                    }
                }

                @Override
                public void onFailure(Call<List<ProductsResDto>> call, Throwable t) {
                    Log.d("통신 실패 : ", "@@@@@@@@@@@@@@@@@@");
                    call.cancel();
                }
            }); */

        });
        // 뒤로가기 버튼을 막는 코드 추가
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        });

        // RecyclerView 초기화
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // ProductAdapter 생성 및 RecyclerView에 설정
        productAdapter = new ProductAdapter(new ArrayList<>(productList), this, productViewModel);
        recyclerView.setAdapter(productAdapter);

        // ViewModel 옵저버 설정
        productViewModel.getProductList().observe(getViewLifecycleOwner(), products -> {
            Log.d("MainPageFrag", "Observer triggered - Product count: " + products.size());
            productAdapter.updateProducts(products);
        });
        adapterInitialized = true;

        // 설정 버튼
        ImageButton settingBtn = v.findViewById(R.id.SettingBtn);
        settingBtn.setOnClickListener(view -> {
            intent = new Intent(getActivity(), SettingLeaderVer.class);
            startActivity(intent);
        });

        // 추가 버튼
        ImageButton addItem = v.findViewById(R.id.AddItem);
        addItem.setOnClickListener(view -> {
            intent = new Intent(getActivity(), CamBarcode.class);
            startActivity(intent);
        });

        // 냉장고 필터 버튼
        ImageButton refrigeratorFoodFilterCheck = v.findViewById(R.id.RefrigeratorFoodFilterCheck);
        refrigeratorFoodFilterCheck.setOnClickListener(view -> {


            showFilterDialog();
        });

        // ThreeTenABP 초기화 (안전한 초기화)
        if (!isThreeTenABPInitialized) {
            AndroidThreeTen.init(requireContext());
            isThreeTenABPInitialized = true;
        }

        // 상품 검색(조회)창
        productSearch = v.findViewById(R.id.ProductSearch);
        productSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // 입력값 변경 시 필터링 호출
                productAdapter.filter(charSequence.toString());


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // 버튼 초기화
        recipeProductButton = v.findViewById(R.id.RecipeProductButton);
        deleteProductButton = v.findViewById(R.id.DeleteProductButton);

        recipeProductButton.setVisibility(View.INVISIBLE);
        deleteProductButton.setVisibility(View.INVISIBLE);

        deleteProductButton.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("상품 삭제")
                    .setMessage("선택하신 상품을 삭제 하시겠습니까?")
                    .setNegativeButton("취소", (dialog, which) -> {
                        // 취소 버튼 클릭 시 실행할 코드

                    })
                    .setPositiveButton("확인", (dialog, which) -> {
                        List<Product> deletedProducts = productAdapter.removeSelectedItems(); // Adapter에서 삭제된 항목 가져오기
                        productViewModel.removeProducts(deletedProducts); // ViewModel에서 해당 항목 삭제
                        productFileManager.saveProductList(new ArrayList<>(productViewModel.getProductList().getValue())); // 파일에도 변경된 리스트 저장
                    });

            AlertDialog dialog = builder.create();
            dialog.show();

        });

        recipeProductButton.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("레시피 찾기")
                    .setMessage("선택하신 상품에 대한 레시피를 찾겠습니까?")
                    .setNegativeButton("취소", (dialog, which) -> {
                        // 취소 버튼 클릭 시 실행할 코드
                    })
                    .setPositiveButton("확인", (dialog, which) -> {
                        // 선택한 상품 이름 가져오기
                        List<Product> selectedProducts = productAdapter.getSelectedItems();
                        if (selectedProducts.isEmpty()) {
                            AlertDialog noSelectionAlert = new AlertDialog.Builder(requireContext())
                                    .setTitle("알림")
                                    .setMessage("상품을 선택해주세요.")
                                    .setPositiveButton("확인", null)
                                    .create();
                            noSelectionAlert.show();
                            return;
                        }

                        // 선택된 상품 이름 리스트 생성
                        ArrayList<String> productNames = new ArrayList<>();
                        for (Product product : selectedProducts) {
                            productNames.add(product.getName());
                        }

                        // 레시피 프래그먼트로 이동
                        Fragment recipeFragment = new MainPageFrag2();

                        // 데이터 전달을 위한 Bundle 생성
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("productNames", productNames);
                        recipeFragment.setArguments(bundle);

                        // Fragment 전환
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, recipeFragment) // fragment_container는 Activity의 컨테이너 ID
                                .addToBackStack(null) // 이전 화면으로 돌아가기 위한 백스택 추가
                                .commit();
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(productAdapter, requireContext()));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        productAdapter.setOnItemClickListener(product -> {
            showEditItemDialog(product);
        });


        imminentExpirationDate = v.findViewById(R.id.ImminentExpirationDate);
        // 소비기한 임박상품 버튼 클릭
        imminentExpirationDate.setOnClickListener(view -> {
            isImminentSelected = !isImminentSelected; // 상태 토글
            updateButtonState(imminentExpirationDate, isImminentSelected); // 버튼의 UI 업데이트

            if (isImminentSelected) {
                // 필터 활성화: 상품 목록 필터링
                filterImminentProducts(); // 필터링만 수행
            } else {
                // 필터 해제: 전체 목록 복원
                List<Product> allProducts = productViewModel.getProductList().getValue();
                if (allProducts != null) {
                    productAdapter.updateProducts(allProducts); // 전체 제품 목록으로 복원
                }
            }
        });

        consumptionExpirationDate = v.findViewById(R.id.ConsumptionExpirationDate);
    // 소비기한 만료상품 버튼 클릭
        consumptionExpirationDate.setOnClickListener(view -> {
            isConsumptionSelected = !isConsumptionSelected; // 상태 토글
            updateButtonState(consumptionExpirationDate, isConsumptionSelected); // UI 업데이트
            Log.d("MainPageFrag", "ConsumptionExpirationDate selected: " + isConsumptionSelected);

            if (isConsumptionSelected) {
                filterExpiredProducts(); // 만료 상품 필터링만 수행
            } else {
                // 필터 해제 시 전체 목록 표시
                List<Product> allProducts = productViewModel.getProductList().getValue();
                if (allProducts != null) {
                    productAdapter.updateProducts(allProducts);
                }
            }
        });

        return v;
    }

    public boolean isAdapterInitialized() {
        return adapterInitialized;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (productViewModel.getProductList().getValue() != null) {
            outState.putParcelableArrayList("productList", new ArrayList<>(productViewModel.getProductList().getValue()));
        }
    }

    public void updateProductList(List<Product> products) {
        if (adapterInitialized && productAdapter != null) {
            productAdapter.updateProducts(products);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (productList != null) {
            productFileManager.saveProductList(productList);
        } else {
            Log.e("MainPageFrag", "productList is null during onPause. Initializing a new ArrayList.");
            productList = new ArrayList<>();
        }
    }

    public ProductAdapter getProductAdapter() {
        return productAdapter;
    }

    // 지워도 될것
    /* @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == REQUEST_CODE_PAGE_2 && resultCode == RESULT_OK && data != null) {
            String newProductData = data.getStringExtra("productList");
            if(newProductData != null) {
                productAdapter.notifyItemInserted(productList.size() -1);
            }
        }
    } */
    @Override
    public void onSelectionModeChanged(boolean isSelectionMode) {
        // 선택 모드일 때만 버튼 표시
        int visibility = isSelectionMode ? View.VISIBLE : View.INVISIBLE;
        recipeProductButton.setVisibility(visibility);
        deleteProductButton.setVisibility(visibility);

        deleteProductButton.setVisibility(isSelectionMode ? View.VISIBLE : View.GONE);
    }

    // MainPageFrag 클래스 내부
    @Override
    public void onProductRemoved(int position) {
        // 항목 삭제 후 추가 작업이 필요할 경우 이곳에 작성합니다.
        Log.d("MainPageFrag", "Product removed at position: " + position);
    }

    // EditItemDialog를 표시하는 메서드
    private void showEditItemDialog(Product product) {

        EditItemDialog editItemDialog = new EditItemDialog();

        // 데이터를 전달하기 위해 Bundle 사용
        Bundle args = new Bundle();
        args.putParcelable("product", product);
        editItemDialog.setArguments(args);

        // DialogFragment를 표시
        FragmentManager fragmentManager = getParentFragmentManager();
        editItemDialog.show(fragmentManager, "EditItemDialog");
    }

    private void showFilterDialog() {
        RefrigeratorFoodFilterDialog filterDialog = new RefrigeratorFoodFilterDialog();

        // 현재 필터값 전달
        filterDialog.setSelectedFilters(currentFilters);

        // 다이얼로그 표시
        filterDialog.show(getChildFragmentManager(), "RefrigeratorFoodFilterDialog");
    }
    // 버튼 색깔 적용
    private void updateButtonState(AppCompatButton button, boolean isSelected) {
        if (isSelected) {
            button.setBackgroundResource(R.drawable.rectangle_selected); // 선택된 배경
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white)); // 텍스트 색상 변경
        } else {
            button.setBackgroundResource(R.drawable.rectangle_default); // 기본 배경
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.black)); // 기본 텍스트 색상
        }
    }

    private int countOrFilterImminentProducts(boolean isFilter) {
        List<Product> allProducts = productViewModel.getProductList().getValue();
        if (allProducts == null) return 0;

        org.threeten.bp.LocalDate today = org.threeten.bp.LocalDate.now();
        List<Product> filteredProducts = new ArrayList<>();
        int count = 0;

        for (Product product : allProducts) {
            String expirationDateStr = product.getExpirationDate();
            if (expirationDateStr != null && !expirationDateStr.isEmpty()) {
                try {
                    org.threeten.bp.LocalDate expirationDate = org.threeten.bp.LocalDate.parse(
                            expirationDateStr, org.threeten.bp.format.DateTimeFormatter.ofPattern("yyyy.MM.dd")
                    );
                    if (!expirationDate.isBefore(today.plusDays(1)) && !expirationDate.isAfter(today.plusDays(7))) {
                        count++;
                        if (isFilter) filteredProducts.add(product);
                    }
                } catch (org.threeten.bp.format.DateTimeParseException e) {
                    Log.e("DateParseError", "유통기한 파싱 실패: " + expirationDateStr, e);
                }
            }
        }

        if (isFilter) productAdapter.updateProducts(filteredProducts);
        return count;
    }



    public void updateImminentExpirationCount() {
        // ViewModel에서 현재 상품 목록 가져오기
        List<Product> allProducts = productViewModel.getProductList().getValue();
        if (allProducts == null || allProducts.isEmpty()) {
            // 상품이 없으면 0으로 표시
            imminentExpirationDate.post(() ->
                    imminentExpirationDate.setText("소비기한 임박상품 0")
            );
            return;
        }

        // 오늘 날짜 가져오기 (ThreeTenABP 사용)
        org.threeten.bp.LocalDate today = org.threeten.bp.LocalDate.now();
        int count = 0;

        for (Product product : allProducts) {
            String expirationDateStr = product.getExpirationDate();
            if (expirationDateStr != null && !expirationDateStr.isEmpty()) {
                try {
                    org.threeten.bp.LocalDate expirationDate = org.threeten.bp.LocalDate.parse(
                            expirationDateStr, org.threeten.bp.format.DateTimeFormatter.ofPattern("yyyy.MM.dd")
                    );
                    // 유통기한이 1~7일 이내인 경우 카운트 증가
                    if (!expirationDate.isBefore(today.plusDays(1)) && !expirationDate.isAfter(today.plusDays(7))) {
                        count++;
                    }
                } catch (org.threeten.bp.format.DateTimeParseException e) {
                    Log.e("DateParseError", "유통기한 파싱 실패: " + expirationDateStr, e);
                }
            }
        }

        // UI 업데이트
        final int finalCount = count; // 사실상 final 변수로 선언
        imminentExpirationDate.post(() ->
                imminentExpirationDate.setText("소비기한 임박상품 " + finalCount)
        );
    }

    private void filterImminentProducts() {
        countOrFilterImminentProducts(true);
    }


    private int countOrFilterExpiredProducts(boolean isFilter) {
        List<Product> allProducts = productViewModel.getProductList().getValue();
        if (allProducts == null) return 0;

        org.threeten.bp.LocalDate today = org.threeten.bp.LocalDate.now();
        List<Product> filteredProducts = new ArrayList<>();
        int count = 0;

        for (Product product : allProducts) {
            String expirationDateStr = product.getExpirationDate();
            Log.d("DateCheck", "원본 유통기한: " + expirationDateStr); // 원본 유통기한 확인
            if (expirationDateStr != null && !expirationDateStr.isEmpty()) {
                try {
                    // 날짜 보정 후 파싱
                    String normalizedDateStr = normalizeDate(expirationDateStr);
                    org.threeten.bp.LocalDate expirationDate = org.threeten.bp.LocalDate.parse(
                            normalizedDateStr, org.threeten.bp.format.DateTimeFormatter.ofPattern("yyyy.MM.dd")
                    );
                    Log.d("ParsedDate", "보정된 유통기한: " + normalizedDateStr + " -> " + expirationDate);

                    // 오늘 날짜와 이전 날짜 포함
                    if (!expirationDate.isAfter(today)) { // expirationDate <= today
                        count++;
                        if (isFilter) filteredProducts.add(product);
                    }
                } catch (org.threeten.bp.format.DateTimeParseException e) {
                    Log.e("DateParseError", "유통기한 파싱 실패: " + expirationDateStr, e);
                }
            }
        }

        if (isFilter) {
            productAdapter.updateProducts(filteredProducts);
        }
        return count;
    }


    // 소비기한 만료 상품 수 업데이트
    public void updateExpiredProductCount() {
        List<Product> allProducts = productViewModel.getProductList().getValue();
        if (allProducts == null || allProducts.isEmpty()) {
            consumptionExpirationDate.post(() ->
                    consumptionExpirationDate.setText("소비기한 만료상품 0")
            );
            return;
        }

        org.threeten.bp.LocalDate today = org.threeten.bp.LocalDate.now();
        int count = 0;

        for (Product product : allProducts) {
            String expirationDateStr = product.getExpirationDate();
            Log.d("DateCheck", "유통기한 확인: " + expirationDateStr); // 유통기한 값 확인
            if (expirationDateStr != null && !expirationDateStr.isEmpty()) {
                try {
                    // 유통기한 문자열을 보정
                    String normalizedDate = normalizeDate(expirationDateStr);
                    org.threeten.bp.LocalDate expirationDate = org.threeten.bp.LocalDate.parse(
                            normalizedDate, org.threeten.bp.format.DateTimeFormatter.ofPattern("yyyy.MM.dd")
                    );
                    Log.d("ParsedDate", "파싱된 유통기한: " + expirationDate);

                    // 오늘 날짜와 이전 날짜 포함
                    if (!expirationDate.isAfter(today)) { // expirationDate <= today
                        count++;
                    }
                } catch (org.threeten.bp.format.DateTimeParseException e) {
                    Log.e("DateParseError", "유통기한 파싱 실패: " + expirationDateStr, e);
                }
            }
        }

        final int finalCount = count;
        Log.d("FinalCount", "소비기한 만료상품 개수: " + finalCount);
        consumptionExpirationDate.post(() ->
                consumptionExpirationDate.setText("소비기한 만료상품 " + finalCount)
        );
    }


    private void filterExpiredProducts() {
        countOrFilterExpiredProducts(true);
    }

    // 날짜 정확하게 지정
    private String normalizeDate(String dateStr) {
        // 날짜가 "yyyy.M.d"처럼 한 자리인 경우 0을 추가하여 보정
        String[] parts = dateStr.split("\\.");
        if (parts.length == 3) {
            String year = parts[0];
            String month = parts[1].length() == 1 ? "0" + parts[1] : parts[1]; // 월(MM)이 한 자리인 경우 0 추가
            String day = parts[2].length() == 1 ? "0" + parts[2] : parts[2];   // 일(DD)이 한 자리인 경우 0 추가
            return year + "." + month + "." + day;
        }
        return dateStr; // 유효하지 않은 형식은 그대로 반환
    }

}

