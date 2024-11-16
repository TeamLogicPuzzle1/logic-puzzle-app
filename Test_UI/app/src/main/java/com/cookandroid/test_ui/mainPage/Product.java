/*
 * 간략: 상품 클래스
 * 최초 작성자: 홍진기
 * 작성일: 2024-10-28
 * 수정일: 2024-11-02
 * 버전: 0.0.4
 * */
package com.cookandroid.test_ui.mainPage;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private String name;
    private String classification;
    private String storageLocation;
    private int quantity;
    private String expirationDate;
    private Uri imageUri;
    private String memo;
    private boolean isSelected = false; // 체크 상태

    public Product(String name, String classification, String storageLocation, int quantity, String expirationDate, Uri imageUri, String memo) {
        this.name = name;
        this.classification = classification;
        this.storageLocation = storageLocation;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
        this.imageUri = imageUri;
        this.memo = memo;
    }

    protected Product(Parcel in) {
        name = in.readString();
        classification = in.readString();
        storageLocation = in.readString();
        quantity = in.readInt();
        expirationDate = in.readString();
        imageUri = in.readParcelable(Uri.class.getClassLoader());   // Uri 필드 읽기
        memo = in.readString();
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
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(classification);
        parcel.writeString(storageLocation);
        parcel.writeInt(quantity);
        parcel.writeString(expirationDate);
        parcel.writeParcelable(imageUri, flags); // Uri 필드를 Parcel에 씀
        parcel.writeString(memo);
    }
    // Getter 메서드 추가
    public String getName() { return name; }
    public String getClassification() { return classification; }
    public String getStorageLocation() { return storageLocation; }
    public int getQuantity() { return quantity; }
    public String getExpirationDate() { return expirationDate; }
    public Uri getImageUri() { return imageUri; }
    public String getMemo() {return memo; }

    public void setName(String name) {
        this.name = name;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void isSelected(boolean selected) {
        isSelected = selected;
    }
}
