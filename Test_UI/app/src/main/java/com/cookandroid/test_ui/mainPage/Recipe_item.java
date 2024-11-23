package com.cookandroid.test_ui.mainPage;

public class Recipe_item {
    private String name;  // 레시피 이름
    private String ingredients;  // 레시피 재료

    public Recipe_item(String name, String ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }
}
