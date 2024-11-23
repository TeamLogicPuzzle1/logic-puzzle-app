package com.cookandroid.test_ui.DTO.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductsResDataDto {
    @Expose
    @SerializedName("imminent") private Integer imminent;
    @SerializedName("expired") private Integer expired;

    public Integer getImminent() {
        return imminent;
    }

    public void setImminent(Integer imminent) {
        this.imminent = imminent;
    }

    public Integer getExpired() {
        return expired;
    }

    public void setExpired(Integer expired) {
        this.expired = expired;
    }
}
