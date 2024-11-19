/*
 * 간략: 상품 클래스
 * 최초 작성자: 홍진기
 * 작성일: 2024-10-28
 * 수정일: 2024-11-02
 * 버전: 0.0.4
 * */
package com.cookandroid.test_ui.DTO.request;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product implements Parcelable {
    @SerializedName("id") @Expose private Integer id;
    @SerializedName("name") private String name;
    @SerializedName("image") private String image;
    @SerializedName("expiration_date") private String expirationDate;
    @SerializedName("category") private String category;
    @SerializedName("location") private String location;
    @SerializedName("quantity") private Integer quantity;
    @SerializedName("memo") private String memo;
    @SerializedName("user") private Integer user;

    private Uri imageUri; // 앱 내부에서 사용하는 이미지 URI
    private boolean isSelected = false; // 선택 상태 관리

    // 기본 생성자
    public Product() {}

    // 새로운 생성자 추가
    public Product(String name, String category, String location, int quantity, String expirationDate, Uri imageUri, String memo) {
        this.name = name;
        this.category = category;
        this.location = location;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
        this.imageUri = imageUri;
        this.memo = memo;
    }

    // Parcelable 구현을 위한 생성자
    protected Product(Parcel in) {
        id = (Integer) in.readValue(Integer.class.getClassLoader());
        name = in.readString();
        image = in.readString();
        expirationDate = in.readString();
        category = in.readString();
        location = in.readString();
        quantity = (Integer) in.readValue(Integer.class.getClassLoader());
        memo = in.readString();
        user = (Integer) in.readValue(Integer.class.getClassLoader());
        imageUri = in.readParcelable(Uri.class.getClassLoader());
        isSelected = in.readByte() != 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(expirationDate);
        dest.writeString(category);
        dest.writeString(location);
        dest.writeValue(quantity);
        dest.writeString(memo);
        dest.writeValue(user);
        dest.writeParcelable(imageUri, flags);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    // Getter 및 Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getExpirationDate() { return expirationDate; }
    public void setExpirationDate(String expirationDate) { this.expirationDate = expirationDate; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getMemo() { return memo; }
    public void setMemo(String memo) { this.memo = memo; }
    public Integer getUser() { return user; }
    public void setUser(Integer user) { this.user = user; }
    public Uri getImageUri() { return imageUri; }
    public void setImageUri(Uri imageUri) { this.imageUri = imageUri; }
    public boolean isSelected() { return isSelected; }
    public void setSelected(boolean selected) { isSelected = selected; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
