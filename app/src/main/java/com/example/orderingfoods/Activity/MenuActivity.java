package com.example.orderingfoods.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderingfoods.Adapter.CategoryAdapter;
import com.example.orderingfoods.Adapter.FoodAdapter;
import com.example.orderingfoods.Models.Category;
import com.example.orderingfoods.Models.Food;
import com.example.orderingfoods.Models.Table;
import com.example.orderingfoods.R;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    Spinner layoutSpinner;
    private GridView gridView_food;
    private ListView listView_cate, listView_food;
    private ArrayList<Food> foodList;
    private FoodAdapter foodAdapter;
    private ArrayList<Category> categoryList;
    private CategoryAdapter categoryAdapter;
    private TextView textQty, textTotal;
    public int quantity = 0;
    private double totalPrice = 0.0;
    private Button saveButton;
    private ArrayList<Food> selectedFoods;
    private ImageButton btnBack;
    private Table currentTable;
    private static final int REQUEST_CODE_CART = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_foods_list);
        setContentView(R.layout.activity_menu);
        int numGuests = getIntent().getIntExtra("numGuests", 0);
        currentTable = (Table) getIntent().getSerializableExtra("currentTable");
        if (currentTable != null) {
            int tableId = currentTable.getTableId();
        }

        layoutSpinner = findViewById(R.id.layout_spinner);
        listView_cate = findViewById(R.id.listview_category);
        gridView_food = (GridView) findViewById(R.id.gridview_foods);
        listView_food = findViewById(R.id.listview_foods);
        textQty = findViewById(R.id.text_qty);
        textTotal = findViewById(R.id.text_total);
        saveButton = findViewById(R.id.button_save);
        btnBack = findViewById(R.id.btn_back);

        String[] layoutOptions = {"Grid View", "List View"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, layoutOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        layoutSpinner.setAdapter(spinnerAdapter);


        ArrayList<Food> starters = new ArrayList<>();
        Category startersCategory = new Category(1, "Khai Vị", "https://i.pinimg.com/originals/e3/17/84/e3178427da26d961a06e45d74c64fbd1.png", starters);
        starters.add(new Food(1, "Gỏi Cuốn", "Gỏi cuốn tươi ngon với tôm, thịt, rau sống và bún.", 30000.0, "https://i.pinimg.com/564x/9a/a6/7a/9aa67aed6a696a76aaba8206d06977a1.jpg", 0, startersCategory, null));
        starters.add(new Food(2, "Bánh Xèo", "Bánh xèo giòn tan với nhân tôm, thịt và giá đỗ.", 35000.0, "https://i.pinimg.com/564x/45/c7/f3/45c7f36b43ec92e65e0a7a7698c5de9b.jpg", 0, startersCategory, null));
        starters.add(new Food(3, "Nem Rán", "Nem rán giòn rụm với nhân thịt và rau củ.", 40000.0, "https://i.pinimg.com/564x/1d/56/d6/1d56d6aa5070d3cb9fdc85ecf8de8a8e.jpg", 0, startersCategory, null));
        starters.add(new Food(4, "Chả Giò", "Chả giò giòn rụm với nhân thịt và nấm.", 35000.0, "https://i.pinimg.com/564x/34/56/8b/34568b8470f3e739c0796ff63465477a.jpg", 0, startersCategory, null));
        starters.add(new Food(5, "Sò Huyết Nướng", "Sò huyết nướng mỡ hành, thơm ngon hấp dẫn.", 50000.0, "https://i.pinimg.com/564x/57/c1/ee/57c1ee7178b5b65b5e2c5d8a90b52fc8.jpg", 0, startersCategory, null));
        starters.add(new Food(6, "Bánh Bột Lọc", "Bánh bột lọc với nhân tôm và thịt heo.", 30000.0, "https://i.pinimg.com/564x/1d/5f/26/1d5f2649ad12c1f752b761e4b4b5835e.jpg", 0, startersCategory, null));


        ArrayList<Food> mainDishes = new ArrayList<>();
        Category mainDishesCategory = new Category(2, "Món Chính", "https://i.pinimg.com/originals/5b/77/5e/5b775eebdfc865b15e39f92e11e22782.jpg", mainDishes);
        mainDishes.add(new Food(4, "Phở Bò", "Món phở truyền thống với thịt bò, rau thơm và nước dùng đậm đà.", 45000.0, "https://i.pinimg.com/564x/80/f0/5e/80f05eb75bea006de7364f102fd7b407.jpg", 0, mainDishesCategory, null));
        mainDishes.add(new Food(5, "Cơm Tấm", "Cơm tấm sườn nướng với trứng, chả và dưa chua.", 50000.0, "https://i.pinimg.com/564x/75/f3/9f/75f39f3ba8033ee5201532c6d54dc7cd.jpg", 0, mainDishesCategory, null));
        mainDishes.add(new Food(6, "Bún Chả", "Bún chả Hà Nội với thịt nướng, bún, rau sống và nước mắm chua ngọt.", 40000.0, "https://i.pinimg.com/564x/41/9c/c8/419cc8a05227d4b4ab7550612149fbb1.jpg", 0, mainDishesCategory, null));
        mainDishes.add(new Food(10, "Mì Xào Thịt Bò", "Mì xào thịt bò với rau củ và gia vị đặc biệt.", 45000.0, "https://i.pinimg.com/564x/a0/33/fc/a033fc7f944abf28d1f0a0c8e91a8ecf.jpg", 0, mainDishesCategory, null));
        mainDishes.add(new Food(11, "Gà Rán", "Gà rán giòn rụm với lớp vỏ ngoài giòn tan.", 50000.0, "https://i.pinimg.com/564x/f5/8d/5c/f58d5c1e5468a56d0ecdf6b1b4073b54.jpg", 0, mainDishesCategory, null));
        mainDishes.add(new Food(12, "Sườn Nướng", "Sườn nướng BBQ với sốt đặc biệt và gia vị.", 55000.0, "https://i.pinimg.com/564x/94/64/89/946489d6cb223bd768ea63cb9d0715a6.jpg", 0, mainDishesCategory, null));



        ArrayList<Food> desserts = new ArrayList<>();
        Category dessertsCategory = new Category(3, "Tráng Miệng", "https://i.pinimg.com/564x/0e/8f/aa/0e8faa1c4eec14cf3012c23da3c7ec34.jpg", desserts);
        desserts.add(new Food(7, "Bánh Flan", "Bánh flan mềm mịn với vị caramel thơm ngon.", 25000.0, "https://i.pinimg.com/564x/88/5e/4f/885e4f17b7d0f4621d5f865b1b1f2c6f.jpg", 0, dessertsCategory, null));
        desserts.add(new Food(8, "Chè Bưởi", "Chè bưởi ngọt thanh với cùi bưởi và đậu xanh.", 20000.0, "https://i.pinimg.com/564x/a0/ef/9a/a0ef9ad6a0f3a2d45f1b760b9efc20bb.jpg", 0, dessertsCategory, null));
        desserts.add(new Food(9, "Bánh Mochi", "Bánh mochi mềm mại với nhân đậu đỏ.", 30000.0, "https://i.pinimg.com/564x/53/3f/8f/533f8f9e30e6f30a90f78cb27b8e6345.jpg", 0, dessertsCategory, null));



        ArrayList<Food> drinks = new ArrayList<>();
        Category drinksCategory = new Category(4, "Đồ Uống", "https://i.pinimg.com/236x/2e/35/b0/2e35b05638b83ae6bce185c25797dc6d.jpg", drinks);
        drinks.add(new Food(10, "Trà Sữa", "Trà sữa thơm ngon với trân châu mềm.", 30000.0, "https://i.pinimg.com/564x/76/d0/3d/76d03dc2ff74f8d046b52e0288cf4815.jpg", 0, drinksCategory, null));
        drinks.add(new Food(11, "Cà Phê Sữa", "Cà phê sữa đá đậm đà, thơm ngon.", 25000.0, "https://i.pinimg.com/564x/2e/35/b0/2e35b05638b83ae6bce185c25797dc6d.jpg", 0, drinksCategory, null));
        drinks.add(new Food(12, "Nước Ép Trái Cây", "Nước ép trái cây tươi ngon.", 30000.0, "https://i.pinimg.com/564x/d5/bb/6d/d5bb6d8cb0cf08d71a272c8859f1f2a4.jpg", 0, drinksCategory, null));


        ArrayList<Food> allFoods = new ArrayList<>();
        allFoods.addAll(starters);
        allFoods.addAll(mainDishes);
        allFoods.addAll(desserts);
        allFoods.addAll(drinks);

         //Tạo danh sách category
        categoryList = new ArrayList<>();
        categoryList.add(startersCategory);
        categoryList.add(mainDishesCategory);
        categoryList.add(dessertsCategory);
        categoryList.add(drinksCategory);
//        // Tạo danh sách category
//        categoryList = new ArrayList<>();
//        categoryList.add(new Category(1, "Khai Vị", "https://i.pinimg.com/originals/e3/17/84/e3178427da26d961a06e45d74c64fbd1.png", starters));
//        categoryList.add(new Category(2,"Món Chính", "https://i.pinimg.com/originals/5b/77/5e/5b775eebdfc865b15e39f92e11e22782.jpg", mainDishes));
//        categoryList.add(new Category(3,"Tráng Miệng", "https://i.pinimg.com/564x/0e/8f/aa/0e8faa1c4eec14cf3012c23da3c7ec34.jpg", desserts));
//        categoryList.add(new Category(4,"Đồ Uống", "https://i.pinimg.com/236x/2e/35/b0/2e35b05638b83ae6bce185c25797dc6d.jpg", drinks));



        selectedFoods = new ArrayList<>();
        int layout = R.layout.row_food_grid;
        // Khởi tạo adapter với danh sách món ăn
        foodAdapter = new FoodAdapter(MenuActivity.this, layout, allFoods,  new FoodAdapter.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int totalQuantity, double totalPrice) {
                textQty.setText("Qty: " + totalQuantity);
                textTotal.setText("Total: " + totalPrice + " VND");
            }
        }, selectedFoods);
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
                int layout = R.layout.row_food_grid;
                if(selectedLayout.equals("Grid View")) {
                    layout = R.layout.row_food_grid;
                    gridView_food.setVisibility(View.VISIBLE);
                    listView_food.setVisibility(View.GONE);
                } else if (selectedLayout.equals("List View")) {
                    layout = R.layout.row_food_list;
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
                Food selectedFood = allFoods.get(position);
                boolean isFoodAlreadyInCart = false;
                for (Food food : selectedFoods) {
                    if (food.getId() == selectedFood.getId()) {
                        food.setQuantity(food.getQuantity() + 1); // Tăng số lượng nếu món đã có trong giỏ hàng
                        isFoodAlreadyInCart = true;
                        break;
                    }
                }

                System.out.println("ddd" + selectedFood);

                if (!isFoodAlreadyInCart) {
                    selectedFood.setQuantity(1); // Đặt số lượng là 1 nếu thêm món mới
                    selectedFoods.add(selectedFood);
                }

                // Cập nhật lại thông tin giỏ hàng
                textQty.setText("Qty: " + selectedFoods.size());
                double totalPrice = 0.0;
                for (Food food : selectedFoods) {
                    totalPrice += food.getQuantity() * food.getPrice();
                }
                textTotal.setText("Total: " + totalPrice + " VND");

                // Cập nhật giao diện giỏ hàng
                foodAdapter.notifyDataSetChanged();
            }
        });


        listView_food.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Food selectedFood = allFoods.get(position);
                boolean isFoodAlreadyInCart = false;
                for (Food food : selectedFoods) {
                    if (food.getId() == selectedFood.getId()) {
                        food.setQuantity(food.getQuantity() + 1); // Tăng số lượng nếu món đã có trong giỏ hàng
                        isFoodAlreadyInCart = true;
                        break;
                    }
                }

                if (!isFoodAlreadyInCart) {
                    selectedFood.setQuantity(1); // Đặt số lượng là 1 nếu thêm món mới
                    selectedFoods.add(selectedFood);
                }

                // Cập nhật lại thông tin giỏ hàng
                textQty.setText("Qty: " + selectedFoods.size());
                double totalPrice = 0.0;
                for (Food food : selectedFoods) {
                    totalPrice += food.getQuantity() * food.getPrice();
                }
                textTotal.setText("Total: " + totalPrice + " VND");

                // Cập nhật giao diện giỏ hàng
                foodAdapter.notifyDataSetChanged();
            }
        });

