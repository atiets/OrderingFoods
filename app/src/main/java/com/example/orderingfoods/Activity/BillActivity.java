package com.example.orderingfoods.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.orderingfoods.Models.Food;
import com.example.orderingfoods.Models.Table;
import com.example.orderingfoods.R;

import java.util.ArrayList;

public class BillActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        // Nhận bàn và danh sách món ăn từ Intent
        Table currentTable = (Table) getIntent().getSerializableExtra("updatedTable");
        ArrayList<Food> selectedFoods = (ArrayList<Food>) getIntent().getSerializableExtra("selectedFoods");

        if (currentTable != null && selectedFoods != null) {
            Log.d("BillActivity", "Số lượng món ăn đã đặt: " + selectedFoods.size());
            StringBuilder billDetails = new StringBuilder();
            double totalPrice = 0.0;

            for (Food food : selectedFoods) {
                billDetails.append("Món: ").append(food.getName())
                        .append(", Số lượng: ").append(food.getQuantity())
                        .append(", Giá: ").append(food.getPrice())
                        .append(" VND\n");
                totalPrice += food.getQuantity() * food.getPrice();
            }

            billDetails.append("Tổng tiền: ").append(totalPrice).append(" VND");

            TextView billTextView = findViewById(R.id.textViewBill);
            billTextView.setText(billDetails.toString());
        } else {
            Log.e("BillActivity", "Bàn hiện tại hoặc món ăn đã chọn là null");
        }
    }
}