package com.cookandroid.test_ui.DTO.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductsResListDto {
    @Expose
    @SerializedName("data") private ProductsResDataDto productsResDataDto;
    @SerializedName("list") private List<ProductsResDto> productsResListDto;

    public ProductsResDataDto getProductsResDataDto() {
        return productsResDataDto;
    }

    public void setProductsResDataDto(ProductsResDataDto productsResDataDto) {
        this.productsResDataDto = productsResDataDto;
    }

    public List<ProductsResDto> getProductsResListDto() {
        return productsResListDto;
    }

    public void setProductsResListDto(List<ProductsResDto> productsResListDto) {
        this.productsResListDto = productsResListDto;
    }
}
