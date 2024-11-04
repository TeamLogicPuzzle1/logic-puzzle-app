package com.cookandroid.test_ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

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
        TextView nameTextView, classificationTextView, storageTextView, quantityTextView, dateTextView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.NameTextView);
            classificationTextView = itemView.findViewById(R.id.ClassificationTextView);
            storageTextView = itemView.findViewById(R.id.StorageTextView);
            quantityTextView = itemView.findViewById(R.id.QuantityTextView);
            dateTextView = itemView.findViewById(R.id.DateTextView);
        }

        public void bind(Product product) {
            nameTextView.setText(product.getName());
            classificationTextView.setText("분류: " + product.getClassification());
            storageTextView.setText("위치: " + product.getStorageLocation());
            quantityTextView.setText("수량: " + product.getQuantity());
            dateTextView.setText("소비기한: " + product.getExpirationDate());
        }
    }
}
