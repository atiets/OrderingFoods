package com.example.orderingfoods.Models;

import java.util.List;

public class Category {
    private String name;
    private String imageResource;
    private List<Food> foodList;

    public Category() {
    }

    public Category(String name, String imageResource, List<Food> foodList) {
        this.name = name;
        this.imageResource = imageResource;
        this.foodList = foodList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }
}
