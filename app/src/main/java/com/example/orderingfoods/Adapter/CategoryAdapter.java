package com.example.orderingfoods.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.orderingfoods.Models.Category;
import com.example.orderingfoods.R;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Category> categoryList;

    public CategoryAdapter(Context context, int layout, List<Category> categoryList) {
        this.context = context;
        this.layout = layout;
        this.categoryList = categoryList;
    }


    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView textName;
        ImageView imagePic;
    }

    @Override
    public View getView(int position, View convertvew, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        System.out.println("locdz");
        if (convertvew == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertvew = inflater.inflate(layout, null);

            viewHolder = new ViewHolder();
            viewHolder.textName = convertvew.findViewById(R.id.tv_NameTitle_cate);
            viewHolder.imagePic = convertvew.findViewById(R.id.image_pic_cate);

            convertvew.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertvew.getTag();
        }

        Category category = categoryList.get(position);
        viewHolder.textName.setText(category.getName());

        Glide.with(context)
                .load(category.getImageResource()) // URL của ảnh
                .placeholder(R.drawable.placeholder) // Ảnh placeholder nếu URL chưa tải được
                .error(R.drawable.error_image) // Ảnh lỗi nếu không tải được URL
                .into(viewHolder.imagePic);

        System.out.println("ss" + Glide.with(context));
        return convertvew;
    }

}