package com.cookandroid.test_ui.DTO.response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
