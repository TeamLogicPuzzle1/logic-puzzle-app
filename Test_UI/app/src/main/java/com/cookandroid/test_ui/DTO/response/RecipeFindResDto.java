package com.cookandroid.test_ui.DTO.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RecipeFindResDto {
    @Expose
    @SerializedName("recipes") private ArrayList<RecipeListDto> productsResListDto;

    public ArrayList<RecipeListDto> getProductsResListDto() {
        return productsResListDto;
    }

    public void setProductsResListDto(ArrayList<RecipeListDto> productsResListDto) {
        this.productsResListDto = productsResListDto;
    }
}
