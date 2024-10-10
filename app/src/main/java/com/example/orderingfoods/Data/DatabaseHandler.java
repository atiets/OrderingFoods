package com.example.orderingfoods.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.orderingfoods.Models.Cart;
import com.example.orderingfoods.Models.Food;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "restaurant.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng
    private static final String TABLE_ORDERS = "Orders";

    // Các cột trong bảng
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FOOD_NAME = "foodName";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_NUM_GUESTS = "numGuests";
    private static final String COLUMN_TABLE_ID = "tableId";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_TOTAL_PRICE = "totalPrice";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng
        String createTable = "CREATE TABLE " + TABLE_ORDERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FOOD_NAME + " TEXT, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_NUM_GUESTS + " INTEGER, " +
                COLUMN_TABLE_ID + " INTEGER, " +
                COLUMN_STATUS + " TEXT, " +
                COLUMN_TOTAL_PRICE + " REAL" +
                ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    public void addOrder(ArrayList<Food> selectedFoods, int numGuests, int tableId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            for (Food food : selectedFoods) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_FOOD_NAME, food.getName());
                values.put(COLUMN_QUANTITY, food.getQuantity());
                values.put(COLUMN_NUM_GUESTS, numGuests);
                values.put(COLUMN_TABLE_ID, tableId);
                values.put(COLUMN_STATUS, status);

                double totalPrice = food.getPrice() * food.getQuantity();
                values.put(COLUMN_TOTAL_PRICE, totalPrice);

                db.insert(TABLE_ORDERS, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public ArrayList<Cart> getOrdersByTableId(int tableId) {
        ArrayList<Cart> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM orders WHERE tableId = ? AND status = ?",
//                new String[]{String.valueOf(tableId), "Đang phục vụ"});
        Cursor cursor = db.rawQuery("SELECT * FROM orders WHERE tableId = ?", new String[]{String.valueOf(tableId)});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Lấy chỉ số cột
                int foodNameIndex = cursor.getColumnIndex(COLUMN_FOOD_NAME);
                int totalPriceIndex = cursor.getColumnIndex(COLUMN_TOTAL_PRICE);
                int quantityIndex = cursor.getColumnIndex(COLUMN_QUANTITY);
                int numGuestsIndex = cursor.getColumnIndex(COLUMN_NUM_GUESTS);
                int tableIdIndex = cursor.getColumnIndex(COLUMN_TABLE_ID);
                int statusIndex = cursor.getColumnIndex(COLUMN_STATUS);

                // Chỉ lấy dữ liệu nếu chỉ số cột hợp lệ
                if (foodNameIndex != -1 && totalPriceIndex != -1 && quantityIndex != -1 && numGuestsIndex != -1 && tableIdIndex != -1 && statusIndex != -1) {
                    String foodName = cursor.getString(foodNameIndex);
                    double totalPrice = cursor.getDouble(totalPriceIndex);
                    int quantity = cursor.getInt(quantityIndex);
                    int numGuests = cursor.getInt(numGuestsIndex);
                    int tableIdValue = cursor.getInt(tableIdIndex);
                    String status = cursor.getString(statusIndex);

                    Cart order = new Cart(foodName, totalPrice, quantity, numGuests, tableIdValue, status);
                    orders.add(order);
                }
            }
            cursor.close();
        }
        db.close();
        return orders;
    }

    public void clearOrdersByTableId(int tableId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Orders", "tableId = ?", new String[]{String.valueOf(tableId)});
        db.close();
    }
}