package com.example.orderingfoods.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.orderingfoods.Adapter.FoodAdapter;
import com.example.orderingfoods.Models.Food;
import com.example.orderingfoods.R;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    //private ListView listView;
    private GridView gridView;
    private ArrayList<Food> foodList;
    private FoodAdapter adapter;
    private TextView textViewName, textViewDesc, textPrice, textQuantity;
    private ImageView imagePic;
    private ImageButton buttonAdd, buttonSubtract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_foods_list);
        setContentView(R.layout.activity_foods_grid);

        //listView = findViewById(R.id.listview1);
        gridView = (GridView) findViewById(R.id.gridview1);
        textViewName = findViewById(R.id.titleTextViewGrid);

        // Tạo danh sách các món ăn
        foodList = new ArrayList<>();
        foodList.add(new Food(1, "Phở Bò", "Món phở truyền thống với thịt bò, rau thơm và nước dùng đậm đà.", 45000.0, "https://i.pinimg.com/564x/80/f0/5e/80f05eb75bea006de7364f102fd7b407.jpg", 0));
        foodList.add(new Food(2, "Bún Chả", "Bún chả Hà Nội với thịt nướng, bún, rau sống và nước mắm chua ngọt.", 40000.0, "https://i.pinimg.com/564x/41/9c/c8/419cc8a05227d4b4ab7550612149fbb1.jpg", 0));
        foodList.add(new Food(3, "Gỏi Cuốn", "Gỏi cuốn tươi ngon với tôm, thịt, rau sống và bún.", 30000.0, "https://i.pinimg.com/564x/9a/a6/7a/9aa67aed6a696a76aaba8206d06977a1.jpg", 0));
        foodList.add(new Food(4, "Bánh Mì", "Bánh mì Việt Nam với pate, thịt nguội, dưa leo, và rau thơm.", 20000.0, "https://i.pinimg.com/564x/86/4d/cf/864dcf64ad03691f0914bdcd3f859d18.jpg", 0));
        foodList.add(new Food(5, "Cơm Tấm", "Cơm tấm sườn nướng với trứng, chả và dưa chua.", 50000.0, "https://i.pinimg.com/564x/75/f3/9f/75f39f3ba8033ee5201532c6d54dc7cd.jpg", 0));

        // Khởi tạo adapter với danh sách món ăn
        adapter = new FoodAdapter(MenuActivity.this, R.layout.row_food_grid, foodList);
        //listView.setAdapter(adapter);
        gridView.setAdapter(adapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Food monHoc = foodList.get(position);
//                textViewName.setText(monHoc.getName());
//                textViewDesc.setText(monHoc.getDescription());
//
//            }
//        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy món ăn được chọn
                Food selectedFood = foodList.get(position);

                // Hiển thị thông tin món ăn bằng Toast
                Toast.makeText(MenuActivity.this, "Món: " + selectedFood.getName() + "\nGiá: " + selectedFood.getPrice() + " VND", Toast.LENGTH_SHORT).show();
            }
        });

    }
}