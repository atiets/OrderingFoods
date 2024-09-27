package com.example.orderingfoods.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Table implements Serializable{
    private int tableId;
    private String status; // Trạng thái: "Trống", "Đã đặt", "Đang phục vụ"
    private int numberOfGuests;

    public Table(int tableId, String status, int numberOfGuests) {
        this.tableId = tableId;
        this.status = status;
        this.numberOfGuests = numberOfGuests;
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
}

