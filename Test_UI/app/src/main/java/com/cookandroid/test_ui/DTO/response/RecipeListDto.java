package com.cookandroid.test_ui.DTO.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeListDto {
    @SerializedName("recipe_name") private String recipeName;
    @SerializedName("ingredients") private List<String> ingredients;

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
