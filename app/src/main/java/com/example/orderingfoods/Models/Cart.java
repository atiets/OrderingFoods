package com.example.orderingfoods.Models;

import java.io.Serializable;

public class Cart implements Serializable {
    private String name;
    private double price;
    private int quantity;
    private int numGuests;
    private int tableId;
    private String status;

    public Cart(String name, double price, int quantity, int numGuests, int tableId, String status) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.numGuests = numGuests;
        this.tableId = tableId;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getNumGuests() {
        return numGuests;
    }

    public void setNumGuests(int numGuests) {
        this.numGuests = numGuests;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
