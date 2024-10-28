package com.cookandroid.test_ui;

public class Product {
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

    // Getter 메서드 추가
    public String getName() { return name; }
    public String getClassification() { return classification; }
    public String getStorageLocation() { return storageLocation; }
    public int getQuantity() { return quantity; }
    public String getExpirationDate() { return expirationDate; }
}
