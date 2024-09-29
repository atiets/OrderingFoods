// Food.java
package com.example.orderingfoods.Models;

import java.io.Serializable;

public class Food implements Serializable {
    private int id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private int quantity;
    private Category category;
    private String note;

    public Food(int id, String name, String description, double price, String imageUrl, int quantity, Category category, String note) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.category = category;
        this.note = note;
    }

    public Food() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() { // Thêm phương thức getCategory
        return category;
    }

    public void setCategory(Category category) { // Thêm phương thức setCategory
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}