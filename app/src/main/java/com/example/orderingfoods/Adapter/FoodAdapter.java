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
import com.example.orderingfoods.Models.Food;
import com.example.orderingfoods.R;

import java.util.List;

public class FoodAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Food> foodList;

    // Constructor
    public FoodAdapter(Context context, int layout, List<Food> foodList) {
        this.context = context;
        this.layout = layout;
        this.foodList = foodList;
    }

    public void updateFoodList(List<Food> newFoodList) {
        this.foodList = newFoodList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView textName, textDesc, textPrice, textQuantity;
        ImageView imagePic;
        ImageButton buttonAdd, buttonSubtract;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            viewHolder = new ViewHolder();
            viewHolder.textName = convertView.findViewById(R.id.tv_NameTitle);
            viewHolder.textDesc = convertView.findViewById(R.id.tv_DescTitle);
            viewHolder.textPrice = convertView.findViewById(R.id.tv_Price);
            viewHolder.textQuantity = convertView.findViewById(R.id.tv_Quantity);
            viewHolder.imagePic = convertView.findViewById(R.id.image_pic);
            viewHolder.buttonAdd = convertView.findViewById(R.id.button_Add);
            viewHolder.buttonSubtract = convertView.findViewById(R.id.button_Subtract);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Food food = foodList.get(position);
        viewHolder.textName.setText(food.getName());
        viewHolder.textDesc.setText(food.getDescription());
        viewHolder.textPrice.setText("Giá: " + food.getPrice() + " VND");
        viewHolder.textQuantity.setText(String.valueOf(food.getQuantity()));

        // Load image using Glide
        Glide.with(context)
                .load(food.getImageUrl()) // URL của ảnh
                .placeholder(R.drawable.placeholder) // Ảnh placeholder nếu URL chưa tải được
                .error(R.drawable.error_image) // Ảnh lỗi nếu không tải được URL
                .into(viewHolder.imagePic);

        // Handle add button click
        viewHolder.buttonAdd.setOnClickListener(v -> {
            int currentQuantity = food.getQuantity();
            food.setQuantity(currentQuantity + 1);
            viewHolder.textQuantity.setText(String.valueOf(food.getQuantity()));
            notifyDataSetChanged();
        });

        // Handle subtract button click
        viewHolder.buttonSubtract.setOnClickListener(v -> {
            int currentQuantity = food.getQuantity();
            if (currentQuantity > 0) {
                food.setQuantity(currentQuantity - 1);
                viewHolder.textQuantity.setText(String.valueOf(food.getQuantity()));
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}