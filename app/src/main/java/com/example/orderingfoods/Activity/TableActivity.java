package com.example.orderingfoods.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.orderingfoods.Adapter.TableAdapter;
import com.example.orderingfoods.Models.Table;
import com.example.orderingfoods.Models.User;
import com.example.orderingfoods.R;

import java.util.ArrayList;

public class TableActivity extends AppCompatActivity {
    private GridView gridView;
    private ArrayList<Table> tableArrayList;
    TableAdapter tableAdapter;
    TextView textViewNumGuests, textViewNum, textViewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        gridView = findViewById(R.id.gridview_tables);
        textViewName = findViewById(R.id.tv_NameTitle_table);

        ArrayList<Table> tableArrayList = new ArrayList<>();

        tableAdapter = new TableAdapter(TableActivity.this, R.layout.row_table, tableArrayList);
        gridView.setAdapter(tableAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Table selectedTable = tableArrayList.get(position);

                textViewNumGuests = view.findViewById(R.id.textViewNumGuests);
                textViewNum = view.findViewById(R.id.textViewNum);

                textViewNumGuests.setText(String.valueOf(selectedTable.getNumberOfGuests()));
                textViewNum.setText(String.valueOf("Bàn " + selectedTable.getTableId()));

                // Hiển thị trạng thái bàn
                Toast.makeText(TableActivity.this, "Bàn " + selectedTable.getTableId() + ": " + selectedTable.getStatus(), Toast.LENGTH_SHORT).show();

                if (selectedTable.getStatus().equals("Trống")) {
                    showAddNumberDialog(selectedTable);
                }
            }
        });


        tableArrayList.add(new Table(1, "Trống", 0));
        tableArrayList.add(new Table(2, "Đã đặt", 4));
        tableArrayList.add(new Table(3, "Đang phục vụ", 2));
        tableArrayList.add(new Table(4, "Trống", 0));
        tableArrayList.add(new Table(5, "Đang phục vụ", 5));
        tableArrayList.add(new Table(6, "Trống", 0));
        tableArrayList.add(new Table(7, "Đã đặt", 3));
        tableArrayList.add(new Table(8, "Trống", 0));
        tableArrayList.add(new Table(9, "Đang phục vụ", 6));
        tableArrayList.add(new Table(10, "Trống", 0));
        tableArrayList.add(new Table(11, "Đang phục vụ", 4));
        tableArrayList.add(new Table(12, "Trống", 0));
        tableArrayList.add(new Table(13, "Trống", 0));
        tableArrayList.add(new Table(14, "Đang phục vụ", 2));
        tableArrayList.add(new Table(15, "Trống", 0));

    }

    private void showAddNumberDialog(Table selectedTable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.diag_add_number, null);
        builder.setView(dialogView);

        EditText edtNumGuests = dialogView.findViewById(R.id.edt_numGuests);
        Button buttonSave = dialogView.findViewById(R.id.button_save);
        Button buttonCancel = dialogView.findViewById(R.id.button_cancel);

        AlertDialog dialog = builder.create();

        buttonSave.setOnClickListener(v -> {
            String numGuestsString = edtNumGuests.getText().toString();
            if (!numGuestsString.isEmpty()) {
                int numGuests = Integer.parseInt(numGuestsString);
                selectedTable.setNumberOfGuests(numGuests);
                selectedTable.setStatus("Đang phục vụ");
                tableAdapter.notifyDataSetChanged(); // Cập nhật giao diện

                Intent intent = new Intent(TableActivity.this, MenuActivity.class);
                intent.putExtra("table_id", selectedTable.getTableId());
                startActivity(intent);
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Vui lòng nhập số lượng khách", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện hủy
        buttonCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
