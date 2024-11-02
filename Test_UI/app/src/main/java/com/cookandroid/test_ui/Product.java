package com.cookandroid.test_ui;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private String name;
    private String classification;
    private String storageLocation;
    private int quantity;
    private String expirationDate;

    public Product(String name, String classification, String storageLocation, int quantity, String expirationDate) {
        this.name = name;
        this.classification = classification;
        this.storageLocation = storageLocation;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
    }

    protected Product(Parcel in) {
        name = in.readString();
        classification = in.readString();
        storageLocation = in.readString();
        quantity = in.readInt();
        expirationDate = in.readString();
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
    }
    // Getter 메서드 추가
    public String getName() { return name; }
    public String getClassification() { return classification; }
    public String getStorageLocation() { return storageLocation; }
    public int getQuantity() { return quantity; }
    public String getExpirationDate() { return expirationDate; }
}
