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
    private TextView textQty, textTotal;
    private int quantity = 0;
    private double totalPrice = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_foods_list);
        setContentView(R.layout.activity_menu_category);

        layoutSpinner = findViewById(R.id.layout_spinner);
        listView_cate = findViewById(R.id.listview_category);
        gridView_food = (GridView) findViewById(R.id.gridview_foods);
        listView_food = findViewById(R.id.listview_foods);
        textQty = findViewById(R.id.text_qty);
        textTotal = findViewById(R.id.text_total);


        String[] layoutOptions = {"Grid View", "List View"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, layoutOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        layoutSpinner.setAdapter(spinnerAdapter);

        ArrayList<Food> starters = new ArrayList<>();
        starters.add(new Food(1, "Gỏi Cuốn", "Gỏi cuốn tươi ngon với tôm, thịt, rau sống và bún.", 30000.0, "https://i.pinimg.com/564x/9a/a6/7a/9aa67aed6a696a76aaba8206d06977a1.jpg", 0));
        starters.add(new Food(2, "Bánh Xèo", "Bánh xèo giòn tan với nhân tôm, thịt và giá đỗ.", 35000.0, "https://i.pinimg.com/564x/45/c7/f3/45c7f36b43ec92e65e0a7a7698c5de9b.jpg", 0));
        starters.add(new Food(3, "Nem Rán", "Nem rán giòn rụm với nhân thịt và rau củ.", 40000.0, "https://i.pinimg.com/564x/1d/56/d6/1d56d6aa5070d3cb9fdc85ecf8de8a8e.jpg", 0));
        starters.add(new Food(4, "Bánh Mỳ Chả", "Bánh mỳ với chả lụa và rau sống.", 20000.0, "https://i.pinimg.com/564x/86/4d/cf/864dcf64ad03691f0914bdcd3f859d18.jpg", 0));
        starters.add(new Food(5, "Mực Nướng", "Mực nướng thơm ngon với gia vị đặc trưng.", 50000.0, "https://i.pinimg.com/564x/ff/5e/a3/ff5ea30b53df71c69d5e064a71b8f6e3.jpg", 0));
        starters.add(new Food(6, "Salad Trái Cây", "Salad tươi ngon với nhiều loại trái cây.", 25000.0, "https://i.pinimg.com/564x/2f/bc/58/2fbc58b7f3a32a99a1e60bcf4f4ccf4e.jpg", 0));

        ArrayList<Food> mainDishes = new ArrayList<>();
        mainDishes.add(new Food(1, "Phở Bò", "Món phở truyền thống với thịt bò, rau thơm và nước dùng đậm đà.", 45000.0, "https://i.pinimg.com/564x/80/f0/5e/80f05eb75bea006de7364f102fd7b407.jpg", 0));
        mainDishes.add(new Food(2, "Cơm Tấm", "Cơm tấm sườn nướng với trứng, chả và dưa chua.", 50000.0, "https://i.pinimg.com/564x/75/f3/9f/75f39f3ba8033ee5201532c6d54dc7cd.jpg", 0));
        mainDishes.add(new Food(3, "Bún Chả", "Bún chả Hà Nội với thịt nướng, bún, rau sống và nước mắm chua ngọt.", 40000.0, "https://i.pinimg.com/564x/41/9c/c8/419cc8a05227d4b4ab7550612149fbb1.jpg", 0));
        mainDishes.add(new Food(4, "Cá Kho Tộ", "Cá kho tộ đậm đà với nước dừa.", 60000.0, "https://i.pinimg.com/564x/7b/aa/5e/7baa5e19b47a7c68eb1e8728a45881e4.jpg", 0));
        mainDishes.add(new Food(5, "Bánh Mì Thịt", "Bánh mì với thịt heo quay và rau sống.", 25000.0, "https://i.pinimg.com/564x/d5/eb/36/d5eb36bb514e15ee5bcf35b25fa58c8b.jpg", 0));
        mainDishes.add(new Food(6, "Cơm Gà", "Cơm gà Hội An với nước dùng thơm ngon.", 55000.0, "https://i.pinimg.com/564x/f2/aa/b6/f2aab60b3bda4cd8c82b92e967b2f8e5.jpg", 0));

        ArrayList<Food> desserts = new ArrayList<>();
        desserts.add(new Food(1, "Bánh Flan", "Bánh flan mềm mịn với vị caramel thơm ngon.", 25000.0, "https://i.pinimg.com/564x/88/5e/4f/885e4f17b7d0f4621d5f865b1b1f2c6f.jpg", 0));
        desserts.add(new Food(2, "Chè Bưởi", "Chè bưởi ngọt thanh với cùi bưởi và đậu xanh.", 20000.0, "https://i.pinimg.com/564x/a0/ef/9a/a0ef9ad6a0f3a2d45f1b760b9efc20bb.jpg", 0));
        desserts.add(new Food(3, "Bánh Mochi", "Bánh mochi mềm mại với nhân đậu đỏ.", 30000.0, "https://i.pinimg.com/564x/53/3f/8f/533f8f9e30e6f30a90f78cb27b8e6345.jpg", 0));
        desserts.add(new Food(4, "Kem Xôi", "Xôi với kem và đậu phộng giòn.", 35000.0, "https://i.pinimg.com/564x/4f/4d/34/4f4d34a4d7a03869de88b236c3de70dc.jpg", 0));
        desserts.add(new Food(5, "Bánh Trôi Nước", "Bánh trôi với nhân đường và nước đường.", 20000.0, "https://i.pinimg.com/564x/2d/4c/53/2d4c53a9c0de9f2f18ee28c58f86b275.jpg", 0));
        desserts.add(new Food(6, "Trái Cây Dĩa", "Trái cây tươi ngon theo mùa.", 30000.0, "https://i.pinimg.com/564x/14/ba/7e/14ba7e43fd7c33c55c592e169751b34c.jpg", 0));

        ArrayList<Food> drinks = new ArrayList<>();
        drinks.add(new Food(1, "Trà Sữa", "Trà sữa thơm ngon với trân châu mềm.", 30000.0, "https://i.pinimg.com/564x/76/d0/3d/76d03dc2ff74f8d046b52e0288cf4815.jpg", 0));
        drinks.add(new Food(2, "Cà Phê Sữa", "Cà phê sữa đá đậm đà, thơm ngon.", 25000.0, "https://i.pinimg.com/564x/2e/35/b0/2e35b05638b83ae6bce185c25797dc6d.jpg", 0));
        drinks.add(new Food(3, "Nước Ép Trái Cây", "Nước ép trái cây tươi ngon.", 30000.0, "https://i.pinimg.com/564x/d5/bb/6d/d5bb6d8cb0cf08d71a272c8859f1f2a4.jpg", 0));
        drinks.add(new Food(4, "Soda", "Soda với nhiều hương vị.", 15000.0, "https://i.pinimg.com/564x/1e/35/60/1e35605b6b290bf6f9ebd0cb43f5c4f8.jpg", 0));
        drinks.add(new Food(5, "Trà Đào", "Trà đào thơm mát, ngọt ngào.", 25000.0, "https://i.pinimg.com/564x/5e/5c/a3/5e5ca327a408e12382f22ff9e0938cc2.jpg", 0));
        drinks.add(new Food(6, "Nước Dừa", "Nước dừa tươi mát lạnh.", 20000.0, "https://i.pinimg.com/564x/76/ab/04/76ab04f051e44e9cc8c85e6c4df1694d.jpg", 0));

        // Tạo danh sách category
        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("Khai Vị", "https://i.pinimg.com/originals/e3/17/84/e3178427da26d961a06e45d74c64fbd1.png", starters));
        categoryList.add(new Category("Món Chính", "https://i.pinimg.com/originals/5b/77/5e/5b775eebdfc865b15e39f92e11e22782.jpg", mainDishes));
        categoryList.add(new Category("Tráng Miệng", "https://i.pinimg.com/564x/0e/8f/aa/0e8faa1c4eec14cf3012c23da3c7ec34.jpg", desserts));
        categoryList.add(new Category("Đồ Uống", "https://i.pinimg.com/236x/2e/35/b0/2e35b05638b83ae6bce185c25797dc6d.jpg", drinks));


        // Khởi tạo adapter với danh sách món ăn
        foodAdapter = new FoodAdapter(MenuActivity.this, R.layout.row_food_grid, new ArrayList<Food>(), new FoodAdapter.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int totalQuantity, double totalPrice) {
                textQty.setText("Qty: " + totalQuantity);
                textTotal.setText("Total: " + totalPrice + " VND");
            }
        });
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