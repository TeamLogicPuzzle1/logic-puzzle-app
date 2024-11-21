package com.cookandroid.test_ui.DTO.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodWasteReqDel {
    @Expose
    @SerializedName("user_id") private String userId;
    @SerializedName("quantity") private Integer quantity;
    @SerializedName("message") private String message;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
