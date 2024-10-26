package com.example.orderingfoods.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderingfoods.Adapter.CartAdapter;
import com.example.orderingfoods.Adapter.FoodAdapter;
import com.example.orderingfoods.Data.DatabaseHandler;
import com.example.orderingfoods.Models.Food;
import com.example.orderingfoods.Models.Table;
import com.example.orderingfoods.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private TextView textQty, textTotal;
    private ListView listView;
    private FoodAdapter foodAdapter;
    private ArrayList<Food> selectedFoods;
    private int quantity = 0;
    private double totalPrice = 0.0;
    private Button btnSave, btnTable;
    private Table currentTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        selectedFoods = (ArrayList<Food>) getIntent().getSerializableExtra("selectedFoods");

        // Kiểm tra nếu danh sách không null
        if (selectedFoods == null) {
            selectedFoods = new ArrayList<>();
        }

        int numGuests = getIntent().getIntExtra("numGuests", 0);
        int tableId = getIntent().getIntExtra("tableId", -1);
        String arrivalTime = getIntent().getStringExtra("arrivalTime"); // Correct method to retrieve arrivalTime

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        textQty = findViewById(R.id.text_qty);
        textTotal = findViewById(R.id.text_total);
        listView = findViewById(R.id.listview_cart);
        btnSave = findViewById(R.id.button_save);
        btnTable = findViewById(R.id.button_table);

        for (Food food : selectedFoods) {
            quantity += food.getQuantity();
            totalPrice += food.getQuantity() * food.getPrice();
        }

        textQty.setText("Số lượng: " + quantity);
        textTotal.setText("Tổng tiền: " + totalPrice + " VND");

        if (textQty == null || textTotal == null) {
            Log.e("CartActivity", "TextView is null!");
            return;
        }

        // Khởi tạo ListView hoặc RecyclerView để hiển thị giỏ hàng
        foodAdapter = new FoodAdapter(this, R.layout.row_food_cart, selectedFoods, new FoodAdapter.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int quanlity, double totalPrice) {
                // Cập nhật giao diện nếu cần
                textQty.setText("Số lượng: " + quanlity);
                textTotal.setText("Tổng tiền: " + totalPrice + " VND");
            }
        }, selectedFoods); // Truyền cùng danh sách selectedFoods để có thể cập nhật nếu cần

        listView.setAdapter(foodAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Food selectedFood = selectedFoods.get(position);
                selectedFood.setQuantity(selectedFood.getQuantity() + 1);
                double totalPrice = 0.0;
                int quantity = 0;
                for (Food food : selectedFoods) {
                    quantity += food.getQuantity();
                    totalPrice += food.getQuantity() * food.getPrice();
                }
                textQty.setText("Số lượng: " + quantity);
                textTotal.setText("Tổng tiền: " + totalPrice + " VND");

                // Cập nhật giao diện giỏ hàng
                foodAdapter.notifyDataSetChanged();
            }
        });

        btnSave.setOnClickListener(v -> {
            databaseHandler.addOrder(selectedFoods, numGuests, tableId, "Đang phục vụ", arrivalTime);
            Intent intent = new Intent(CartActivity.this, TableActivity.class);
            startActivity(intent);
            finish();
        });

    }
}
