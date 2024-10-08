package com.example.orderingfoods.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Table implements Serializable{
    private int tableId;
    private String status; // Trạng thái: "Trống", "Đã đặt", "Đang phục vụ"
    private int numberOfGuests;
    private ArrayList<Food> orderedFoods;

    public Table(int tableId, String status, int numberOfGuests, ArrayList orderedFoods) {
        this.tableId = tableId;
        this.status = status;
        this.numberOfGuests = numberOfGuests;
        this.orderedFoods = orderedFoods;
    }

    public int getTableId() {
        return tableId;
    }

    public String getStatus() {
        return status;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public ArrayList<Food> getOrderedFoods() {
        return orderedFoods;
    }

    public void setOrderedFoods(ArrayList<Food> orderedFoods) {
        this.orderedFoods = orderedFoods;
    }
}

