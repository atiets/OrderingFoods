package com.example.orderingfoods.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.orderingfoods.R;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] layoutOptions;

    public CustomSpinnerAdapter(Context context, String[] layoutOptions) {
        super(context, R.layout.spinner_item, layoutOptions);  // Gọi hàm khởi tạo của ArrayAdapter
        this.context = context;
        this.layoutOptions = layoutOptions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Layout hiển thị khi Spinner chưa được bấm vào
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_item, parent, false);
        }

        TextView text = convertView.findViewById(R.id.spinner_text);
        text.setText(layoutOptions[position]);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // Layout hiển thị khi Spinner xổ xuống
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_dropdown_item, parent, false);
        }

        TextView text = convertView.findViewById(R.id.spinner_text);
        text.setText(layoutOptions[position]);
        return convertView;
    }
}