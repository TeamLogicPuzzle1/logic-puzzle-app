package com.cookandroid.test_ui.mainPage;

public class Recipe_item {
    private String title;
    private String description;

    public Recipe_item(String title,String description){
        this.title=title;
        this.description=description;
    }

    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
}