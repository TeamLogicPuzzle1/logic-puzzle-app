package com.cookandroid.test_ui;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
//  import com.github.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    public class Item {
        private String name;
        private String classification;
        private String storage;
        private String date;
        private int quantity;
        private String imageUrl;

        // 생성자
        public Item(String name, String classification, String storage, String date, int quantity, String imageUrl) {
            this.name = name;
            this.classification = classification;
            this.storage = storage;
            this.date = date;
            this.quantity = quantity;
            this.imageUrl = imageUrl;
        }

        // 각 필드에 대한 getter 메서드
        public String getName() {
            return name;
        }

        public String getClassification() {
            return classification;
        }

        public String getStorage() {
            return storage;
        }

        public String getDate() {
            return date;
        }

        public int getQuantity() {
            return quantity;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }
    private List<Item> itemList;
    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }


    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        // 현재 아이템에 대한 데이터를 가져와서 ViewHolder의 각 뷰에 설정합니다.
        Item item = itemList.get(position);
        holder.nameTextView.setText(item.getName());
        holder.classificationTextView.setText("분류: " + item.getClassification());
        holder.storageTextView.setText("위치: " + item.getStorage());
        holder.dateTextView.setText(item.getDate());
        holder.quantityTextView.setText("수량: " + item.getQuantity());

        // Glide 또는 Picasso를 사용하여 이미지를 로드할 수 있습니다.
        Glide.with(holder.itemView.getContext()).load(item.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, classificationTextView, storageTextView, dateTextView, quantityTextView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 여기서 product_item_layout.xml에 정의된 뷰와 연결합니다.
            nameTextView = itemView.findViewById(R.id.NameTextView);
            classificationTextView = itemView.findViewById(R.id.ClassificationTextView);
            storageTextView = itemView.findViewById(R.id.StorageTextView);
            dateTextView = itemView.findViewById(R.id.DateTextView);
            quantityTextView = itemView.findViewById(R.id.QuantityTextView);
            imageView = itemView.findViewById(R.id.ItemImageView);
        }
    }

}
