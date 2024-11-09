package com.cookandroid.test_ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private ArrayList<Product> productList;

    // 생성자
    public ProductAdapter(ArrayList<Product> initialProductList) {
        this.productList = initialProductList != null ? initialProductList : new ArrayList<>(); // 전달된 리스트가 null이 아닐 때만 사용
    }

    // 새 상품 추가
    public void addProduct(Product product) {
        productList.add(product);
        notifyItemInserted(productList.size() - 1); // 리스트에 새 항목 추가
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(productList.get(position)); // bind 메서드를 통해 데이터 설정
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

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, classificationTextView, storageTextView, quantityTextView, dateTextView, memoTextView, dDayTextView;
        ImageView itemImageView;
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

        }

        public void bind(Product product) {
            nameTextView.setText(product.getName());
            classificationTextView.setText("분류: " + product.getClassification());
            storageTextView.setText("위치: " + product.getStorageLocation());
            quantityTextView.setText("수량: " + product.getQuantity());
            dateTextView.setText("소비기한: " + product.getExpirationDate());
            memoTextView.setText(product.getMemo());
            dDayTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rectangle_search, 0,0,0);

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
        }
    }
}
