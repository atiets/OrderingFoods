package com.example.orderingfoods.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.orderingfoods.Models.Cart;
import com.example.orderingfoods.R;

import java.util.ArrayList;

public class BillAdapter extends ArrayAdapter<Cart> {
    private Context context;
    private int resource;
    private ArrayList<Cart> orderList;

    public BillAdapter(Context context, int resource, ArrayList<Cart> orderList) {
        super(context, resource, orderList);
        this.context = context;
        this.resource = resource;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(resource, parent, false);

        TextView foodName = convertView.findViewById(R.id.tv_name_bill);
        TextView quantity = convertView.findViewById(R.id.tv_Quantity_bill);
        TextView totalPrice = convertView.findViewById(R.id.tv_totalPrice_bill);

        Cart order = orderList.get(position);

        foodName.setText(order.getName());
        quantity.setText(String.valueOf(order.getQuantity()));
        totalPrice.setText(String.valueOf(order.getPrice()));

        return convertView;
    }
}