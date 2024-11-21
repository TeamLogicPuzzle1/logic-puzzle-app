package com.cookandroid.test_ui.DTO.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BooleanResDto {
    @Expose
    @SerializedName("message") private String message;
    @SerializedName("data") private boolean data;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
