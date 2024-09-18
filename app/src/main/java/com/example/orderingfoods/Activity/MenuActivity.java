package com.example.orderingfoods.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderingfoods.Adapter.CategoryAdapter;
import com.example.orderingfoods.Adapter.FoodAdapter;
import com.example.orderingfoods.Models.Category;
import com.example.orderingfoods.Models.Food;
import com.example.orderingfoods.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import kotlin.Suppress;

public class MenuActivity extends AppCompatActivity {
    //private ListView listView;
    Spinner layoutSpinner;
    private GridView gridView_food;
    private ListView listView_cate, listView_food;
    private ArrayList<Food> foodList;
    private FoodAdapter foodAdapter;
    private ArrayList<Category> categoryList;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_foods_list);
        setContentView(R.layout.activity_menu_category);

        layoutSpinner = findViewById(R.id.layout_spinner);
        listView_cate = findViewById(R.id.listview_category);
        gridView_food = (GridView) findViewById(R.id.gridview_foods);
        listView_food = findViewById(R.id.listview_foods);

        String[] layoutOptions = {"Grid View", "List View"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, layoutOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        layoutSpinner.setAdapter(spinnerAdapter);

        // Tạo danh sách các món ăn
        ArrayList<Food> foodlist1 = new ArrayList<>();
        foodlist1.add(new Food(1, "Phở Bò 1", "Món phở truyền thống với thịt bò, rau thơm và nước dùng đậm đà.", 45000.0, "https://i.pinimg.com/564x/80/f0/5e/80f05eb75bea006de7364f102fd7b407.jpg", 0));
        foodlist1.add(new Food(2, "Bún Chả 2", "Bún chả Hà Nội với thịt nướng, bún, rau sống và nước mắm chua ngọt.", 40000.0, "https://i.pinimg.com/564x/41/9c/c8/419cc8a05227d4b4ab7550612149fbb1.jpg", 0));
        foodlist1.add(new Food(3, "Gỏi Cuốn 3", "Gỏi cuốn tươi ngon với tôm, thịt, rau sống và bún.", 30000.0, "https://i.pinimg.com/564x/9a/a6/7a/9aa67aed6a696a76aaba8206d06977a1.jpg", 0));
        foodlist1.add(new Food(4, "Bánh Mì 4", "Bánh mì Việt Nam với pate, thịt nguội, dưa leo, và rau thơm.", 20000.0, "https://i.pinimg.com/564x/86/4d/cf/864dcf64ad03691f0914bdcd3f859d18.jpg", 0));
        foodlist1.add(new Food(5, "Cơm Tấm 5", "Cơm tấm sườn nướng với trứng, chả và dưa chua.", 50000.0, "https://i.pinimg.com/564x/75/f3/9f/75f39f3ba8033ee5201532c6d54dc7cd.jpg", 0));

        ArrayList<Food> foodlist2 = new ArrayList<>();
        foodlist2.add(new Food(1, "Phở Bò 6", "Món phở truyền thống với thịt bò, rau thơm và nước dùng đậm đà.", 45000.0, "https://i.pinimg.com/564x/80/f0/5e/80f05eb75bea006de7364f102fd7b407.jpg", 0));
        foodlist2.add(new Food(2, "Bún Chả 7 ", "Bún chả Hà Nội với thịt nướng, bún, rau sống và nước mắm chua ngọt.", 40000.0, "https://i.pinimg.com/564x/41/9c/c8/419cc8a05227d4b4ab7550612149fbb1.jpg", 0));
        foodlist2.add(new Food(3, "Gỏi Cuốn 8", "Gỏi cuốn tươi ngon với tôm, thịt, rau sống và bún.", 30000.0, "https://i.pinimg.com/564x/9a/a6/7a/9aa67aed6a696a76aaba8206d06977a1.jpg", 0));
        foodlist2.add(new Food(4, "Bánh Mì 9", "Bánh mì Việt Nam với pate, thịt nguội, dưa leo, và rau thơm.", 20000.0, "https://i.pinimg.com/564x/86/4d/cf/864dcf64ad03691f0914bdcd3f859d18.jpg", 0));
        foodlist2.add(new Food(5, "Cơm Tấm", "Cơm tấm sườn nướng với trứng, chả và dưa chua.", 50000.0, "https://i.pinimg.com/564x/75/f3/9f/75f39f3ba8033ee5201532c6d54dc7cd.jpg", 0));

        //create list cate
        categoryList= new ArrayList<>();
        categoryList.add(new Category("Combo", "https://i.pinimg.com/564x/b8/7a/95/b87a95a43fdfa71b2f629c326d595a6f.jpg", foodlist1));
        categoryList.add(new Category("Coffee", "https://i.pinimg.com/236x/2e/35/b0/2e35b05638b83ae6bce185c25797dc6d.jpg", foodlist2));
        categoryList.add(new Category("Beer, soft drink", "https://i.pinimg.com/474x/19/28/08/1928089e2052beaaf502042c16a399cf.jpg", foodlist1));
        categoryList.add(new Category("Vegetable", "https://i.pinimg.com/originals/e3/17/84/e3178427da26d961a06e45d74c64fbd1.png", foodlist2));
        categoryList.add(new Category("Meat", "https://i.pinimg.com/236x/1e/ea/d8/1eead88b3ce7bfcc98fc3a88a52e2db6.jpg", foodList));



        // Khởi tạo adapter với danh sách món ăn
        foodAdapter = new FoodAdapter(MenuActivity.this, R.layout.row_food_grid, new ArrayList<Food>());
        gridView_food.setAdapter(foodAdapter);
        listView_food.setAdapter(foodAdapter);

        // Khởi tạo adapter với danh sách category
        categoryAdapter = new CategoryAdapter(MenuActivity.this, R.layout.row_cate_list, categoryList);
        //listView.setAdapter(adapter);
        listView_cate.setAdapter(categoryAdapter);


        layoutSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLayout = parent.getItemAtPosition(position).toString();

                if(selectedLayout.equals("Grid View")) {
                    gridView_food.setVisibility(View.VISIBLE);
                    listView_food.setVisibility(View.GONE);
                } else if (selectedLayout.equals("List View")) {
                    listView_food.setVisibility(View.VISIBLE);
                    gridView_food.setVisibility(View.GONE);
                }
            }


            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì nếu không có mục nào được chọn
            }
        });


        gridView_food.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy món ăn được chọn
                Food selectedFood = foodList.get(position);

                // Hiển thị thông tin món ăn bằng Toast
                Toast.makeText(MenuActivity.this, "Món: " + selectedFood.getName() + "\nGiá: " + selectedFood.getPrice() + " VND", Toast.LENGTH_SHORT).show();
            }
        });

        listView_food.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy món ăn được chọn
                Food selectedFood = foodList.get(position);

                // Hiển thị thông tin món ăn bằng Toast
                Toast.makeText(MenuActivity.this, "Món: " + selectedFood.getName() + "\nGiá: " + selectedFood.getPrice() + " VND", Toast.LENGTH_SHORT).show();
            }
        });

        listView_cate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category selectedCategory = categoryList.get(position);
                // Update the food list for the selected category
                foodAdapter.updateFoodList(selectedCategory.getFoodList());
                // Optionally scroll to the top or refresh the view
                gridView_food.smoothScrollToPosition(0);
            }
        });


    }
}