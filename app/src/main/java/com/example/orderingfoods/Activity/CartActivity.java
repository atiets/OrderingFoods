package com.example.orderingfoods.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        textQty = findViewById(R.id.text_qty);
        textTotal = findViewById(R.id.text_total);

        Intent intent = getIntent();
        int quantity = intent.getIntExtra("totalQuantity", 0);
        double totalPrice = intent.getDoubleExtra("totalPrice", 0.0);

        textQty.setText(String.valueOf(quantity));
        textTotal.setText(String.format("%.2f", totalPrice));


    }
}