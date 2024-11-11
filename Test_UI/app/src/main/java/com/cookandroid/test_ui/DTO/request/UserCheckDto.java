package com.cookandroid.test_ui.DTO.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCheckDto {
    @Expose
    @SerializedName("user_id") private String userId;
    @SerializedName("data") private boolean data;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean getData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