//        listView_cate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Category selectedCategory = categoryList.get(position);
//                ArrayList<Food> filteredFoods = new ArrayList<>();
//                for (Food food : allFoods) {
//                    if (food.getCategory().getId() == selectedCategory.getId()) {
//                        filteredFoods.add(food);
//                    }
//                }
//
//                // Update the adapter with the filtered list
//                foodAdapter.updateFoodList(filteredFoods);
//
//                // Optional: Highlight the selected category in the list
//                Food food = (Food) foodAdapter.getItem(position);
//                highlightCategory(food.getCategory().getId());
//                categoryAdapter.notifyDataSetChanged();
//            }
//        });

        listView_food.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (totalItemCount > 0) {
                    View firstVisibleChild = listView_food.getChildAt(0);
                    if (firstVisibleChild != null) {
                        int position = listView_food.getPositionForView(firstVisibleChild);
                        Food food = (Food) foodAdapter.getItem(position);
                        highlightCategory(food.getCategory().getId());
                    }
                }
            }
        });


        gridView_food.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (totalItemCount > 0) {
                    View firstVisibleChild = gridView_food.getChildAt(0);
                    if (firstVisibleChild != null) {
                        int position = gridView_food.getPositionForView(firstVisibleChild);
                        Food food = (Food) foodAdapter.getItem(position);
                        highlightCategory(food.getCategory().getId());
                    }
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedFoods.isEmpty()) {
                    Toast.makeText(MenuActivity.this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MenuActivity.this, CartActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectedFoods", selectedFoods);
                    intent.putExtras(bundle);
                    intent.putExtra("totalQuantity", quantity);
                    intent.putExtra("totalPrice", totalPrice);
                    intent.putExtra("currentTable", currentTable);
                    intent.putExtra("numGuests", numGuests);
                    startActivityForResult(intent, REQUEST_CODE_CART);
                    //startActivity(intent);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Hoặc bạn có thể gọi một Activity khác
            }
        });



    }

    private void highlightCategory(int categoryId) {
        for (int i = 0; i < categoryAdapter.getCount(); i++) {
            View view = listView_cate.getChildAt(i);
            if(view != null) {
                Category category = (Category) categoryAdapter.getItem(i);
                if (category.getId() == categoryId) {
                    view.setBackgroundColor(getColor(R.color.selected_color));
                } else {
                    view.setBackgroundColor(getColor(R.color.default_color));
                }
            }

        }
    }


}