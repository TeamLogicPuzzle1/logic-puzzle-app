package com.cookandroid.test_ui.DTO.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductsResDto {
    @Expose
    @SerializedName("product_id") private Integer productId;
    @SerializedName("name") private String name;
    @SerializedName("expiration_date") private String expirationDate;
    @SerializedName("category") private Integer category;
    @SerializedName("location") private Integer location;
    @SerializedName("quantity") private Integer quantity;
    @SerializedName("memo") private String memo;
    @SerializedName("image") private String image;
    @SerializedName("expiration_status") private Integer expirationStatus;

    public Integer getProductId() {
        return productId;
    }

    public Integer getExpirationStatus() {
        return expirationStatus;
    }

    public void setExpirationStatus(Integer expirationStatus) {
        this.expirationStatus = expirationStatus;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
