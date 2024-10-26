package com.example.orderingfoods.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
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
import com.example.orderingfoods.Adapter.CustomSpinnerAdapter;
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
        setContentView(R.layout.activity_menu);

        int numGuests = getIntent().getIntExtra("numGuests", 0);
        currentTable = (Table) getIntent().getSerializableExtra("currentTable");
        String arrivalTime = getIntent().getStringExtra("arrivalTime"); // Correct method to retrieve arrivalTime

        int tableId = -1;
        if (currentTable != null) {
            tableId = currentTable.getTableId();
        }
        Log.d("MenuActivity", "Table ID: " + tableId);

        layoutSpinner = findViewById(R.id.layout_spinner);
        listView_cate = findViewById(R.id.listview_category);
        gridView_food = (GridView) findViewById(R.id.gridview_foods);
        listView_food = findViewById(R.id.listview_foods);
        textQty = findViewById(R.id.text_qty);
        textTotal = findViewById(R.id.text_total);
        saveButton = findViewById(R.id.button_save);
        btnBack = findViewById(R.id.btn_back);

        String[] layoutOptions = {"Grid View", "List View"};
        CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(this, layoutOptions);
        layoutSpinner.setAdapter(spinnerAdapter);

        ArrayList<Food> starters = new ArrayList<>();
        Category startersCategory = new Category(1, "Khai Vị", "https://i.pinimg.com/originals/e3/17/84/e3178427da26d961a06e45d74c64fbd1.png", starters);
        starters.add(new Food(1, "Gỏi Cuốn", "Gỏi cuốn tươi ngon với tôm, thịt, rau sống và bún.", 30000.0, "https://i.pinimg.com/236x/cc/2a/cf/cc2acf8225e98630524d2d383901dea4.jpg", 0, startersCategory, null));
        starters.add(new Food(2, "Bánh Xèo", "Bánh xèo giòn tan với nhân tôm, thịt và giá đỗ.", 35000.0, "https://i.pinimg.com/564x/05/08/22/0508226df92f4142601a8b640171ea3d.jpg", 0, startersCategory, null));
        starters.add(new Food(3, "Nem Rán", "Nem rán giòn rụm với nhân thịt và rau củ.", 40000.0, "https://i.pinimg.com/enabled_hi/236x/aa/95/96/aa959690c55dfb4600b4db9bf5539e04.jpg", 0, startersCategory, null));
        starters.add(new Food(4, "Chả Giò", "Chả giò giòn rụm với nhân thịt và nấm.", 35000.0, "https://i.pinimg.com/236x/13/18/cd/1318cd36a4e7a72033be66b707121651.jpg", 0, startersCategory, null));
        starters.add(new Food(5, "Sò Nướng", "Sò huyết nướng mỡ hành.", 50000.0, "https://i.pinimg.com/236x/23/b7/0f/23b70f75d312d581d2aec5bb872b212b.jpg", 0, startersCategory, null));
        starters.add(new Food(6, "Bánh Bột Lọc", "Bánh bột lọc với nhân tôm và thịt heo.", 30000.0, "https://i.pinimg.com/474x/63/83/e6/6383e60e1bd28b828ac2992469d8e525.jpg", 0, startersCategory, null));


        ArrayList<Food> mainDishes = new ArrayList<>();
        Category mainDishesCategory = new Category(2, "Món Chính", "https://i.pinimg.com/enabled_hi/564x/ab/18/11/ab181181d836027898016b6b560e484f.jpg", mainDishes);
        mainDishes.add(new Food(4, "Phở Bò", "Món phở truyền thống với thịt bò và nước dùng đậm đà.", 45000.0, "https://i.pinimg.com/564x/80/f0/5e/80f05eb75bea006de7364f102fd7b407.jpg", 0, mainDishesCategory, null));
        mainDishes.add(new Food(5, "Cơm Tấm", "Cơm tấm sườn nướng với trứng, chả và dưa chua.", 50000.0, "https://i.pinimg.com/564x/75/f3/9f/75f39f3ba8033ee5201532c6d54dc7cd.jpg", 0, mainDishesCategory, null));
        mainDishes.add(new Food(6, "Bún Chả", "Bún chả với thịt nướng, nước mắm chua ngọt.", 40000.0, "https://i.pinimg.com/564x/41/9c/c8/419cc8a05227d4b4ab7550612149fbb1.jpg", 0, mainDishesCategory, null));
        mainDishes.add(new Food(10, "Mì Xào Bò", "Mì xào thịt bò với rau củ và gia vị đặc biệt.", 45000.0, "https://i.pinimg.com/236x/33/04/73/330473886f9c7a162e3af18dae5ab73d.jpg", 0, mainDishesCategory, null));
        mainDishes.add(new Food(11, "Gà Rán", "Gà rán giòn rụm với lớp vỏ ngoài giòn tan.", 50000.0, "https://i.pinimg.com/236x/5c/7a/bf/5c7abf4bf3ac7440af505641a682d7cc.jpg", 0, mainDishesCategory, null));
        mainDishes.add(new Food(12, "Sườn Nướng", "Sườn nướng BBQ với sốt đặc biệt và gia vị.", 55000.0, "https://i.pinimg.com/236x/fd/8e/cb/fd8ecb3bb3730ef843a185cf025e3753.jpg", 0, mainDishesCategory, null));



        ArrayList<Food> desserts = new ArrayList<>();
        Category dessertsCategory = new Category(3, "Tráng Miệng", "https://i.pinimg.com/enabled_hi/564x/80/35/60/803560020f0f772bb12862e1eb2f50c0.jpg", desserts);
        desserts.add(new Food(7, "Bánh Flan", "Bánh flan mềm mịn với vị caramel thơm ngon.", 25000.0, "https://i.pinimg.com/236x/93/af/35/93af35792cb4c4892173d0786cf95743.jpg", 0, dessertsCategory, null));
        desserts.add(new Food(8, "Chè Bưởi", "Chè bưởi ngọt thanh với cùi bưởi.", 20000.0, "https://i.pinimg.com/236x/7f/2b/6c/7f2b6c185d4c3d0674c39eaba8a476d0.jpg", 0, dessertsCategory, null));
        desserts.add(new Food(9, "Bánh Mochi", "Bánh mochi mềm mại với nhân đậu đỏ.", 30000.0, "https://i.pinimg.com/236x/8b/c6/48/8bc64842ad2e9bde43fdaca1800fc05e.jpg", 0, dessertsCategory, null));



        ArrayList<Food> drinks = new ArrayList<>();
        Category drinksCategory = new Category(4, "Đồ Uống", "https://i.pinimg.com/236x/2e/35/b0/2e35b05638b83ae6bce185c25797dc6d.jpg", drinks);
        drinks.add(new Food(10, "Trà Sữa", "Trà sữa thơm ngon với trân châu mềm.", 30000.0, "https://i.pinimg.com/236x/4b/96/75/4b9675dfa453afb780b5d279bf27606d.jpg", 0, drinksCategory, null));
        drinks.add(new Food(11, "Cà Phê Sữa", "Cà phê sữa đá đậm đà, thơm ngon.", 25000.0, "https://i.pinimg.com/236x/c5/8a/88/c58a88f9190b8b751a4576edca99f7ef.jpg", 0, drinksCategory, null));
        drinks.add(new Food(12, "Nước Ép", "Nước ép trái cây tươi ngon.", 30000.0, "https://i.pinimg.com/236x/ab/c1/56/abc15600e3514b8a780466d14bec1658.jpg", 0, drinksCategory, null));
        drinks.add(new Food(14, "Trà Đào", "Trà đào thanh mát, dễ uống.", 40000.0, "https://i.pinimg.com/236x/fc/98/22/fc9822308ce163590ab06410dc7f0e76.jpg", 0, drinksCategory, null));
        drinks.add(new Food(15, "Nước Dừa", "Nước dừa tươi mát lạnh, bổ dưỡng.", 25000.0, "https://i.pinimg.com/236x/6e/d1/e7/6ed1e78c709548de620ba51db2e5507f.jpg", 0, drinksCategory, null));
        drinks.add(new Food(16, "Soda Chanh", "Soda chanh chua ngọt, sảng khoái.", 20000.0, "https://i.pinimg.com/236x/56/b8/c6/56b8c6a106736c81794328767a6153d6.jpg", 0, drinksCategory, null));
        drinks.add(new Food(20, "Sữa Đậu Nành", "Sữa đậu nành mát lành, bổ dưỡng.", 30000.0, "https://i.pinimg.com/236x/d6/a8/35/d6a8353bc3d1b1617ec3657c33b149ce.jpg", 0, drinksCategory, null));

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
                if (selectedLayout.equals("Grid View")) {
                    gridView_food.setVisibility(View.VISIBLE);
                    gridView_food.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in));
                    listView_food.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out));
                    listView_food.setVisibility(View.GONE);
                } else if (selectedLayout.equals("List View")) {
                    listView_food.setVisibility(View.VISIBLE);
                    listView_food.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in));
                    gridView_food.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out));
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
                    int position = gridView_food.getFirstVisiblePosition();
                    Food food = (Food) foodAdapter.getItem(position);
                    highlightCategory(food.getCategory().getId());
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
                    intent.putExtra("arrivalTime", arrivalTime);
                    if (currentTable != null) {
                        int tableId = currentTable.getTableId();
                        intent.putExtra("tableId", tableId);
                    }

                    startActivityForResult(intent, REQUEST_CODE_CART);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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