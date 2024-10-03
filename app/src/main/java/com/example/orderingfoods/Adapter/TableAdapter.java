package com.example.orderingfoods.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.orderingfoods.Models.Table;
import com.example.orderingfoods.R;

import java.util.List;

public class TableAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Table> tableList;

    public TableAdapter(Context context, int layout, List<Table> tableList) {
        this.context = context;
        this.layout = layout;
        this.tableList = tableList;
    }


    @Override
    public int getCount() {
        return  tableList.size();
    }

    @Override
    public Object getItem(int position) {
        return tableList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView textViewNumGuests, textViewNum;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_table, null);

            viewHolder = new ViewHolder();
            viewHolder.textViewNumGuests = convertView.findViewById(R.id.textViewNumGuests);
            viewHolder.textViewNum = convertView.findViewById(R.id.textViewNum);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Table table = tableList.get(position);
        viewHolder.textViewNumGuests.setText(String.valueOf(table.getNumberOfGuests()));
        viewHolder.textViewNum.setText(String.valueOf(table.getTableId()));

        CardView cardView = convertView.findViewById(R.id.card_view);

        switch (table.getStatus()) {
            case "Trống":
                cardView.setCardBackgroundColor(context.getResources().getColor(R.color.default_color));
                break;
            case "Đã đặt":
                cardView.setCardBackgroundColor(context.getResources().getColor(R.color.red));
                break;
            case "Đang phục vụ":
                cardView.setCardBackgroundColor(context.getResources().getColor(R.color.green));
                break;
            default:
                cardView.setCardBackgroundColor(context.getResources().getColor(R.color.default_color));
                break;
        }

        return convertView;
    }

    public void updateTable(int position, int numGuests, String status) {
        Table table = tableList.get(position);
        table.setNumberOfGuests(numGuests);
        table.setStatus(status);
        notifyDataSetChanged();
    }
}
