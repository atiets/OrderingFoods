package com.example.orderingfoods.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.orderingfoods.Models.Food;
import com.example.orderingfoods.R;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Food> foodList;
    private OnQuantityChangeListener quantityChangeListener;
    private  ArrayList<Food> selectedFoods;



    // Constructor
    public FoodAdapter(Context context, int layout, List<Food> foodList, OnQuantityChangeListener quantityChangeListener, ArrayList<Food> selectedFoods) {
        this.context = context;
        this.layout = layout;
        this.foodList = foodList;
        this.quantityChangeListener = quantityChangeListener;
        this.selectedFoods = selectedFoods;

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
        TextView textName, textDesc, textPrice, textQuantity;
        ImageView imagePic;
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
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_food_grid, parent, false);

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
        System.out.println("foood" + food);
        // Bind data to viewHolder
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
            updateTotal();

            if (selectedFoods != null && !selectedFoods.contains(food)) {
                selectedFoods.add(food);
            }



        });

        // Handle subtract button click
        viewHolder.buttonSubtract.setOnClickListener(v -> {
            int currentQuantity = food.getQuantity();
            if (currentQuantity > 0) {
                food.setQuantity(currentQuantity - 1);
                viewHolder.textQuantity.setText(String.valueOf(food.getQuantity()));
                updateTotal();

                if (selectedFoods != null && food.getQuantity() == 0) {
                    selectedFoods.remove(food);
                }

            }
        });

        ImageButton buttonNote = convertView.findViewById(R.id.button_Note);
        buttonNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNoteDialog(parent.getContext(), position);
            }
        });

        return convertView;
    }

    private void showAddNoteDialog(Context context, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.diag_add_note, null);
        builder.setView(dialogView);

        EditText editTextNote = dialogView.findViewById(R.id.edt_note);
        Button buttonSave = dialogView.findViewById(R.id.button_save);
        Button buttonCancel = dialogView.findViewById(R.id.button_cancel);

        // Đặt ghi chú hiện tại (nếu có)
        editTextNote.setText(foodList.get(position).getNote());

        AlertDialog dialog = builder.create();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = editTextNote.getText().toString().trim();
                if (!note.isEmpty()) {
                    foodList.get(position).setNote(note); // Cập nhật ghi chú cho món ăn
                }
                dialog.dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Đóng dialog khi nhấn Cancel
            }
        });

        dialog.show();
    }
}