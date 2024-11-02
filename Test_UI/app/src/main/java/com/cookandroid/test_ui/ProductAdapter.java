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

    private List<Product> productList; // 빈 리스트로 초기화

    // 생성자
    public ProductAdapter(List<Product> initialProductList) {
        this.productList = productList != null ? productList : new ArrayList<>(); // Null 검사 추가
    }

    // 전체 리스트 설정
    /* public void setProductList(List<Product> newProductList) {
        this.productList.clear();
        if (newProductList != null) {
            this.productList.addAll(newProductList);
        }
        notifyDataSetChanged();
    } */

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
        if (productList == null) { // Null 검사 추가
            productList = new ArrayList<>();
        }
        productList.clear();
        productList.addAll(products);
        notifyDataSetChanged();
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

        // 데이터 바인딩을 위한 메서드
        public void bind(Product product) {
            nameTextView.setText(product.getName());
            classificationTextView.setText("분류: " + product.getClassification());
            storageTextView.setText("위치: " + product.getStorageLocation());
            quantityTextView.setText("수량: " + product.getQuantity());
            dateTextView.setText("소비기한: " + product.getExpirationDate());
        }
    }
}
