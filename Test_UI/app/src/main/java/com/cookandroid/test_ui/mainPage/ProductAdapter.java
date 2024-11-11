/*
 * 간략: 상품등록 팝업창
 * 최초 작성자: 홍진기
 * 작성일: 2024-10-28
 * 수정일: 2024-11-09
 * 버전: 0.0.5
 * */
package com.cookandroid.test_ui.mainPage;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.test_ui.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private ArrayList<Product> productList;
    private boolean isSelectionMode = false;    // 선택 모드인지 여부
    private List<Integer> selectedItems = new ArrayList<>();   // 선택된 항목의 인덱스
    SelectionModeListener selectionModeListener;
    private ProductViewModel productViewModel;
    private ProductFileManager productFileManager;
    private OnItemClickListener onItemClickListener;

    // ProductAdapter에 인터페이스 정의
    public interface SelectionModeListener {
        void onSelectionModeChanged(boolean isSelectionMode);
        void onProductRemoved(int position);
    }

    public interface OnItemClickListener{
        void onItemClick(Product product);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    // 생성자
    public ProductAdapter(ArrayList<Product> initialProductList, SelectionModeListener listener, ProductViewModel viewModel) {
        this.productList = initialProductList != null ? initialProductList : new ArrayList<>(); // 전달된 리스트가 null이 아닐 때만 사용
        this.selectionModeListener = listener;
        this.productViewModel = viewModel;
    }

    // 새 상품 추가
    public void addProduct(Product product) {
        productList.add(product);
        notifyItemInserted(productList.size() - 1); // 리스트에 새 항목 추가
    }
    // 선택 모드 설정 메서드
    public void setSelectionMode(boolean selectionMode) {
        // 선택 모드 상태 업데이트
        if (isSelectionMode != selectionMode) {
            isSelectionMode = selectionMode;
            notifyDataSetChanged(); // 전체 업데이트
        }

        // 선택 모드 변경을 리스너에 알림
        if (selectionModeListener != null) {
            selectionModeListener.onSelectionModeChanged(selectionMode);
        }
    }




    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(productList.get(position), position); // bind 메서드를 통해 데이터 설정
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void updateProducts(List<Product> products) {
        productList.clear();
        productList.addAll(products);
        notifyDataSetChanged(); // 전체 데이터가 갱신되도록 설정
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, classificationTextView, storageTextView, quantityTextView, dateTextView, memoTextView, dDayTextView;
        ImageView itemImageView;
        CheckBox checkBox;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.NameTextView);
            classificationTextView = itemView.findViewById(R.id.ClassificationTextView);
            storageTextView = itemView.findViewById(R.id.StorageTextView);
            quantityTextView = itemView.findViewById(R.id.QuantityTextView);
            dateTextView = itemView.findViewById(R.id.DateTextView);
            itemImageView = itemView.findViewById(R.id.ItemImageView);
            memoTextView = itemView.findViewById(R.id.MemoTextView);
            dDayTextView = itemView.findViewById(R.id.DDayTextView);
            checkBox = itemView.findViewById(R.id.CheckBox);

            // CheckBox 클릭시 선택 상태 변경
            checkBox.setOnClickListener(v -> {
                int position = getAdapterPosition();
                toggleSelection(position);
            });

            // ItemView 길게 누르면 선택 모드 활성화
            itemView.setOnLongClickListener(v -> {
                setSelectionMode(!isSelectionMode); //` 선택 모드 토글
                if(selectionModeListener != null) {
                    selectionModeListener.onSelectionModeChanged(isSelectionMode); // Fragment에 알림
                }
                return true;
            });

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                    Product product = productList.get(position);
                    onItemClickListener.onItemClick(product);  // 클릭 이벤트 전달
                }
            });
        }

        public void bind(Product product, int position) {
            nameTextView.setText(product.getName());
            classificationTextView.setText("분류: " + product.getClassification());
            storageTextView.setText("위치: " + product.getStorageLocation());
            quantityTextView.setText("수량: " + product.getQuantity());
            dateTextView.setText("소비기한: " + product.getExpirationDate());
            memoTextView.setText(product.getMemo());



            // RGB 색상을 이용하여 ColorStateList 생성
            ColorStateList redColorState = ColorStateList.valueOf(Color.rgb(216, 67, 21));
            ColorStateList yellowColorState = ColorStateList.valueOf(Color.rgb(251,192,45));
            ColorStateList greenColorState = ColorStateList.valueOf(Color.rgb(124, 179, 66));

            // 이미지가 있을 경우 ImageView에 표시
            if(product.getImageUri() != null) {
                itemImageView.setImageURI(product.getImageUri());
            } else {
                itemImageView.setImageResource(R.drawable.default_image);
            }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
            try {
                Date expirationDate = formatter.parse(product.getExpirationDate());
                Date today = new Date();

                // 두 날짜 간의 일 수 계산
                long diffInMillis = expirationDate.getTime() - today.getTime();
                long daysUntilExpiration = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

                String dDayText;
                if (daysUntilExpiration > 2) {
                    dDayText = "D-" + daysUntilExpiration;
                   dDayTextView.setBackgroundTintList(greenColorState); // 초록색 적용
                } else if (daysUntilExpiration > 0) {
                    dDayText = "D-" + daysUntilExpiration;
                    dDayTextView.setBackgroundTintList(yellowColorState); // 노란색 적용
                } else if (daysUntilExpiration == 0) {
                    dDayText = "D-Day";
                    dDayTextView.setBackgroundTintList(yellowColorState); // 노란색 적용
                } else {
                    dDayText = "D+" + Math.abs(daysUntilExpiration); // 만료된 경우
                    dDayTextView.setBackgroundTintList(redColorState); // 빨간색 적용
                }

                dDayTextView.setText(dDayText);
            } catch (ParseException e) {
                dDayTextView.setText("날짜 오류"); // 날짜 형식이 맞지 않는 경우 오류 메시지
            }

            // 선택 모드일 때만 체크박스 표시
            checkBox.setVisibility(isSelectionMode ? View.VISIBLE : View.GONE);

            // 선택 상태에 따라 체크박스 상태 설정
            checkBox.setChecked(selectedItems.contains(position));
        }

        // 선택 상태 변경
        private void toggleSelection(int position) {
            if (selectedItems.contains(position)) {
                // 선택 해제 시 항목 제거
                selectedItems.remove(Integer.valueOf(position));
            } else {
                // 새 항목 선택 시 추가
                selectedItems.add(position);
            }
            notifyItemChanged(position); // 변경된 아이템만 업데이트
        }
    }

    public void removeSelectItem() {
        // selectedItems 인덱스 기준으로 내림차순으로 삭제해 안정성 유지
        for (int i = selectedItems.size() - 1; i >= 0; i--) {
            int index = selectedItems.get(i);
            productList.remove(index);
            notifyItemRemoved(index);
        }
        selectedItems.clear();
        setSelectionMode(false); // 선택 모드 종료
    }

    public List<Product> removeSelectedItems() {
        List<Product> itemsToRemove = new ArrayList<>();
        for (int i = selectedItems.size() - 1; i >= 0; i--) {
            int index = selectedItems.get(i);
            itemsToRemove.add(productList.get(index));
            productList.remove(index);
            notifyItemRemoved(index);
        }
        selectedItems.clear();
        setSelectionMode(false);
        return itemsToRemove; // 삭제된 항목 리스트 반환
    }

    public void removeItem(int position) {
        Product removedProduct = productList.get(position);

        // 1. `productList`에서 항목 제거 및 RecyclerView에 알림
        productList.remove(position);
        notifyItemRemoved(position);

        // ViewModel 업데이트
        if (productViewModel != null) {
            productViewModel.setProductList(new ArrayList<>(productList));
        }

        // 삭제된 항목 리스너에 알림
        if (selectionModeListener != null) {
            selectionModeListener.onProductRemoved(position);
        }
    }
}

