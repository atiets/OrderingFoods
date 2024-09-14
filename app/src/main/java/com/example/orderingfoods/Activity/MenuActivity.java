package com.example.orderingfoods.Activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.orderingfoods.Adapter.FoodListAdapter;
import com.example.orderingfoods.Models.Food;
import com.example.orderingfoods.R;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Food> foodList;
    private FoodListAdapter adapter;
    private TextView textViewName, textViewDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods_list);

        listView = findViewById(R.id.listview1);
        textViewName = findViewById(R.id.titleTextView);

        // Tạo danh sách các món ăn
        List<Food> foodList = new ArrayList<>();
        foodList.add(new Food(1, "Phở Bò", "Món phở truyền thống với thịt bò, rau thơm và nước dùng đậm đà.", 45000.0, "https://example.com/pho_bo.png", 0));
        foodList.add(new Food(2, "Bún Chả", "Bún chả Hà Nội với thịt nướng, bún, rau sống và nước mắm chua ngọt.", 40000.0, "https://example.com/bun_cha.png", 0));
        foodList.add(new Food(3, "Gỏi Cuốn", "Gỏi cuốn tươi ngon với tôm, thịt, rau sống và bún.", 30000.0, "https://example.com/goi_cuon.png", 0));
        foodList.add(new Food(4, "Bánh Mì", "Bánh mì Việt Nam với pate, thịt nguội, dưa leo, và rau thơm.", 20000.0, "https://example.com/banh_mi.png", 0));
        foodList.add(new Food(5, "Cơm Tấm", "Cơm tấm sườn nướng với trứng, chả và dưa chua.", 50000.0, "https://example.com/com_tam.png", 0));

        // Khởi tạo adapter với danh sách món ăn
        adapter = new FoodListAdapter(MenuActivity.this, R.layout.row_food_list, foodList);
        listView.setAdapter(adapter);
    }
}