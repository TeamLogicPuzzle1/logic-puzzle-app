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

    private List<Product> productList;

    // 기본 생성자에서 리스트를 초기화
    public ProductAdapter(List<Product> productList) {
        this.productList = (productList != null) ? productList : new ArrayList<>();
    }

    public void addProduct(Product product) {
        productList.add(product);
        notifyItemInserted(productList.size() - 1);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.nameTextView.setText(product.getName());
        holder.classificationTextView.setText("분류: " + product.getClassification());
        holder.storageTextView.setText("위치: " + product.getStorageLocation());
        holder.quantityTextView.setText("수량: " + product.getQuantity());
        holder.dateTextView.setText("소비기한: " + product.getExpirationDate());
    }

    @Override
    public int getItemCount() {
        return productList.size();
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
    }
}
