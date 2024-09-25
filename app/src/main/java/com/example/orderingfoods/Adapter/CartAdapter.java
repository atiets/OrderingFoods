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
import com.example.orderingfoods.Activity.CartActivity;
import com.example.orderingfoods.Models.Cart;
import com.example.orderingfoods.Models.Category;
import com.example.orderingfoods.Models.Food;
import com.example.orderingfoods.R;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Cart> cartList;
    private CartAdapter.OnQuantityChangeListener quantityChangeListener;


    public CartAdapter(Context context, int layout, List<Cart> cartList, CartAdapter.OnQuantityChangeListener quantityChangeListener) {
        this.context = context;
        this.layout = layout;
        this.cartList = cartList;
        this.quantityChangeListener = quantityChangeListener;
    }

    public CartAdapter(CartActivity context, ArrayList<Food> cart) {
    }


    @Override
    public int getCount() {
        return cartList != null ? cartList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return cartList != null ? cartList.get(position) : null;
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

        for (Cart cart : cartList) {
            totalQuantity += cart.getQuantity();
            totalPrice += cart.getQuantity() * cart.getPrice();
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
            convertView = inflater.inflate(layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textName = convertView.findViewById(R.id.tv_name_cart);
            viewHolder.textPrice = convertView.findViewById(R.id.tv_price_cart);
            viewHolder.textQuantity = convertView.findViewById(R.id.tv_Quantity);
            viewHolder.buttonAdd = convertView.findViewById(R.id.button_Add);
            viewHolder.buttonSubtract = convertView.findViewById(R.id.button_Subtract);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CartAdapter.ViewHolder) convertView.getTag();
        }



        Cart cart = cartList.get(position);

        // Bind data to viewHolder
        viewHolder.textName.setText(cart.getName());
        viewHolder.textPrice.setText("Giá: " + cart.getPrice() + " VND");
        viewHolder.textQuantity.setText(String.valueOf(cart.getQuantity()));

        // Handle add button click
        viewHolder.buttonAdd.setOnClickListener(v -> {
            int currentQuantity = cart.getQuantity();
            cart.setQuantity(currentQuantity + 1);
            viewHolder.textQuantity.setText(String.valueOf(cart.getQuantity()));
            updateTotal();
        });

        // Handle subtract button click
        viewHolder.buttonSubtract.setOnClickListener(v -> {
            int currentQuantity = cart.getQuantity();
            if (currentQuantity > 0) {
                cart.setQuantity(currentQuantity - 1);
                viewHolder.textQuantity.setText(String.valueOf(cart.getQuantity()));
                updateTotal();
            }
        });

        return convertView;
    }
}
