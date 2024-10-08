package com.example.orderingfoods.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.orderingfoods.Adapter.TableAdapter;
import com.example.orderingfoods.Models.Food;
import com.example.orderingfoods.Models.Table;
import com.example.orderingfoods.Models.User;
import com.example.orderingfoods.R;

import java.util.ArrayList;

public class TableActivity extends AppCompatActivity {
    private GridView gridView;
    private ArrayList<Table> tableArrayList;
    TableAdapter tableAdapter;
    TextView textViewNumGuests, textViewNum, textViewName;
    private static final int REQUEST_CODE_CART = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        gridView = findViewById(R.id.gridview_tables);
        textViewName = findViewById(R.id.tv_NameTitle_table);

        tableArrayList = new ArrayList<>();

        tableAdapter = new TableAdapter(TableActivity.this, R.layout.row_table, tableArrayList);
        gridView.setAdapter(tableAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Table selectedTable = tableArrayList.get(position);

                textViewNumGuests = view.findViewById(R.id.textViewNumGuests);
                textViewNum = view.findViewById(R.id.textViewNum);

                textViewNumGuests.setText(String.valueOf(selectedTable.getNumberOfGuests()));
                textViewNum.setText(String.valueOf(selectedTable.getTableId()));

                // Hiển thị trạng thái bàn
                Toast.makeText(TableActivity.this, "Bàn " + selectedTable.getTableId() + ": " + selectedTable.getStatus(), Toast.LENGTH_SHORT).show();

                if (selectedTable.getStatus().equals("Trống") || selectedTable.getStatus().equals("Đã đặt")) {
                    showAddNumberDialog(selectedTable);
                }else if (selectedTable.getStatus().equals("Đang phục vụ")) {
                    // Khởi chạy BillActivity để hiển thị hóa đơn
                    Intent intent = new Intent(TableActivity.this, BillActivity.class);
                    intent.putExtra("currentTable", selectedTable);
                    startActivity(intent);
                }
            }
        });

        tableArrayList.add(new Table(1, "Trống", 0, null));
        tableArrayList.add(new Table(2, "Đã đặt", 4, null));
        tableArrayList.add(new Table(3, "Trống", 0, null));
        tableArrayList.add(new Table(4, "Trống", 0, null));
        tableArrayList.add(new Table(5, "Trống", 0, null));
        tableArrayList.add(new Table(6, "Trống", 0, null));
        tableArrayList.add(new Table(7, "Đã đặt", 3, null));
        tableArrayList.add(new Table(8, "Trống", 0, null));
        tableArrayList.add(new Table(9, "Trống", 0, null));
        tableArrayList.add(new Table(10, "Trống", 0, null));
        tableArrayList.add(new Table(11, "Trống", 0, null));
        tableArrayList.add(new Table(12, "Trống", 0, null));
        tableArrayList.add(new Table(13, "Trống", 0, null));
        tableArrayList.add(new Table(14, "Trống", 0, null));
        tableArrayList.add(new Table(15, "Trống", 0, null));
        tableArrayList.add(new Table(16, "Trống", 0, null));
        tableArrayList.add(new Table(17, "Đã đặt", 2, null));
        tableArrayList.add(new Table(18, "Trống", 0, null));
        tableArrayList.add(new Table(19, "Trống", 0, null));
        tableArrayList.add(new Table(20, "Trống", 0, null));
        tableArrayList.add(new Table(21, "Trống", 0, null));
        tableArrayList.add(new Table(22, "Đã đặt", 1, null));
        tableArrayList.add(new Table(23, "Trống", 0, null));
        tableArrayList.add(new Table(24, "Trống", 0, null));
        tableArrayList.add(new Table(25, "Trống", 0, null));
        tableArrayList.add(new Table(26, "Trống", 0, null));
        tableArrayList.add(new Table(27, "Đã đặt", 4, null));
        tableArrayList.add(new Table(28, "Trống", 0, null));
        tableArrayList.add(new Table(29, "Trống", 0, null));
        tableArrayList.add(new Table(30, "Trống", 0, null));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CART && resultCode == RESULT_OK && data != null) {
            // Nhận thông tin bàn đã cập nhật
            int tablePosition = data.getIntExtra("tablePosition", -1);
            int numGuests = data.getIntExtra("numGuests", 0);
            String status = data.getStringExtra("status");
            ArrayList<Food> selectedFoods = (ArrayList<Food>) data.getSerializableExtra("selectedFoods");

            // Cập nhật bàn trong TableAdapter
            if (tablePosition != -1) {
                Table updatedTable = tableArrayList.get(tablePosition); // Dùng tablePosition để lấy bàn đã cập nhật
                updatedTable.setNumberOfGuests(numGuests);
                updatedTable.setStatus(status);

                if (selectedFoods != null) {
                    updatedTable.setOrderedFoods(selectedFoods);
                }

                tableAdapter.notifyDataSetChanged(); // Cập nhật giao diện
            }
        }
    }

    private void showAddNumberDialog(Table selectedTable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.diag_add_number, null);
        builder.setView(dialogView);

        EditText edtNumGuests = dialogView.findViewById(R.id.edt_numGuests);
        Button buttonSave = dialogView.findViewById(R.id.button_save);
        Button buttonCancel = dialogView.findViewById(R.id.button_cancel);

        if (selectedTable.getStatus().equals("Đã đặt")) {
            edtNumGuests.setText(String.valueOf(selectedTable.getNumberOfGuests()));
        }

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
                intent.putExtra("currentTable", selectedTable);
                intent.putExtra("numGuests", numGuests);
                startActivityForResult(intent, REQUEST_CODE_CART);
                dialog.dismiss();

                startActivity(intent);
            } else {
                Toast.makeText(this, "Vui lòng nhập số lượng khách", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện hủy
        buttonCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại adapter khi quay lại
        tableAdapter.notifyDataSetChanged();
    }
}
