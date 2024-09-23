package com.example.orderingfoods.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.orderingfoods.Models.Category;
import com.example.orderingfoods.Models.Food;
import com.example.orderingfoods.R;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Food> foodList;
    private FoodAdapter.OnQuantityChangeListener quantityChangeListener;


    public CartAdapter(Context context, int layout, List<Food> foodList, FoodAdapter.OnQuantityChangeListener quantityChangeListener) {
        this.context = context;
        this.layout = layout;
        this.foodList = foodList;
        this.quantityChangeListener = quantityChangeListener;
    }


    @Override
    public int getCount() {
        return foodList != null ? foodList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return foodList != null ? foodList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView textName, textPrice, textQuantity;
        ImageButton buttonAdd, buttonSubtract;
    }

    public interface OnQuantityChangeListener {
        void onQuantityChanged(int totalQuantity, double totalPrice);
    }


    private void updateTotal() {
        int totalQuantity = 0;
        double totalPrice = 0.0;

        for (Food food : foodList) {
            totalQuantity += food.getQuantity();
            totalPrice += food.getQuantity() * food.getPrice();
        }

        if (quantityChangeListener != null) {
            quantityChangeListener.onQuantityChanged(totalQuantity, totalPrice);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CartAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, parent, false);

            viewHolder = new CartAdapter.ViewHolder();
            viewHolder.textName = convertView.findViewById(R.id.tv_name_cart);
            viewHolder.textPrice = convertView.findViewById(R.id.tv_price_cart);
            viewHolder.textQuantity = convertView.findViewById(R.id.tv_Quantity);
            viewHolder.buttonAdd = convertView.findViewById(R.id.button_Add);
            viewHolder.buttonSubtract = convertView.findViewById(R.id.button_Subtract);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CartAdapter.ViewHolder) convertView.getTag();
        }



        Food food = foodList.get(position);

        // Bind data to viewHolder
        viewHolder.textName.setText(food.getName());
        viewHolder.textPrice.setText("Giá: " + food.getPrice() + " VND");
        viewHolder.textQuantity.setText(String.valueOf(food.getQuantity()));

        // Handle add button click
        viewHolder.buttonAdd.setOnClickListener(v -> {
            int currentQuantity = food.getQuantity();
            food.setQuantity(currentQuantity + 1);
            viewHolder.textQuantity.setText(String.valueOf(food.getQuantity()));
            updateTotal();
        });

        // Handle subtract button click
        viewHolder.buttonSubtract.setOnClickListener(v -> {
            int currentQuantity = food.getQuantity();
            if (currentQuantity > 0) {
                food.setQuantity(currentQuantity - 1);
                viewHolder.textQuantity.setText(String.valueOf(food.getQuantity()));
                updateTotal();
            }
        });

        return convertView;
    }
}
