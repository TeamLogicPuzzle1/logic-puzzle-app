package com.cookandroid.test_ui.DTO.reponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;

public class ProductsExpirationDateResDto {
    @Expose

    @SerializedName("expiration_date") // JSON 필드 이름
    private String expirationDate;

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }


}
