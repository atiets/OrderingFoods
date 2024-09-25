package com.example.orderingfoods.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderingfoods.Adapter.CartAdapter;
import com.example.orderingfoods.Adapter.FoodAdapter;
import com.example.orderingfoods.Models.Food;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        selectedFoods = (ArrayList<Food>) getIntent().getSerializableExtra("selectedFoods");

        // Kiểm tra nếu danh sách không null
        if (selectedFoods == null) {
            selectedFoods = new ArrayList<>();
        }

        textQty = findViewById(R.id.text_qty);
        textTotal = findViewById(R.id.text_total);
        listView = findViewById(R.id.listview_cart);

        int quantity = getIntent().getIntExtra("quantity", 0);
        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);


        textQty.setText("Số lượng: " + quantity);
        textTotal.setText("Tổng tiền: " + totalPrice + " VND");

        if (textQty == null || textTotal == null) {
            Log.e("CartActivity", "TextView is null!");
            return;
        }

        // Khởi tạo ListView hoặc RecyclerView để hiển thị giỏ hàng
        ListView listViewCart = findViewById(R.id.listview_cart);
        foodAdapter = new FoodAdapter(this, R.layout.row_food_cart, selectedFoods, new FoodAdapter.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int totalQuantity, double totalPrice) {
                // Cập nhật giao diện nếu cần
            }
        }, selectedFoods); // Truyền cùng danh sách selectedFoods để có thể cập nhật nếu cần

        listViewCart.setAdapter(foodAdapter);
    }
}